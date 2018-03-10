/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package varioustests.actionOrder;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author kpadhikari
 */


public class actionOrderTestUI extends JFrame {
    
    JButton button1, button2, button3, button4, button5, button6;
    OrderOfAction OA = null;

    public actionOrderTestUI () {
        initLookAndFeel();

        createView();
        
        //Make window exit application on close
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Set display size (int width, int height) in pixels
        setSize(400, 200);
        //Center the frame to middle of screen
        setLocationRelativeTo(null); 
        //kp: other JFrame object is passed instead of 'null', this frame will open wrt the middle of the first frame
        
        //Disable resize
        //setResizable(false);
        setResizable(true);
    }

    //Initialize all UI components
    public void createView() {
        JPanel panelMain = new JPanel();
        getContentPane().add(panelMain);
        
        JPanel buttonPanel1 = new JPanel(new FlowLayout());
        panelMain.add(buttonPanel1);
        JPanel buttonPanel2 = new JPanel(new FlowLayout());
        panelMain.add(buttonPanel2);
        
        button1 = new JButton("Button1");
        button2 = new JButton("Button2");
        button3 = new JButton("Button3");
        button4 = new JButton("Button4");
        button5 = new JButton("Button5");
        button6 = new JButton("Button6");        
        
        addListeners(button1, 1);
        addListeners(button2, 2);
        addListeners(button3, 3);
        addListeners(button4, 4);
        addListeners(button5, 5);
        addListeners(button6, 6);
        
        buttonPanel1.add(button1);
        buttonPanel1.add(button2);
        buttonPanel1.add(button3);
        buttonPanel2.add(button4);
        buttonPanel2.add(button5);
        buttonPanel2.add(button6);
        
        OA = new OrderOfAction(6);
        OA.setButtonOrder(button1, 1);
        OA.setButtonOrder(button2, 2);
        OA.setButtonOrder(button3, 3);
        OA.setButtonOrder(button4, 4);
        OA.setButtonOrder(button5, 5);
        OA.setButtonOrder(button6, 6);
    }
    
    private void addListeners(JButton button, int bNum) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OA.setButtonStatus(e);
                if (OA.isOrderOk()) {
                    System.out.println("Button "+ bNum + " Clicked.");
                    System.out.println("currentIndex = " + OA.getCurrentIndex());
                }
            }
        });
        
    }
    
    
    private static void initLookAndFeel() {
        //
        // Ideas used from 
        // https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/ and
        // https://docs.oracle.com/javase/tutorial/uiswing/examples/lookandfeel/index.html
        //
        initNimbusLookAndFeel();
        //initSynthLookAndFeel();
    }

    private static void initNimbusLookAndFeel() {

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
            System.err.println("Couldn't get specified look and feel ("
                    + "Nimbus" //+ lookAndFeel
                    + "), for some reason.");
            System.err.println("Using the default look and feel.");
            e.printStackTrace();            
        }
    }

    private static void initSynthLookAndFeel() {
//        //https://docs.oracle.com/javase/tutorial/uiswing/examples/lookandfeel/index.html
//        //private static String synthFile = "buttonSkin.xml";
//        String synthFile = "buttonSkin.xml";
//
//        // String lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
//        SynthLookAndFeel lookAndFeel = new SynthLookAndFeel();
//
//        try {
//            lookAndFeel.load(SynthApplication.class.getResourceAsStream(synthFile),
//                    SynthApplication.class);
//            UIManager.setLookAndFeel(lookAndFeel);
//        } catch (Exception e) {
//            System.err.println("Couldn't get specified look and feel ("
//                    + lookAndFeel
//                    + "), for some reason.");
//            System.err.println("Using the default look and feel.");
//            e.printStackTrace();
//        }

    }


    //Entry point of program
    public static void main(String[] args) {
        //Create a frame and show it through SwingUtilities
        SwingUtilities.invokeLater(()-> {new actionOrderTestUI().setVisible(true);});
    }
}
