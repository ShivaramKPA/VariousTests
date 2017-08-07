/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varioustests.swingworkertests;

/**
 *
 * @author http://www.java2s.com/Tutorials/Java/Swing_How_to/SwingWorker/Check_SwingWorker_state.htm
 */

//from w w w.  j av a  2  s  .c  o m
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class CheckSwingWorkerState {   
//public class Main {
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new TestPane());
    frame.pack();
    frame.setVisible(true);
  }
}
class ProgressWorker extends SwingWorker<Object, Object> {
  @Override
  protected Object doInBackground() throws Exception {
    for (int i = 0; i < 100; i++) {
      setProgress(i);
      try {
        Thread.sleep(50);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}

class TestPane extends JPanel {
  JProgressBar pbProgress = new JProgressBar();
  JButton start;

  public TestPane() {    
    add(pbProgress);
    start = new JButton("Start");
    add(start);
    start.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //kp: Once the start button is clicked, we enter here and we disable this button 
        //    in order to allow the background task go on. When the task is done or when
        //    a stop button (if any) is clicked, the start button is enabled again.
        start.setEnabled(false);
        
        ProgressWorker pw = new ProgressWorker();
        pw.addPropertyChangeListener(new PropertyChangeListener() {

          @Override
          public void propertyChange(PropertyChangeEvent evt) {
            String name = evt.getPropertyName();
            
            //kp: http://docs.oracle.com/javase/tutorial/uiswing/concurrency/bound.html
            //    "progress" is one of the bound variables (of int type) whose value range from 0 to 100 
            if (name.equals("progress")) {
              int progress = (int) evt.getNewValue();
              pbProgress.setValue(progress);
              repaint();
            } else if (name.equals("state")) {
              //kp: "state" is another bound variable (of SwingWorker.StateValue type) that has values 
              //    of "PENDING", "STARTED" and "DONE"
              SwingWorker.StateValue state = (SwingWorker.StateValue) evt
                  .getNewValue();
              
              switch (state) {
              case DONE:
                start.setEnabled(true);
                break;
              }
            }
          }
        });
        pw.execute();
      }
    });
  }
}