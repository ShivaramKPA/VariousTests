//package org.jlab.dc_calibration.domain;
package swing.consoles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//public class DC_TimeToDistanceFitter implements ActionListener, Runnable {
public class DC_TimeToDistanceFitter implements Runnable {

    private ArrayList<String> fileArray;
    private boolean isLinearFit = false;

    private boolean acceptorder = false;
    private OrderOfAction OAInstance = null;

    public DC_TimeToDistanceFitter(ArrayList<String> files, boolean isLinearFit) {
        this.fileArray = files;
        this.isLinearFit = isLinearFit;
        createHists();
    }

    public DC_TimeToDistanceFitter(OrderOfAction OAInstance, ArrayList<String> files, boolean isLinearFit) {
        this.OAInstance = OAInstance;
        this.fileArray = files;
        this.isLinearFit = isLinearFit;
        createHists();
    }

    private void createHists() {

    }

    protected void processData() {
        int counter = 0;
    }

    protected void drawHistograms() {

    }

    //kp:  This method is required only if ActionListener is implemented (which is usually 
    //     done on the class that extends JFrame, JPanelm WindowAdapter or something like that
    //     or in inner classes that are used to add ActionListener to buttons etc.
    //
//    public void actionPerformed(ActionEvent e) {
//        OAInstance.buttonstatus(e);
//        acceptorder = OAInstance.isorderOk();
//        JFrame frame = new JFrame("JOptionPane showMessageDialog example1");
//        if (acceptorder) {
//            System.out.println("Hello from actionPerformed(ActionEvent e) method of DC_TimeToDistanceFitter class.");
//            JOptionPane.showMessageDialog(frame, "Click OK to start processing the time to distance fitting...");
//            processData();
//            drawHistograms();
//            // DCTabbedPane test = new DCTabbedPane();
//        } else {
//            System.out.println("I am red and it is not my turn now ;( ");
//        }
//    }

//
//    public void OpenFitControlUI(DC_TimeToDistanceFitter fitter) {
//
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FitControlUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FitControlUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FitControlUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FitControlUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FitControlUI(fitter).setVisible(true); //Defined in FitControlUI.java
//            }
//        });
//    }
//
//    public void SliceViewer(DC_TimeToDistanceFitter fitter) {
//        //Create a frame and show it through SwingUtilities
//        //   It doesn't require related methods and variables to be of static type
//        SwingUtilities.invokeLater(() -> {
//            new SliceViewer("Slice Viewer").create(fitter);
//        });
//    }
    
    //kp: This method is called with Thread(objOfThisClass).start() method is called
    //    from a different class such as DC_GUI, and when the entry point is not the 
    //    main(..) method of this file itself (see below).
    @Override
    public void run() {
        
        System.out.println("Hello from overridden 'run()' method of the 'Runnable' \n" 
                + " implementing DC_TimeToDistanceFitter class.");
        System.out.println("\n Thread Name = " + Thread.currentThread().getName() 
                + " & Thread ID = " + Thread.currentThread().getId());
        System.out.println("Files selected and passed to the T2D fitter are: \n " + fileArray);
        processData();

        //SliceViewer(this);
        //OpenFitControlUI(this);
        //drawHistograms(); //Disabled 4/3/17 - to control it by clicks in FitConrolUI.
    }

    public static void main(String[] args) {
        String fileName;
        String fileName2;

        fileName = "/Volumes/Mac_Storage/Work_Codes/CLAS12/DC_Calibration/data/out_clasdispr.00.e11.000.emn0.75tmn.09.xs65.61nb.dis.1.evio";
        ArrayList<String> fileArray = new ArrayList<String>();
        fileArray.add("/Users/michaelkunkel/WORK/CLAS/CLAS12/DC_Calibration/data/Calibration/pion/mergedFiles/cookedFiles/out_out_1.evio");
        fileArray.add("/Users/michaelkunkel/WORK/CLAS/CLAS12/DC_Calibration/data/Calibration/pion/mergedFiles/cookedFiles/out_out_10.evio");
        fileArray.add("/Users/michaelkunkel/WORK/CLAS/CLAS12/DC_Calibration/data/Calibration/pion/mergedFiles/cookedFiles/out_out_2.evio");
        fileArray.add("/Users/michaelkunkel/WORK/CLAS/CLAS12/DC_Calibration/data/Calibration/pion/mergedFiles/cookedFiles/out_out_4.evio");

        DC_TimeToDistanceFitter rd = new DC_TimeToDistanceFitter(fileArray, true);

        rd.processData();
        // System.out.println(rd.getMaximumFitValue(5, 5, 5) + " output");
        rd.drawHistograms();

    }
}
