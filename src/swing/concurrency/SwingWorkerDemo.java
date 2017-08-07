/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.concurrency;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author Jonas https://stackoverflow.com/questions/8083768/stop-cancel-swingworker-thread
 */

public class SwingWorkerDemo extends JFrame {
    private boolean isStarted = false;
    private JLabel counterLabel = new JLabel("Not started");
    private Worker worker;// = new Worker(); //kp: If this 'new object creation' line is here, we can start the 'work' only once
    private JButton startButton = new JButton(new AbstractAction("Start") {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            //kp: If this worker thread is not started already, start it & reset the value of the flag to false.
            if(!isStarted) {
                worker = new Worker(); //kp: If this 'new object creation' line is here, we can start the 'work' on each button click
                
                worker.execute();
                isStarted = false;
            }
        }

    });
    
    private JButton stopButton = new JButton(new AbstractAction("Stop") {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            worker.cancel(true);
        }

    });

    public SwingWorkerDemo() {

        add(startButton, BorderLayout.WEST);
        add(counterLabel, BorderLayout.CENTER);
        add(stopButton, BorderLayout.EAST);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    class Worker extends SwingWorker<Void, Integer> {

        int counter = 0;

        @Override
        protected Void doInBackground() throws Exception {
            while(true) {
                counter++;
                publish(counter);
                Thread.sleep(60);
            }
        }

        @Override
        protected void process(List<Integer> chunk) {

            // get last result
            Integer counterChunk = chunk.get(chunk.size()-1);

            counterLabel.setText(counterChunk.toString());
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new SwingWorkerDemo();
            }

        });
    }

}