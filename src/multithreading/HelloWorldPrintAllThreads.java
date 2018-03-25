/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multithreading;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kpadhikari
 */
public class HelloWorldPrintAllThreads {
    
    public static void main(String[] args) {
        System.out.println("Hello World!!");
        Constants.showAllThreads();
        System.out.println("Now, I am going to use sleep(60000) (6s pause)");
        
        try {
            sleep(120000);//Sleeping for 60000 milliseconds or 60 secs.
        } catch (InterruptedException ex) {
            Logger.getLogger(HelloWorldPrintAllThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
