//https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ProgressBarDemoProject/src/components/ProgressBarDemo.java
package swing.concurrency;


//package components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.*;
import java.util.Random;

public class StartStopALoopWithVolatileFlag extends JPanel
                             implements ActionListener, 
                                        PropertyChangeListener {

    private JProgressBar progressBar;
    private JButton startButton;
    private JButton stopButton;
    private JTextArea taskOutput;
    private Task task;    

    //kp: as said in https://docs.oracle.com/javase/tutorial/uiswing/concurrency/worker.html
    //   SwingWorker is an abstract class and we must define its subclass as follows:
    // All concrete subclasses of SwingWorker implement doInBackground; implementation of done is optional.
    class Task extends SwingWorker<Void, Void> {
        private volatile boolean stopFlag = false; //kp:

        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            int nextInt = 0; //kp: 
            
            //Initialize progress property.
            setProgress(0);
            while (progress < 100) {
                if(stopFlag) break;
                //Sleep for up to one second.
                try {
                    nextInt = random.nextInt(2000); System.out.println("try: nextInt=" + nextInt);
                    Thread.sleep(nextInt);
                } catch (InterruptedException ignore) {}
                
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
            setCursor(null); //turn off the wait cursor
            taskOutput.append("Done!\n");
        }
    }

    public StartStopALoopWithVolatileFlag() {
        super(new BorderLayout());

        //Create the demo's UI.
        startButton = new JButton("Start");
        startButton.setActionCommand("start");
        startButton.addActionListener(this);
        //kp:
        stopButton = new JButton("Stop");
        stopButton.setActionCommand("stop"); //Not essential for now (not used) 8/2/17
        //stopButton.addActionListener(this); //This is already used by Start button        
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        taskOutput = new JTextArea(5, 30);
        taskOutput.setMargin(new Insets(5,5,5,5));
        taskOutput.setEditable(false);

        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(stopButton);
        panel.add(progressBar);

        add(panel, BorderLayout.PAGE_START);
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    /**
     * Invoked when the user presses the start button.
     */
    public void actionPerformed(ActionEvent evt) {
        startButton.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //A 
        //setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //kp
        //setCursor(Cursor.getPredefinedCursor(Cursor.CUSTOM_CURSOR));//crashes
        
        //Instances of javax.swing.SwingWorker are not reusuable, so
        //we create new instances as needed.
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
    }

    /**
     * Invoked when the user presses the stop button.
     */
    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        //System.out.println("Hi!!! You clicked the Stop button.");
        taskOutput.append("Hi!!! You clicked the Stop button.\n");
        if(!(task==null)) //only if the task object has been created already (otherwise there will be errors)
            task.stopFlag = true;
    }
    
    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("evt.getPropertyName = " + evt.getPropertyName());//kp
        //kp: http://docs.oracle.com/javase/tutorial/uiswing/concurrency/bound.html
        //    "progress" is one of the bound variables (of int type) whose value range from 0 to 100 
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            taskOutput.append(String.format(
                    "Completed %d%% of task.\n", task.getProgress()));
        } 
    }


    /**
     * Create the GUI and show it. As with all GUI code, this must run
     * on the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("StartStopALoop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new StartStopALoopWithVolatileFlag();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

