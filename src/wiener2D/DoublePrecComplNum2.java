package wiener2D;

/**
 * Title:        DeconvolutionJ --- Image Reconstruction with ImageJ
 * Description:
 * Copyright:    Copyright (c) 2001, 2002
 * Company:      Wake Forest University School of Medicine, Department of Medical Engineering
 *               Winston-Salem, North Carolina, USA
 * @author       Nick Linnenbr�gger (nilin@web.de)
 */

// class is final for performance reasons
final public class DoublePrecComplNum2 extends ComplexNum2
{
    private double re, im;


    public DoublePrecComplNum2() { super(); }

    public DoublePrecComplNum2( double re, double im ) { super( re, im ); }

    public DoublePrecComplNum2( double re ) { super( re ); }

    public DoublePrecComplNum2( ComplexNum2 c ) { super( c ); }


    public double getRealValue() { return this.re; }

    public double getImagValue() { return this.im; }

    public void setRealValue( double re ) { this.re = re; }

    public void setImagValue( double im ) { this.im = im; }
}