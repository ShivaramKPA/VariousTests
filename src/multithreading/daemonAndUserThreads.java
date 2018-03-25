/*
*  kp: 3/9/18
 * This is mainly copied from the forum mentioned below (see comments after the code in this file)
* While working on the code "WaitNotifyMonitorTest.java", I realized that other threads could 
*   continue even after the main thread had finished (exited), so just to confirm that what
*   I was observing was really the case, I googled about it and landed on the forum that indicated
*   that as long as there is at least one user (or non-daemon) thread running, the whole program
*   would continue to run even if the main() thread exits (the main thread is one of the user threads)
*
*
*  https://stackoverflow.com/questions/5642802/termination-of-program-on-main-thread-exit
*   Java programs terminate when all non-daemon threads finish.

The documentation states:

    When a Java Virtual Machine starts up, there is usually a single non-daemon thread (which 
typically calls the method named main of some designated class). The Java Virtual Machine continues 
to execute threads until either of the following occurs:

        The exit method of class Runtime has been called and the security manager has permitted 
the exit operation to take place.
        All threads that are not daemon threads have died, either by returning from the call to 
the run method or by throwing an exception that propagates beyond the run method.

If you don't want the runtime to wait for a thread, call the setDaemon method.
===========

https://www.geeksforgeeks.org/daemon-thread-java/ 

Daemon vs User Threads

    Priority: When the only remaining threads in a process are daemon threads, the interpreter exits. 
This makes sense because when only daemon threads remain, there is no other thread for which a daemon 
thread can provide a service.

    Usage: Daemon thread is to provide services to user thread for background supporting task.

 */
package multithreading;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kpadhikari
 */
public class daemonAndUserThreads {
//public class Spawner {

    public static void main(String[] args) {
        System.out.println("This is " + Thread.currentThread().getName() 
                + " thread with ID = " + Thread.currentThread().getId());
        //https://www.geeksforgeeks.org/daemon-thread-java/
        // Checking whether the thread is Daemon or not
        if (Thread.currentThread().isDaemon()) {
            System.out.println("This main thread is a Daemon thread");
        } else {
            System.out.println("This is User thread i.e non-Daemon thread. Remember main-thread is never Daemon.");
        }

        Thread t = new Thread(new Runnable() {
            public void run() {
                //while (true) {
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(1);//Try changing the sleep time when it's a daemon thread
                    } catch (InterruptedException ex) {
                        //Logger.getLogger(daemonAndUserThreads.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
                    }
                    System.out.println("I'm still alive");

                    //https://www.geeksforgeeks.org/daemon-thread-java/
                    // Checking whether the thread is Daemon or not
                    if (Thread.currentThread().isDaemon()) {
                        System.out.println("This is Daemon thread");
                    } else {
                        System.out.println("This is User thread i.e non-Daemon thread");
                    }
                }
            }
        });

        // t.start(); 
        // Try uncommenting/commenting this line
        //t.setDaemon(true);
        t.start(); //we cannot call the setDaemon() method after starting the thread.

        System.out.println("Main thread has finished");
    }
}

/*
https://stackoverflow.com/questions/31252227/how-does-daemon-thread-survive-after-jvm-exits 
################
Question:
################
I am reading docs about Java's setDaemon() method, and got confused when I read that
JVM exits without waiting for daemon threads to finish.

However, since essentially daemon threads are Java Thread's, which presumably rely on 
running on JVM to achieve its functionalities, how do daemon threads even survive if 
JVM exits before the daemon thread finishes?

################
Answer:
################
They don't survive. The JVM will exit when all the threads, except the daemon ones, have died.

When you start your application, the JVM will start a single, non-daemon thread to run your static main method.

Once the main method exits, this main thread will die, and if you spawned no other non-daemon thread, the JVM will exit.

If however you started another thread, the JVM will not exit, it will wait for all the non-daemon threads to die before exiting.

If that thread you spawned is doing something vital, this is absolutely the right thing to do, however often you have some threads that are not that vital, maybe they are listening to some external event that may or may not happen.

So, in theory, you should place some code somewhere to stop all the threads you spawned to allow the JVM to exit.

Since this is error prone, it's way easier to mark such non-vital threads as daemons. If they are marked as such, the JVM will not wait for them to die before exiting, the JVM will exit and kill those threads when the "main threads" (those not marked as daemon) have died.

To put it in code, it's something like this :

public class Spawner {
  public static void main(String[] args) {
    Thread t = new Thread(new Runnable() {
      public void run() {
        while (true) {
          System.out.println("I'm still alive");
        }
      }
    });
    t.start();
    // Try uncommenting/commenting this line
    // t.setDaemon(true);
    System.out.println("Main thread has finished");
  }
}

(I haven't tested this code, wrote it here directly, so it could contain stupid mistakes).

When running this code with the line commented, the thread is not deamon, so even if your main method has finished, you'll keep on having the console flooded until you stop it with CTRL+C. That is, the JVM will not exit.

If you uncomment the line, then the thread is a daemon, and soon after the main method has finished, the thread will be killed and the JVM will exit, without the need for CTRL+C.
 */
