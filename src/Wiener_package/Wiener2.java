/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Wiener_package;


import org.micromanager.PropertyMap;
import org.micromanager.Studio;
import org.micromanager.data.ProcessorConfigurator;
import org.micromanager.data.ProcessorFactory;
import org.micromanager.data.ProcessorPlugin;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.SciJavaPlugin;

/**
 * 
 * @author Vincent
 */
@Plugin(type = ProcessorPlugin.class)
public class Wiener2 implements ProcessorPlugin, SciJavaPlugin  {
  private Studio studio_;
   @Override
   public void setContext(Studio studio) {
      studio_ = studio;
      
         }
   @Override
   public ProcessorConfigurator createConfigurator(PropertyMap settings) {
      return new WienerConfigurator(studio_, settings);
   }
   @Override
   public ProcessorFactory createFactory(PropertyMap settings) {
      return new Wiener2Factory(settings, studio_);
   }
   @Override
   public String getName() {
      return "Wiener Filter";
   }
   @Override
   public String getHelpText() {
      return "Wiener filter for image deconvolution";
   }

   @Override
   public String getVersion() {
      return "Version 1.0";
   }

   @Override
   public String getCopyright() {
      return "Copyright Riken, 2016";
   }
    
}
