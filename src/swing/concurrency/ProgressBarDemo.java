//https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ProgressBarDemoProject/src/components/ProgressBarDemo.java
package swing.concurrency;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
//package components;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.*;
import java.util.Random;
import java.util.Set;


public class ProgressBarDemo extends JPanel
        implements ActionListener,
        PropertyChangeListener {

    private JProgressBar progressBar;
    private JButton startButton;
    private JTextArea taskOutput;
    private Task task;

    //kp: as said in https://docs.oracle.com/javase/tutorial/uiswing/concurrency/worker.html
    //   SwingWorker is an abstract class and we must define its subclass as follows:
    // All concrete subclasses of SwingWorker implement doInBackground; implementation of done is optional.
    class Task extends SwingWorker<Void, Void> {

        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            while (progress < 100) {
                //Sleep for up to one second.
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException ignore) {
                }
                //Make random progress.
                progress += random.nextInt(10);
                setProgress(Math.min(progress, 100));
            }
            return null;
        }

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

    public ProgressBarDemo() {
        super(new BorderLayout());

        //Create the demo's UI.
        startButton = new JButton("Start");
        startButton.setActionCommand("start");
        startButton.addActionListener(this);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        taskOutput = new JTextArea(5, 20);
        taskOutput.setMargin(new Insets(5, 5, 5, 5));
        taskOutput.setEditable(false);

        JPanel panel = new JPanel();
        panel.add(startButton);
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
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //Instances of javax.swing.SwingWorker are not reusuable, so
        //we create new instances as needed.
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
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
     * Create the GUI and show it. As with all GUI code, this must run on the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
                System.out.println("Inside createAndShowGUI(): This is " 
                        + Thread.currentThread().getName() + " thread with ID = " 
                        + Thread.currentThread().getId() + " before calling setVisible(true);");        
        //Create and set up the window.
        JFrame frame = new JFrame("ProgressBarDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ProgressBarDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
                System.out.println("Inside createAndShowGUI(): This is " 
                        + Thread.currentThread().getName() + " thread with ID = " 
                        + Thread.currentThread().getId() + " after calling setVisible(true);");         
    }

    public static void main(String[] args) {
        //It will show that we're in the main thread with ID=1
        System.out.println("This is " + Thread.currentThread().getName()
                + " thread with ID = " + Thread.currentThread().getId());

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                System.out.println("This is " + Thread.currentThread().getName()
                        + " thread with ID = " + Thread.currentThread().getId()
                        + " just before calling createAndShowGUI();");

                createAndShowGUI();

                System.out.println("This is " + Thread.currentThread().getName()
                        + " thread with ID = " + Thread.currentThread().getId()
                        + " just after calling createAndShowGUI();");
            }
        });
        
        
        //showAllThreads();
        //showAllThreads2();
        showAllThreads3();        
    }
    
    public static void showAllThreads() {
        //https://www.tutorialspoint.com/javaexamples/thread_showall.htm
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        System.out.println("kp: # of threads = " + noThreads);
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);

        for (int i = 0; i < noThreads; i++) {
            System.out.println("Thread No:" + i + " = " + lstThreads[i].getName());
        }
    }

    public static void showAllThreads2() {
        //https://stackoverflow.com/questions/1323408/get-a-list-of-all-threads-currently-running-in-java
        Thread.getAllStackTraces().keySet().forEach((t) -> System.out.println(t.getName() 
                + "\nIs Daemon " + t.isDaemon() + "\nIs Alive " + t.isAlive()));
    }

    public static void showAllThreads3() {
        //http://www.codejava.net/java-core/concurrency/how-to-list-all-threads-currently-running-in-java
        //The following code snippet will list all threads that are currently running 
        // in the JVM along with their information like name, state, priority, and daemon status:        
        Set<Thread> threads = Thread.getAllStackTraces().keySet();

        System.out.println("####################################################");
        System.out.println("thread-name    thread-state   Id   priority       type");
        System.out.println("####################################################");
        for (Thread t : threads) {
            String name = t.getName();
            Thread.State state = t.getState();
            long Id = t.getId();
            int priority = t.getPriority();
            String type = t.isDaemon() ? "Daemon" : "Normal";
            System.out.printf("%-20s \t %s \t %d \t %d \t %s\n", name, state, Id, priority, type);
        }
        System.out.println("####################################################");
    }    
}
