/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varioustests.swingworkertests;

//package com.javacreed.examples.swing.worker.part2;//http://www.javacreed.com/swing-worker-example/

import java.util.List;

import javax.swing.SwingWorker;

public class MyBlankWorker extends SwingWorker<Integer, String> {

  @Override
  protected Integer doInBackground() throws Exception {
    // Start
    publish("Start");
    setProgress(1);
    
    // More work was done
    publish("More work was done");
    setProgress(10);

    // Complete
    publish("Complete");
    setProgress(100);
    return 1;
  }
  
  @Override
  protected void process(List<String> chunks) {
    // Messages received from the doInBackground() (when invoking the publish() method)
  }
}