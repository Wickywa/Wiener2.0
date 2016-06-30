/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wiener2D;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 *
 * @author Vincent
 */
public class Deconvoluter2D {
     ImagePlus blurred, psf;

    public Deconvoluter2D( ImagePlus blurred, ImagePlus psf )
    {
        if ( ( blurred.getWidth() != psf.getWidth() ) || ( blurred.getHeight() != psf.getHeight() ) )
           throw new IllegalArgumentException( "Both image  must be of the same size in all dimensions." );
        this.blurred = blurred;
        this.psf = psf;
    }
    public ImageProcessor getDeconvolutedVolume( double gamma)
    {
      // Compute The EDF image
      // IJ.showStatus( "Compute Fourier Transform of blurred image (g) ..." );
        FFT2D blurredFFT = new FFT2D();
        blurredFFT.setData(this.blurred);
        System.gc(); // garbage collector 
        blurredFFT.fft();
        ComplexNum2[][] blurredFFTData = blurredFFT.getData();
        ComplexNum2[][] psfFFTData =Globals.psfData;
         
        // IJ.showStatus( "Apply Regularized Wiener Filter ..." );
        // normalize DC components to 1
         ComplexNum2.normalizeToMaxAbsValue( blurredFFTData, 1 );
        blurredFFT.setChanged();
      
        // calculate Fourier domain F of undegraded image f by calculating
        // F(u,v) = ( G(u,v) H(u,v) ) / ( |H(u,v)|^2 + gamma )
         for ( int x = 0; x < blurredFFTData.length; x++ )
            for ( int y = 0; y < blurredFFTData[ 0 ].length; y++ )
                {
                    blurredFFTData[ x ][ y ].multiplyByValue( psfFFTData[ x ][ y ] );
                    blurredFFTData[ x ][ y ].divideByValue(psfFFTData[ x ][ y ].getFourierPowerSpectrum() + gamma );
                }
        blurredFFT.setChanged();
        psfFFTData = null; // mark as eligible for GC
        blurredFFTData = null; // the actual data is still referenced by 'blurredFFT'
        System.gc();
        blurredFFT.ifft();
        blurredFFT.rearrangeData();
        Deconvoluter2D.cutNegativeValues(blurredFFT.getData());
        blurredFFT.setChanged();     
           
       ImageProcessor deconvoluted = blurredFFT.toImageProc(ComplexValueType2.REAL_PART );
        blurredFFT = null; // mark as eligible for GC
       
        
        return deconvoluted;
       
    }
    
    private static void cutNegativeValues( ComplexNum2[][] data )
    {
        for ( int x = 0; x < data.length; x++ )
            for ( int y = 0; y < data[ 0 ].length; y++ )
                {
                    if ( data[ x ][ y ].getRealValue() < 0 )
                       data[ x ][ y ].setValue( 0, 0 );
                }
    }
    
    
  
    
}
