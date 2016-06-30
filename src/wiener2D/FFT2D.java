package wiener2D;


import ij.ImagePlus;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

/**
 *
 * @author Vincent
 */
public  class FFT2D implements Cloneable {
    
    private ComplexNum2[][]data;
    private int dataDimX, dataDimY;
     private PublicObservable2 observationAgent;
    public static final Direction FORWARD = new Direction( "FORWARD" );
    public static final Direction INVERSE = new Direction( "INVERSE" );
     
     public FFT2D()
    {
        this.observationAgent = new PublicObservable2();
    }
     
     
    protected ComplexNum2 makeComplexNumber()
    {
        return new DoublePrecComplNum2();
    }
    protected ComplexNum2 makeComplexNumber( ComplexNum2 c )
    {
        return new DoublePrecComplNum2( c );
    }
    protected ComplexNum2 makeComplexNumber( double re, double im )
    {
        return new DoublePrecComplNum2( re, im );
    }
    protected ComplexNum2 makeComplexNumber( double re )
    {
        return new DoublePrecComplNum2( re );
    }
  public void setChanged()
    {
        this.observationAgent.setChanged();
        this.observationAgent.notifyObservers( this );
    }

  public void setData( ImagePlus sourceReal, ImagePlus sourceImag )
    {
         if ( sourceReal == null )
           throw new IllegalArgumentException( "Source img with real part cannot be 'null'." );

        if ( ( sourceImag != null ) &&
             ( ( sourceReal.getWidth() != sourceImag.getWidth() ) ||
               ( sourceReal.getHeight() != sourceImag.getHeight() ) ))
            throw new IllegalArgumentException( "Source img with real and imaginary part must be\n" +
                                                "of the same size in x- and y-direction." );

        this.dataDimX = sourceReal.getWidth();
        this.dataDimY = sourceReal.getHeight();
        this.data = new ComplexNum2[ this.dataDimX ][ this.dataDimY ];
        ImageProcessor imageReal = sourceReal.getProcessor();
            if ( sourceImag != null )
            {
                ImageProcessor imageImag = sourceImag.getProcessor();
                for ( int x = 0; x < this.dataDimX; x++ )
                    for ( int y = 0; y < this.dataDimY; y++ )
                        this.data[ x ][ y ] = this.makeComplexNumber( imageReal.getPixelValue( x, y ),
                                                                               imageImag.getPixelValue( x, y ) );
            }
            else
                for ( int x = 0; x < this.dataDimX; x++ )
                    for ( int y = 0; y < this.dataDimY; y++ )
                        this.data[ x ][ y ] = this.makeComplexNumber( imageReal.getPixelValue( x, y ) );
        

        this.setChanged();
    }
     public void setData( ImagePlus sourceReal )
    {
        this.setData( sourceReal, null );
    }   
      public void setData( ComplexNum2[][] source )
    {
        if ( source == null )
           throw new IllegalArgumentException( "'source' cannot be 'null'." );

        this.dataDimX = source.length;
        this.dataDimY = source[ 0 ].length;

        this.data = source;

        this.setChanged();
    }
  public ComplexNum2[][] getData()
    {
        return this.data;
    }
  public void fft( Direction direction )
    {
        if ( this.getData() == null )
           throw new IllegalStateException( "Data to process must be set first. Use method setData(). ");

        this.FFT2D( direction );
    }
  public void fft() { 
      this.fft( FFT2D.FORWARD );
  }
  public void ifft() { 
      this.fft( FFT2D.INVERSE ); 
  }
  private static boolean isPowerOfTwo( int value )
  {
      int i;
      for ( i = 2; i < value; i *= 2 );
      return ( i == value );
  }

