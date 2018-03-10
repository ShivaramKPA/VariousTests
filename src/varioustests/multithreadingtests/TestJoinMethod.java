/*
 * https://www.youtube.com/watch?v=vLjucKGR654 JOIN() METHOD : JAVA MULTITHREADING TUTORIALS
*  Join() method ensures termination of thread which calls its join method 
*   before any other thread such as (main thread) terminates.
*   This method can be used to decide sequence of thread's execution.
* 
*  Whenever a thread calls the join() method from another thread, the 'hosting
*    thread' (from which the call is made) has to wait until that thread (whose 
*    join method is called) completes it's execution.
*
 */

 /*        
        // kp:
        // https://docs.oracle.com/javase/tutorial/essential/concurrency/join.html

        The join method allows one thread to wait for the completion of another. If t is 
        a Thread object whose thread is currently executing,
        
        t.join();

        causes the current thread to pause execution until t's thread terminates. 
        Overloads of join allow the programmer to specify a waiting period. However,
        as with sleep, join is dependent on the OS for timing, so you should not assume 
        that join will wait exactly as long as you specify.

        Like sleep, join responds to an interrupt by exiting with an InterruptedException.
        
        https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html
        join() : Waits for this thread to die.
        join(long millis) : Waits at most millis milliseconds for this thread to die.
        join(long millis, int nanos)  : Waits at most millis milliseconds plus nanos 
        nanoseconds for this thread to die.
 */

package varioustests.multithreadingtests;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kpadhikari
 */
class NewThread implements Runnable {

    public void run() {
        try {
            //Let's make this thread sleep for 5 secs
            Thread.sleep(5000);//5000 milliseconds

            System.out.println("New Thread Terminating ...");
        } catch (InterruptedException e) {
            System.out.println("Exception ...");
        }
    }
}

public class TestJoinMethod {

    public static void main(String[] args) throws InterruptedException {

        Thread th = new Thread(new NewThread());
        th.start();

        //Try running this programm enabling and disabling the following line
        th.join();

        System.out.println("Main Thread Terminating ...");
    }

}
