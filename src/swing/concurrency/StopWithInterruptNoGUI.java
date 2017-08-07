/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.concurrency;

/**
 *
 * @author http://javaconceptoftheday.com/how-to-stop-a-thread-in-java/
 */
 
public class StopWithInterruptNoGUI 
{       
    public static void main(String[] args) 
    {
        MyThreadForIntterruptTest thread = new MyThreadForIntterruptTest();
         
        thread.start();
         
        try
        {
            Thread.sleep(100);
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
         
        //interrupting the thread
         
        thread.interrupt();
    }    
}