    private void FFT2D(Direction direction) {
        int nx = this.data.length;
        boolean nxPowerOf2 = isPowerOfTwo( nx );
        int ny = this.data[ 0 ].length;
        boolean nyPowerOf2 = isPowerOfTwo( ny );
        ComplexNum2 referenceBuffer = this.makeComplexNumber();
        ComplexNum2[] objectBuffer = new ComplexNum2[Math.max( nx, ny)];
        for ( int i = 0; i < objectBuffer.length; i++ )
            objectBuffer[ i ] = this.makeComplexNumber();
       ComplexNum2[] temporarySource = new ComplexNum2[Math.max( nx, ny)]; 
       for ( int y = 0; y < ny; y++ )
            {
                for ( int x = 0; x < nx; x++ )
                    temporarySource[ x ] = this.data[ x ][ y ];
                if ( nxPowerOf2 )
                   FFT1D( direction, temporarySource, nx, referenceBuffer );
                else
                    DFT1D( direction, temporarySource, nx, objectBuffer );

                for ( int x = 0; x < nx; x++ )
                    this.data[ x ][ y ] = temporarySource[ x ];
            }
       for ( int x = 0; x < nx; x++ )
            {
                for ( int y = 0; y < ny; y++ )
                    temporarySource[ y ] = this.data[ x ][ y ];

                if ( nyPowerOf2 )
                   FFT1D( direction, temporarySource, ny, referenceBuffer );
                else
                    DFT1D( direction, temporarySource, ny, objectBuffer );

                for ( int y = 0; y < ny; y++ )
                    this.data[ x ][ y ] = temporarySource[ y ];
                
              this.setChanged();
            }
       
    }
     private static int calculateLog2( int length )
    {
        int log = 1;
        int i;
        for ( i = 2; i < length; i *= 2 )
            log++;

        if ( i != length )
           throw new IllegalArgumentException( "Argument 'length' is zero or not a power of 2." );

        return log;
    }
    static private void FFT1D( Direction direction, ComplexNum2[] source, int sourceLength, ComplexNum2 buffer )
    {
        int lengthLog2 = calculateLog2( sourceLength );

        long i2 = sourceLength >> 1;
        int j = 0;
        for ( int i = 0; i < sourceLength - 1; i++ )
        {
            if ( i < j )
            {
                ComplexNum2 temp = source[ i ];
                source[ i ] = source[ j ];
                source[ j ] = temp;
            }
            long k = i2;
            while( k <= j )
            {
                j -= k;
                k >>= 1;
            }
            j += k;
        }

        double c1 = -1, c2 = 0;
        long l2 = 1;
        for ( long l = 0; l < lengthLog2; l++ )
        {
            long l1 = l2;
            l2 <<= 1;
            double u1 = 1, u2 = 0;
            for ( j = 0; j < l1; j++ )
            {
                for ( int i = j; i < sourceLength; i += l2 )
                {
                    int i1 = ( int )( i + l1 );
                    buffer.setValue( u1 * source[ i1 ].getRealValue() - u2 * source[ i1 ].getImagValue(),
                                   u1 * source[ i1 ].getImagValue() + u2 * source[ i1 ].getRealValue() );
                    source[ i1 ].setValue( source[ i ].getRealValue() - buffer.getRealValue(),
                                           source[ i ].getImagValue() - buffer.getImagValue());
                    source[ i ].addValue( buffer );
                }
                double z = u1 * c1 - u2 * c2;
                u2 = u1 * c2 + u2 * c1;
                u1 = z;
            }
            c2 = Math.sqrt( ( 1 - c1 ) / 2d );
            if ( direction == FORWARD )
               c2 = - c2;
            c1 = Math.sqrt( ( 1 + c1 ) / 2d );
        }

        // scaling for forward transformation
        if ( direction == FORWARD )
           for ( int i = 0; i < sourceLength; i++ )
               source[ i ].divideByValue( sourceLength );
    }
    static private void DFT1D( Direction direction, ComplexNum2[] source, int sourceLength, ComplexNum2[] buffer )
    {
        for ( int i = 0; i < sourceLength; i++ )
        {
            buffer[ i ].setValue( 0, 0 );
            double arg = - 2d * Math.PI * i / sourceLength;
            if ( direction == INVERSE )
               arg = - arg;
            for ( int k = 0; k < sourceLength; k++ )
            {
                double cosarg = Math.cos( k * arg );
                double sinarg = Math.sin( k * arg );
                buffer[ i ].addValue( source[ k ].getRealValue() * cosarg - source[ k ].getImagValue() * sinarg,
                                      source[ k ].getRealValue() * sinarg + source[ k ].getImagValue() * cosarg );
            }
        }

        // scaling for forward transformation
        if ( direction == FORWARD )
           for ( int i = 0; i < sourceLength; i++ )
              buffer[ i ].divideByValue( sourceLength );

        // Copy the data back
        for ( int i = 0; i < sourceLength; i++ )
            source[ i ].setValue( buffer[ i ] );
    }
    public void rearrangeData()
    {
        if ( this.getData() == null )
           throw new IllegalStateException( "Data to process must be set first. Use method setData(). ");

        int halfDimXRounded = ( int ) Math.round( this.dataDimX / 2d );
        int halfDimYRounded = ( int ) Math.round( this.dataDimY / 2d );
 
        ComplexNum2[] buffer = new ComplexNum2[ Math.max( this.dataDimX / 2, this.dataDimY / 2)];

        // swap data in x-direction
        for	( int y = 0; y < this.dataDimY; y++ )
                    {
                // cache first "half" to buffer
                for ( int x = 0; x < this.dataDimX / 2; x++ )
                    buffer[ x ] = this.data[ x ][ y ];
                // move second "half" to first "half"
                for ( int x = 0; x < halfDimXRounded; x++ )
                    this.data[ x ][ y ] = this.data[ x + this.dataDimX / 2 ][ y ];
                // move data in buffer to second "half"
                for ( int x = halfDimXRounded; x < this.dataDimX; x++ )
                    this.data[ x ][ y ] = buffer[ x - halfDimXRounded ];
            }
         for	( int x = 0; x < this.dataDimX; x++ )
              {
                // cache first "half" to buffer
                for ( int y = 0; y < this.dataDimY / 2; y++ )
                    buffer[ y ] = this.data[ x ][ y ];
                // move second "half" to first "half"
                for ( int y = 0; y < halfDimYRounded; y++ )
                    this.data[ x ][ y ] = this.data[ x ][ y + this.dataDimY / 2 ];
                // move data in buffer to second "half"
                for ( int y = halfDimYRounded; y < this.dataDimY; y++ )
                    this.data[ x ][ y ] = buffer[ y - halfDimYRounded ];
            }
           this.setChanged();
    }
    
