/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.concurrency;

/**
 *  Used by StopWithVolatileFlag class
 * @author http://javaconceptoftheday.com/how-to-stop-a-thread-in-java/
 * 
 */
public class MyThreadWithVolatileFlagNoGUI extends Thread {
    //Initially setting the flag as true
     
    private volatile boolean flag = true;
     
    //This method will set flag as false
     
    public void stopRunning()
    {
        flag = false;
    }
     
    @Override
    public void run()
    {
        //Keep the task in while loop
         
        //This will make thread continue to run until flag becomes false
         
        while (flag)
        {
            System.out.println("I am running....");
        }
         
        System.out.println("Stopped Running....");
    }
}