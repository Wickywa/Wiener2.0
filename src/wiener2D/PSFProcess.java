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
public class PSFProcess {

    public  ComplexNum2[][] psfData;
    
    public void psfProcess(ImagePlus psf)
    {
         FFT2D psfFFT = new FFT2D();
         psfFFT.setData(psf);
         System.gc();
         psfFFT.fft();
         ComplexNum2[][] psfFFTData = psfFFT.getData();
         psfFFT = null; // mark as eligible for GC
         ComplexNum2.normalizeToMaxAbsValue( psfFFTData, 1 );
         this.psfData=psfFFTData;
    } 
    
       public void psfProcess(String spsf)
    {
         ImagePlus psf = IJ.openImage(PSF_DIR2+"/Deconv/PSF/"+spsf);
         FFT2D psfFFT = new FFT2D();
         psfFFT.setData(psf);
         System.gc();
         psfFFT.fft();
         ComplexNum2[][] psfFFTData = psfFFT.getData();
         psfFFT = null; // mark as eligible for GC
         ComplexNum2.normalizeToMaxAbsValue( psfFFTData, 1 );
         this.psfData=psfFFTData;
    } 

    public ComplexNum2[][] getPsfData() {
        return psfData;
    }

    public static void setGlobalData(ComplexNum2[][] psfData) {
        Globals.psfData = psfData;
    }
    
   
    public void setPsfData(ComplexNum2[][] psfData) {
        this.psfData = psfData;
    }

    public PSFProcess(ComplexNum2[][] psfData) {
        this.psfData = psfData;
    }
    public PSFProcess() {
        
    }
 
    
    
}

