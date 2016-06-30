/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Wiener_package;



import org.micromanager.PropertyMap;
import org.micromanager.Studio;
import org.micromanager.data.Processor;
import org.micromanager.data.ProcessorFactory;

/**
 *
 * @author Vincent
 */
public class Wiener2Factory implements ProcessorFactory {
   private PropertyMap settings_;
   private Studio studio_;
   
   public Wiener2Factory(PropertyMap settings, Studio studio) {
      settings_ = settings;
      studio_ = studio;
   }

    @Override
    public Processor createProcessor() {
        return new Wiener2Processor(studio_, settings_.getString("camera", ""), settings_.getString("psf","PSF.tif"),settings_.getDouble("nsr", 1e-8));
    }
    
}
