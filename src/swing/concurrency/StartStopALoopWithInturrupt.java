/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.concurrency;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author kpadhikari
 */
public class StartStopALoopWithInturrupt  extends JPanel 
                                            implements PropertyChangeListener{
    private static JFrame frame;
    private JProgressBar progressBar;
    private JButton startButton;
    private JButton stopButton;
    private JTextArea taskOutput;
    private SwingWorkerTaskToBeInterrupted task;   
    MyThreadForIntterruptTest thread;
    
    public StartStopALoopWithInturrupt() {
        super(new BorderLayout());

        //Create the demo's UI.
        startButton = new JButton("Start");
        startButton.setActionCommand("start");
        //startButton.addActionListener(this);
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

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
        
        //Now create an object of the SwingWorkerTask class (to be employed to run a background task (a loop here))
        //task = new SwingWorkerTaskToBeInterrupted (this, startButton, taskOutput);
    }    

    /**
     * Invoked when the user presses the stop button.
     */
    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        taskOutput.append("Hi!!! You clicked the Start button.\n");
        startButton.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); //A 
        //setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //kp
        //setCursor(Cursor.getPredefinedCursor(Cursor.CUSTOM_CURSOR));//crashes
        
//        //Instances of javax.swing.SwingWorker are not reusuable, so
//        //we create new instances as needed.
//        task = new SwingWorkerTaskToBeInterrupted(this, startButton, taskOutput);
//        task.addPropertyChangeListener(this);
//        task.execute();

    
        //couldn't kill swingworker task with task.canell(true) from the stop button
        //  therefore, I am instead using a subclass (extension) of Thread rather than SwingWorker
        thread = new MyThreadForIntterruptTest();
         
        thread.start();
         
        try
        {
            Thread.sleep(100);
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Invoked when the user presses the stop button.
     */
    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {
        taskOutput.append("Hi!!! You clicked the Stop button.\n");
//        if(!(task==null)) //only if the task object has been created already (otherwise there will be errors)
//            task.stopFlag = true;
        //task.cancel(true);
        thread.interrupt();
        startButton.setEnabled(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); //A 
        JOptionPane.showMessageDialog(frame, "Krishna (8/4/17): Didn't work quite like I had thought.");
    } 
    
    
    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
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
        frame = new JFrame("StartStopALoop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        //Following class extends JPanel and adds several elements such as buttons 
        //   and fields to it, adds properties (such as listeners). All this is done
        //   in the constructor of this class.
        //JComponent newContentPane = new StartStopALoopWithVolatileFlag();
        JComponent newContentPane = new StartStopALoopWithInturrupt();
        newContentPane.setOpaque(true); //content panes must be opaque
        
        //Now add the panel (created out of above object creation) to the frame.
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
