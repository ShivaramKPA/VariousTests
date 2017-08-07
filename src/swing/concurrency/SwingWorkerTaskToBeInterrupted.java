/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.concurrency;

import java.awt.Toolkit;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author kpadhikari
 */
//kp: as said in https://docs.oracle.com/javase/tutorial/uiswing/concurrency/worker.html
//   SwingWorker is an abstract class and we must define its subclass as follows:
// All concrete subclasses of SwingWorker implement doInBackground; implementation of done is optional.
public class SwingWorkerTaskToBeInterrupted extends SwingWorker<Void, Void> {
    //private volatile boolean stopFlag = false; //kp:
    private JPanel panel; //JFrame frame;
    private JButton startButton;
    private JTextArea taskOutput;
    //Constructor
    //public SwingWorkerTaskToBeInterrupted (JFrame frame, JButton startButton, JTextArea taskOutput) {
    public SwingWorkerTaskToBeInterrupted (JPanel panel, JButton startButton, JTextArea taskOutput) {
        this.panel = panel;
        this.startButton = startButton;
        this.taskOutput = taskOutput;
    }
    
    /*
         * Main task. Executed in background thread.
     */
    @Override
    public Void doInBackground()  throws Exception {
        Random random = new Random();
        int progress = 0;
        int nextInt = 0; //kp: 

        //Initialize progress property.
        setProgress(0);
        while (progress < 100) {
//            if (stopFlag) {
//                break;
//            }
            //Sleep for up to one second.
            try {
                nextInt = random.nextInt(2000);
                System.out.println("try: nextInt=" + nextInt);
                Thread.sleep(nextInt);
            } catch (InterruptedException ignore) {
            }

            //Make random progress.
            nextInt = random.nextInt(10);
            progress += nextInt;
            System.out.println("prog = " + progress + ", nextInt=" + nextInt);
            setProgress(Math.min(progress, 100));
        }
        return null;
    }

    /*
        * https://www.tutorialspoint.com/java/util/random_nextint_inc_exc.htm
        *  The nextInt(int n) method is used to get a pseudorandom, uniformly distributed 
        *  int value between 0 (inclusive) and the specified value (exclusive), drawn from 
        *  this random number generator's sequence.
     */

 /*
         * Executed in event dispatching thread
     */
    //kp: As said in https://docs.oracle.com/javase/tutorial/uiswing/concurrency/worker.html
    //   this method is executed automatically when doInBackground() is completed.
    @Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
        startButton.setEnabled(true);
        panel.setCursor(null); //turn off the wait cursor
        taskOutput.append("Done!\n");
    }
}
