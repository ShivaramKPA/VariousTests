/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kpadhikari Example copied from
 * https://www.youtube.com/watch?v=XjMCgirgWeA Java Multithreading Lecture 5:
 * Thread Pools
 */
public class ThreadPoolDemo {

    public static void main(String[] args) {
        //We'll have a pool of 2 threads to complete various tasks
        //   These 2 threads are like two factory works assigned some 
        //   amount/number of tasks 
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        for (int i = 0; i < 5; i++) {
            executor.submit(new Processor(i));
        }
        
        //kp: executor will close after submission but threads may continue
        executor.shutdown(); 
        System.out.println("All tasks submitted");
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            //Logger.getLogger(ThreadPoolDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class Processor implements Runnable {

    private int id;

    //Constructor 
    public Processor(int id) {
        this.id = id;
    }

    public void run() {
        System.out.println("Starting task " + id
            + " in Thread " +  Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            //Logger.getLogger(Processor.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        System.out.println("Completed task " + id
             + " in Thread " +  Thread.currentThread().getName());
    }
}
