/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.concurrency;

/**
 *
 * @author kpadhikari
 */
public class StopWithVolatileFlagNoGUI {
    public static void main(String[] args) 
    {
        MyThreadWithVolatileFlag thread = new MyThreadWithVolatileFlag();
         
        thread.start();
         
        try
        {
            Thread.sleep(100);
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
         
        //call stopRunning() method whenever you want to stop a thread
         
        thread.stopRunning();
    }        
}
