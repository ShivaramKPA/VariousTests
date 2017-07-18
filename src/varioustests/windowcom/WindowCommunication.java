/*
 * https://stackoverflow.com/questions/23582464/java-gui-passing-values-from-frame-to-another
 */
package varioustests.windowcom;

import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class WindowCommunication {

   private static void createAndShowUI() {
      JFrame frame = new JFrame("WindowCommunication");
      frame.getContentPane().add(new MyFramePanel());
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   // let's be sure to start Swing on the Swing event thread
   public static void main(String[] args) {
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            createAndShowUI();
         }
      });
   }
}
