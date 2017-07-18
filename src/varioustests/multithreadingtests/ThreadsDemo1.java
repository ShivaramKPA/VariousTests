/*
 * https://www.youtube.com/watch?v=0ySznjdXMEA
 * This code is to demonstrate the creating a thread by using one
 *   one of the two methods i.e. by subclassing the 'Thread' class
 *   and instantiating it's object and then calling 'start()' method
 *
 */
package varioustests.multithreadingtests;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kpadhikari
 */
class MyThreadSubclass extends Thread {

    //@Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet."); 
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getId() + " i=" + i);
        }

        try {
            //Without followign .sleep(t) line, the threads may run too fast
            //   and we may not feel like they are running in separate threads.
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(MyThreadSubclass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

public class ThreadsDemo1 {
    public static void main(String[] args) {
        MyThreadSubclass thread1 = new MyThreadSubclass();
        //if .run() is called below instead of start(), it wont create a thread, 
        // rather it will run like a normal program without any thread. 
        thread1.start(); 
        MyThreadSubclass thread2 = new MyThreadSubclass();
        thread2.start();
    }
}