    public ImagePlus toImagePlus( ComplexValueType2 type )
    {
        return this.toImagePlus( type, "AT_ZERO");
    }
        public ImagePlus toImagePlus( ComplexValueType2 type , String fdOrigin )
    {
        if ( this.getData() == null )
           throw new IllegalStateException( "Data to process must be set first. Use method setData(). ");
        ComplexNum2[][] data;
            data = this.getData();
          // output data is of FLOAT precision by default
            FloatProcessor proc = new FloatProcessor( this.dataDimX, this.dataDimY );
            for ( int x = 0; x < this.dataDimX; x++ )
                for ( int y = 0; y < this.dataDimY; y++ )
                    proc.putPixelValue( x, y, data[ x ][ y ].getValue(type) );
        ImagePlus imp = new ImagePlus( type.toString() +   ")", proc );
        imp.changes = true;
        return imp;
    }
  public static class Direction
    {
        private String name;
        private Direction( String name ) { this.name = name; }
        public String toString() { return this.name; }
    }
  public ImageProcessor toImageProc( ComplexValueType2 type )
  {
      return this.toImageProc( type, "AT_ZERO");
  }
  public ImageProcessor toImageProc( ComplexValueType2 type,String fdOrigin  )
  {
	  if ( this.getData() == null )
          throw new IllegalStateException( "Data to process must be set first. Use method setData(). ");
       ComplexNum2[][] data;
           data = this.getData();
                 // output data is of FLOAT precision by default
           FloatProcessor proc = new FloatProcessor( this.dataDimX, this.dataDimY );
           for ( int x = 0; x < this.dataDimX; x++ )
               for ( int y = 0; y < this.dataDimY; y++ )
                   proc.putPixelValue( x, y, data[ x ][ y ].getValue(type) );
      return proc;
  }
  
  
  
}
