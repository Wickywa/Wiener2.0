package wiener2D;

/**
 * Title:        DeconvolutionJ --- Image Reconstruction with ImageJ
 * Description:
 * Copyright:    Copyright (c) 2001, 2002
 * Company:      Wake Forest University School of Medicine, Department of Medical Engineering
 *               Winston-Salem, North Carolina, USA
 * @author       Nick Linnenbr�gger (nilin@web.de)
 */

abstract public class ComplexNum2 implements Cloneable
{
    public ComplexNum2() { this.setValue( 0, 0 ); }

    public ComplexNum2( double re, double im ) { this.setValue( re, im ); }

    public ComplexNum2( double re ) { this.setValue( re, 0 ); }

    public ComplexNum2( ComplexNum2 c ) { this.setValue( c ); }

    abstract public double getRealValue();

    abstract public double getImagValue();

    abstract public void setRealValue( double re );

    abstract public void setImagValue( double im );

    public Object clone()
    {
        try { return super.clone(); }
        catch ( CloneNotSupportedException exc ) { throw new RuntimeException( exc.getMessage() ); }
    }

    public boolean equals( Object obj )
    {
        if ( !( obj instanceof ComplexNum2 ) )
           return false;
        else
            return ( this.getRealValue() == ( ( ComplexNum2 ) obj ).getRealValue() ) &&
                   ( this.getImagValue() == ( ( ComplexNum2 ) obj ).getImagValue() );
    }

    public void setValue( double re, double im )
    {
        this.setRealValue( re );
        this.setImagValue( im );
    }

    public void setValue( ComplexNum2 c ) { this.setValue( c.getRealValue(), c.getImagValue() ); }

    public void makeComplexConjugate() { this.setImagValue( - this.getImagValue() ); }

    public ComplexNum2 getComplexConjugate()
    {
        ComplexNum2 clone = ( ComplexNum2 ) this.clone();
        clone.setImagValue( - clone.getImagValue() );
        return clone;
    }

    public void addValue( ComplexNum2 c )
    {
        this.setValue( this.getRealValue() + c.getRealValue(), this.getImagValue() + c.getImagValue() );
    }

    public void addValue( double re, double im )
    {
        this.setValue( this.getRealValue() + re, this.getImagValue() + im );
    }

    public void addValue( double re )
    {
        this.setRealValue( this.getRealValue() + re );
    }

    public void divideByValue( ComplexNum2 c )
    {
        double divisor = c.getRealValue() * c.getRealValue() + c.getImagValue() * c.getImagValue();
        this.setValue( ( this.getRealValue() * c.getRealValue() + this.getImagValue() * c.getImagValue() ) / divisor,
                       ( this.getImagValue() * c.getRealValue() - this.getRealValue() * c.getImagValue() ) / divisor );
    }

    public void divideByValue( double realDivisor )
    {
        this.setValue( this.getRealValue() / realDivisor, this.getImagValue() / realDivisor );
    }

    public void multiplyByValue( ComplexNum2 c )
    {
        this.setValue( this.getRealValue() * c.getRealValue() - this.getImagValue() * c.getImagValue(),
                       this.getRealValue() * c.getImagValue() + this.getImagValue() * c.getRealValue() );
    }

    public void multiplyByValue( double realMultiplier )
    {
        this.setValue( this.getRealValue() * realMultiplier, this.getImagValue() * realMultiplier );
    }

    public double getAbs()
    {
        return Math.sqrt( this.getRealValue() * this.getRealValue() + this.getImagValue() * this.getImagValue() );
    }

    public double getFourierFrequencySpectrum() { return this.getAbs(); }

    public double getFourierFrequencySpectrumLogarithmic() { return Math.log( 1d + this.getAbs() ); }

    public double getFourierPhaseSpectrum() { return Math.atan2( this.getImagValue(), this.getRealValue() ); }

    public double getFourierPowerSpectrum()
    {
        return this.getRealValue() * this.getRealValue() + this.getImagValue() * this.getImagValue();
    }

    public double getFourierPowerSpectrumLogarithmic() { return Math.log( 1d + this.getFourierPowerSpectrum() ); }

public double getValue( ComplexValueType2 type )
    {
        if ( type == ComplexValueType2.ABS )
           return this.getAbs();
        else if ( type == ComplexValueType2.FREQUENCY_SPECTRUM )
             return this.getFourierFrequencySpectrum();
        else if ( type == ComplexValueType2.FREQUENCY_SPECTRUM_LOG )
             return this.getFourierFrequencySpectrumLogarithmic();
        else if ( type == ComplexValueType2.IMAG_PART )
             return this.getImagValue();
        else if ( type == ComplexValueType2.PHASE_SPECTRUM )
             return this.getFourierPhaseSpectrum();
        else if ( type == ComplexValueType2.POWER_SPECTRUM )
             return this.getFourierPowerSpectrum();
        else if ( type == ComplexValueType2.POWER_SPECTRUM_LOG )
             return this.getFourierPowerSpectrumLogarithmic();
        else
            return this.getRealValue();
    }

    public String toString()
    {
        return this.getRealValue() + " + " + this.getImagValue() + "i";
    }

    public static void normalizeToMaxAbsValue( ComplexNum2[][] data, double value )
    {
        double max = Double.MIN_VALUE;

        for ( int x = 0; x < data.length; x++ )
            for ( int y = 0; y < data[ 0 ].length; y++ )
               
                    max = Math.max( max, data[ x ][ y ].getAbs() );

        for ( int x = 0; x < data.length; x++ )
            for ( int y = 0; y < data[ 0 ].length; y++ )
                
                    data[ x ][ y ].multiplyByValue( value / max );
    }
}