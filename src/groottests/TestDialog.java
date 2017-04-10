/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groottests;
//http://www2.hawaii.edu/~takebaya/ics111/jdialog/jdialog.html

import javax.swing.*;
import java.awt.event.*;
class TestDialog extends JFrame implements ActionListener {
   public TestDialog(String title) {
      super(title);
      setBounds(0,0,600,400);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      JMenu file = new JMenu("File");
      JMenuItem quit = new JMenuItem("Quit");
      quit.addActionListener(this);
      file.add(quit);
      menuBar.add(file);
      JMenu data = new JMenu("Data");
      JMenuItem enter = new JMenuItem("Enter data");
      enter.addActionListener(this);
      data.add(enter);
      //menuBar.add(data);
      
      //kp: Lines added by myself
     //JButton bReconstruction = new JButton();
     JMenuItem rec = new JMenuItem("Run Reconstruction");
     rec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //System.out.println("Reconstruction menu has been hit..");
                //JOptionPane.showMessageDialog(null, "I am happy to get the Reconstruction order.");
                //chooseInputFiles(inputFC, e); 
                String choice = ae.getActionCommand();
                if (choice.equals("Quit")) {
                    System.exit(0);
                }   
                //else if (choice.equals("Enter data")) {
                else {
                    callMyDialog2();    
                }                
            }
     }); 
     data.add(rec);
     menuBar.add(data);
   }
   
    public void callMyDialog2() {
        String fName, runNum;
        MyDialog2 dlg = new MyDialog2(this);
        String[] results = dlg.run();        
        if (results[0] != null) {
            fName = results[0];
            runNum = fName.substring(41, 47);
            System.out.println("Run number = " + runNum);
            JOptionPane.showMessageDialog(this,
                    "Input file: " + results[0] + "\nOutput file: " + results[1]);                    
        }
    }
   
   public void actionPerformed(ActionEvent ae) {
      String choice = ae.getActionCommand();
      if (choice.equals("Quit")) {
         System.exit(0);
      }
      else if (choice.equals("Enter data")) {
         MyDialog dlg = new MyDialog(this);
         String[] results = dlg.run();
         if (results[0] != null) {
            JOptionPane.showMessageDialog(this,
               results[0] + ", color: " + results[1]);
         }
      }
   }
   public static void main(String[] args) {
      TestDialog myApp = new TestDialog("Test Dialog");
      myApp.setVisible(true);
   }
}