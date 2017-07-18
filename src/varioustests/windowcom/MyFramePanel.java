/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varioustests.windowcom;

import java.awt.Dialog.ModalityType;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

class MyFramePanel extends JPanel {
   private JTextField field = new JTextField(10);
   private JButton openDialogeBtn = new JButton("Open Dialog");

   // here my main gui has a reference to the JDialog and to the
   // MyDialogPanel which is displayed in the JDialog
   private MyDialogPanel dialogPanel = new MyDialogPanel();
   private JDialog dialog;

   public MyFramePanel() {
      openDialogeBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            openTableAction();
         }
      });
      field.setEditable(false);
      field.setFocusable(false);

      add(field);
      add(openDialogeBtn);
   }

   private void openTableAction() {
      // lazy creation of the JDialog
      if (dialog == null) {
         Window win = SwingUtilities.getWindowAncestor(this);
         if (win != null) {
            dialog = new JDialog(win, "My Dialog",
                     ModalityType.APPLICATION_MODAL);
            dialog.getContentPane().add(dialogPanel);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
         }
      }
      dialog.setVisible(true); // here the modal dialog takes over

      // this line starts *after* the modal dialog has been disposed
      // **** here's the key where I get the String from JTextField in the GUI held
      // by the JDialog and put it into this GUI's JTextField.
      field.setText(dialogPanel.getFieldText());
   }
}
