/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.concurrency;

/**
 *
 * @author https://www.tutorialspoint.com/javaexamples/thread_stop.htm
 */

import java.util.Timer;
import java.util.TimerTask;

class CanStop  extends Thread {
   private volatile boolean stop = false;
   private int counter = 0;
   
   public void run() {
      while (!stop && counter < 10000) {
         System.out.println(counter++);
      }
      if (stop)
      System.out.println("Detected stop"); 
   }
   public void requestStop() {
      stop = true;
   }
}

//public class Stopping {
public class StopThreadForAWhile {
   public static void main(String[] args) {
      final CanStop stoppable = new CanStop();
      stoppable.start();
      
      new Timer(true).schedule(new TimerTask() {
         public void run() {
            System.out.println("Requesting stop");
            stoppable.requestStop();
         }
      }, 
      350);
   }
} 
