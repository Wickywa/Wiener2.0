/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Wiener_package;

import static Wiener_package.WienerConfigurator.PSF_DIR2;
import ij.IJ;
import ij.ImagePlus;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;
import org.micromanager.PropertyMap;
import org.micromanager.Studio;
import org.micromanager.data.Image;
import org.micromanager.data.Metadata;
import org.micromanager.data.Processor;
import org.micromanager.data.ProcessorContext;
import wiener2D.ComplexNum2;
import wiener2D.Deconvoluter2D;

/**
 *
 * @author Vincent
 */
public class Wiener2Processor extends Processor{
    
    private Studio studio_;
   String camera_;
   ImagePlus psf_;
   double nsr_;
   ComplexNum2[][] psfData;
   
   public Wiener2Processor (Studio studio, String camera, String psf,
         double nsr){
       studio_ = studio;
      camera_ = camera;
        psf_ = IJ.openImage(PSF_DIR2+"/Deconv/PSF/"+psf);
      //psf_=psf;
     nsr_=nsr;
          
   }

    @Override
    public void processImage(Image image, ProcessorContext context) {
        String imageCam = image.getMetadata().getCamera();
        if (imageCam == null||!imageCam.equals(camera_) ) { 
         context.outputImage(image);
          
         return;
      }
      context.outputImage(
            transformImage(studio_, image, psf_, nsr_));
   } //To change body of generated methods, choose Tools | Templates.

    private Image transformImage(Studio studio, Image image, ImagePlus psf, double nsr) {
        //timer
        long startTime = System.currentTimeMillis();
        
        int width = image.getWidth();
      int height = image.getHeight();
      ImageProcessor proc = studio.data().ij().createProcessor(image);
      
      //Wiener process
     ImagePlus img =new ImagePlus("EDF",proc);
     Deconvoluter2D deconvoluter = new Deconvoluter2D( img, psf );
   
     ImageProcessor tempproc =deconvoluter.getDeconvolutedVolume(nsr);
     proc=Convertor(proc,tempproc);
          
        PropertyMap.PropertyMapBuilder builder;
      PropertyMap userData = image.getMetadata().getUserData();
      if (userData != null) {
         builder = userData.copy();
         }
      else {
         builder = studio.data().getPropertyMapBuilder();
            }
       builder.putString("PSF :", psf.getTitle());
       Metadata newMetadata = image.getMetadata().copy().userData(builder.build()).build();
       Image result =image;
        try {
              result = studio.data().ij().createImage(proc, image.getCoords(), newMetadata);
        }
        catch (Exception e) {
            System.out.println("fail display the result image!");
        }
        
     
     
      //timer end
     long endTime = System.currentTimeMillis();
     System.out.println("That took " + (endTime - startTime) + " milliseconds");	
     
    return result;
    }
    
    private ImageProcessor Convertor(ImageProcessor init, ImageProcessor conv){
         if(init instanceof ShortProcessor)
                 {if(conv instanceof ShortProcessor)
                     return conv;
                  return conv.convertToShort(true);
                     }
         else if (init instanceof FloatProcessor)
                 {if(conv instanceof FloatProcessor)
                     return conv;
                  return conv.convertToFloat();
                     }
         return conv;
    }
 
}
