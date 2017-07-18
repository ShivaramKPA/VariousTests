/*
 * https://www.tutorialspoint.com/swing/swing_jslider.htm
 */
package components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class JSliderSwingControlDemo {
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;

   public JSliderSwingControlDemo(){
      prepareGUI();
   }
   public static void main(String[] args){
      JSliderSwingControlDemo  swingControlDemo = new JSliderSwingControlDemo();      
      swingControlDemo.showSliderDemo();
   }
   private void prepareGUI(){
      mainFrame = new JFrame("Java Swing Examples");
      mainFrame.setSize(400,400);
      mainFrame.setLayout(new GridLayout(3, 1));
      
      //kp: Similar to the listener for slider below, here also, we have an
      //    object of anonymous class (which implemented WindowAdapter abstract class)
      //    being passed into the addWindowListener() method of the JFrame (which, 
      //    in turn, extends Object)
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new JLabel("", JLabel.CENTER);        
      statusLabel = new JLabel("",JLabel.CENTER);    
      statusLabel.setSize(350,100);//kp: Without this, the main window was smaller
      statusLabel.setBorder(BorderFactory.createLineBorder(Color.red));//kp:

      controlPanel = new JPanel(); //kp: I think, this is used only to give some white space in the middle
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel); //kp: Without this, the statusLabel moved up to the middle
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);  
   }
   private void showSliderDemo(){
      headerLabel.setText("Control in action: JSlider"); 
      int initialSliderValue = 30;
      JSlider slider = new JSlider(JSlider.HORIZONTAL,0,100,initialSliderValue);
      statusLabel.setText("Selected Value : " + initialSliderValue); //kp
      
      //kp: Listener here is prepared by passing (into addChangeListener(object) method) 
      //    an instance of an anonymous class which 
      //    is created by implementing the ChangeListner interface (which, in turn, extends EventListener)
      slider.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            statusLabel.setText("Selected Value : " 
                    + ((JSlider)e.getSource()).getValue());
         }
      });
      
      controlPanel.add(slider);      
      //mainFrame.setVisible(true); //kp: Disabled as it seemed unnecessary    
   } 
}