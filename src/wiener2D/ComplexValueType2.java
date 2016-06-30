package wiener2D;

/**
 * Title:        DeconvolutionJ --- Image Reconstruction with ImageJ
 * Description:
 * Copyright:    Copyright (c) 2001, 2002
 * Company:      Wake Forest University School of Medicine, Department of Medical Engineering
 *               Winston-Salem, North Carolina, USA
 * @author       Nick Linnenbr�gger (nilin@web.de)
 */

public class ComplexValueType2
{
    private String name;
    private static String enumerationDescription = new String( "Complex Value Type" );

    private ComplexValueType2( String name ) { this.name = name; }

    public String toString() { return name; }

    public String getEnumerationDescription() { return enumerationDescription; }

    public boolean equals( Object obj )
    {
        if ( !( obj instanceof ComplexValueType2 ) )
           return false;
        else
            return ( this.name == ( ( ComplexValueType2 ) obj ).name );
    }

    public static final ComplexValueType2 ABS = new ComplexValueType2( "Abs" );
    public static final ComplexValueType2 IMAG_PART = new ComplexValueType2( "Imaginary Part" );
    public static final ComplexValueType2 REAL_PART = new ComplexValueType2( "Real Part" );
    public static final ComplexValueType2 FREQUENCY_SPECTRUM = new ComplexValueType2( "Frequency Spectrum" );
    public static final ComplexValueType2 FREQUENCY_SPECTRUM_LOG =
                                         new ComplexValueType2( "Frequency Spectrum (logarithmic)" );
    public static final ComplexValueType2 PHASE_SPECTRUM = new ComplexValueType2( "Phase Spectrum" );
    public static final ComplexValueType2 POWER_SPECTRUM = new ComplexValueType2( "Power Spectrum" );
    public static final ComplexValueType2 POWER_SPECTRUM_LOG =
                                         new ComplexValueType2( "Power Spectrum (logarithmic)" );
}
