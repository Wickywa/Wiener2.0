/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Wiener_package;

import ij.IJ;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import mmcorej.StrVector;
import org.micromanager.PropertyMap;
import org.micromanager.Studio;
import org.micromanager.data.ProcessorConfigurator;
import org.micromanager.internal.utils.MMFrame;
import wiener2D.Globals;
import wiener2D.PSFProcess;

/**
 *
 * @author Vincent
 */
public class WienerConfigurator extends MMFrame implements ProcessorConfigurator {
    
    private static final String DEFAULT_CAMERA = "Default camera for Wiener";
    private static final String DEFAULT_NSR = "The noise spread repartition";
    private static final String DEFAULT_PSF = "The Point spread function used";
  
    public static final String PSF_DIR2= IJ.getDirectory("startup")+"\\mmplugins";
   // public static final String PSF_DIR2= "C:\\Micro-Manager-2-beta\\mmplugins";
   // public static final String PSF_DIR2= "C:\\Micro-Manager-2.0beta\\mmplugins";
   // public static final String PSF_DIR2= "C:\\Micro-Manager-2betanightbuild\\mmplugins";
    
    
    
    private static final String[] PSF_LIST = File2string(findFiles(PSF_DIR2+"/Deconv/PSF/"));
    
     private final int frameXPos_ = 300;
   private final int frameYPos_ = 300;
   
   
   private Studio studio_;
   private final String selectedCamera_;
   
      
    public WienerConfigurator(Studio studio, PropertyMap settings) {
         studio_ = studio;
        initComponents();
          selectedCamera_ = settings.getString("camera",studio_.profile().getString(WienerConfigurator.class, DEFAULT_CAMERA,studio_.core().getCameraDevice()));
          String nsr = settings.getString("nsr",studio_.profile().getString(WienerConfigurator.class, DEFAULT_NSR, "1e-8"));
          String psf = settings.getString("psf",studio_.profile().getString(WienerConfigurator.class, DEFAULT_PSF,"Psf setting"));
        
            psfComboBox.setSelectedItem(psf);
            this.loadAndRestorePosition(frameXPos_, frameYPos_);
      updateCameras();
    }
    
    
    	 public static File[] findFiles(String directoryPath) {
			File directory = new File(directoryPath);
			if(!directory.exists()){
				System.out.println("The file/directory :'"+directoryPath+"doesn't exist");
			}else if(!directory.isDirectory()){
				System.out.println("Path :'"+directoryPath+" is a file, not a directory");
			}else{
				File[] subfiles = directory.listFiles();
				String message = subfiles.length+" file"+(subfiles.length>1?"s":"")+" in "+"'"+directoryPath+"' :" ;
				System.out.println(message);
				return subfiles;
			}
			return null;
		}
//convert the file[] into a String []	 
	public static String[] File2string(File[] fl) {
           
		String[] str = new String[fl.length];
		
		for (int i = 0; i < fl.length; i++) {
			str[i]=fl[i].getName();	
		}
              return str;
	}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        psfComboBox = new javax.swing.JComboBox();
        psfLabel = new javax.swing.JLabel();
        wienerLabel = new javax.swing.JLabel();
        nsrTextField = new javax.swing.JTextField();
        nsrLabel = new javax.swing.JLabel();
        nsrButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        psfComboBox.setModel(new DefaultComboBoxModel(PSF_LIST));
        psfComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                psfComboBoxActionPerformed(evt);
            }
        });

        psfLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        psfLabel.setText("PSF");
        psfLabel.setToolTipText("");

        wienerLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        wienerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        wienerLabel.setText("Wiener filter parameters");
        wienerLabel.setAutoscrolls(true);

        nsrTextField.setText("0.005");
        nsrTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nsrTextFieldActionPerformed(evt);
            }
        });

        nsrLabel.setText("NSR");

        nsrButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        nsrButton.setText("set");
        nsrButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nsrButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(wienerLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(psfLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nsrLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nsrTextField)
                            .addComponent(psfComboBox, 0, 91, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nsrButton)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(wienerLabel)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(psfComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(psfLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nsrTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nsrLabel)
                    .addComponent(nsrButton))
                .addContainerGap(83, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nsrTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nsrTextFieldActionPerformed
        studio_.profile().setString(WienerConfigurator.class,DEFAULT_PSF, (String) nsrTextField.getText());
        studio_.data().notifyPipelineChanged();
    }//GEN-LAST:event_nsrTextFieldActionPerformed

    private void nsrButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nsrButtonActionPerformed
        studio_.profile().setString(WienerConfigurator.class,DEFAULT_PSF, (String) nsrTextField.getText());
        studio_.data().notifyPipelineChanged();
    }//GEN-LAST:event_nsrButtonActionPerformed

    private void psfComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_psfComboBoxActionPerformed
        studio_.profile().setString(WienerConfigurator.class,DEFAULT_PSF, (String) psfComboBox.getSelectedItem());
        studio_.data().notifyPipelineChanged();
    }//GEN-LAST:event_psfComboBoxActionPerformed

    
    @Override
    public void showGUI() {
       setVisible(true);
      // System.out.println("DIr "+PSF_DIR);
     // System.out.println("MM dir  "+PSF_DIR2);
    }

    @Override
    public void cleanup() {
         dispose();
    }

    
    final public void updateCameras() {
       try {
         StrVector cameras = studio_.core().getAllowedPropertyValues("Core", "Camera");
      } catch (Exception ex) {
         studio_.logs().logError(ex, "Error updating valid cameras in image flipper");
      }
   }
    
      public String getCamera() {
      return selectedCamera_;
   }
    
      public final String getPSF() {
       Globals.psfProcess((String) psfComboBox.getSelectedItem());
      return (String) psfComboBox.getSelectedItem();
      
   }
    public final double getNSR() {
        //needed to convert the string into double
      return Double.parseDouble(nsrTextField.getText()) ;
      
   }
    @Override
    public PropertyMap getSettings() {
        PropertyMap.PropertyMapBuilder builder = studio_.data().getPropertyMapBuilder();
      builder.putString("camera", getCamera());
      builder.putString("psf", getPSF());
      builder.putDouble("nsr", getNSR());
      return builder.build();
    }


    /**
     * @param args the command line arguments
     */
   
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton nsrButton;
    private javax.swing.JLabel nsrLabel;
    private javax.swing.JTextField nsrTextField;
    private javax.swing.JComboBox psfComboBox;
    private javax.swing.JLabel psfLabel;
    private javax.swing.JLabel wienerLabel;
    // End of variables declaration//GEN-END:variables
}