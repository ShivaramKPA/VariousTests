/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.concurrency;

/**
 *
 * @author http://www.javaworld.com/article/2077754/core-java/swing-threading-and-the-event-dispatch-thread.html?page=5
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GoodSwingButton {
  public static void main(String args[]) {
    Runnable runner = new Runnable() {
      public void run() {
        JFrame frame = new JFrame("Title");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton button = new JButton("Press Here");
        ContainerListener container = new ContainerAdapter() {
          public void componentAdded(final ContainerEvent e) {
            SwingWorker worker = new SwingWorker() {
              protected String doInBackground() throws InterruptedException {
                return null;
              }
              protected void done() {
                System.out.println("On the event thread? : " +
                  EventQueue.isDispatchThread());
                JButton button = (JButton)e.getChild();
                String label = button.getText();
                button.setText(label + "0");
              }
            };
            worker.execute();
          }
        };
        frame.getContentPane().addContainerListener(container);
        frame.add(button, BorderLayout.CENTER);
        frame.setSize(200, 200);
        System.out.println("I'm about to be realized: " +
          EventQueue.isDispatchThread());
        frame.setVisible(true);
      }
    };
    EventQueue.invokeLater(runner);
 }
}
