/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wiener2D;

import static Wiener_package.WienerConfigurator.PSF_DIR2;
import ij.IJ;
import ij.ImagePlus;

/**
 *
 * @author Vincent
 */
public class Globals {
     public static ComplexNum2[][] psfData;  
     
     
    public static void setGlobalData(ComplexNum2[][] psfData) {
        Globals.psfData = psfData;
    }
    
       public static void psfProcess(String spsf)
    {
         ImagePlus psf = IJ.openImage(PSF_DIR2+"/Deconv/PSF/"+spsf);
         FFT2D psfFFT = new FFT2D();
         psfFFT.setData(psf);
         System.gc();
         psfFFT.fft();
         ComplexNum2[][] psfFFTData = psfFFT.getData();
         psfFFT = null; // mark as eligible for GC
         ComplexNum2.normalizeToMaxAbsValue( psfFFTData, 1 );
        Globals.psfData=psfFFTData;
        System.out.println(spsf);
    } 
}
