/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.concurrency;

/**
 * Used by StopWithInterruptNoGUI class
 * @author kpadhikari
 */
public class MyThread extends Thread {    
    @Override
    public void run()
    {
        while (!Thread.interrupted())
        {
            System.out.println("I am running....");
        }
         
        System.out.println("Stopped Running.....");
    }
}
