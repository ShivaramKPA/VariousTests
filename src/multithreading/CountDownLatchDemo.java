/*
 https://www.geeksforgeeks.org/countdownlatch-in-java/
CountDownLatch in Java

CountDownLatch is used to make sure that a task waits for other threads before it 
starts. To understand its application, let us consider a server where the main task 
can only start when all the required services have started.

Working of CountDownLatch:
When we create an object of CountDownLatch, we specify the number if threads it 
should wait for, all such thread are required to do count down by calling 
CountDownLatch.countDown() once they are completed or ready to the job. As soon as 
count reaches zero, the waiting task starts running.

Facts about CountDownLatch:

    Creating an object of CountDownLatch by passing an int to its constructor (the 
        count), is actually number of invited parties (threads) for an event.
    The thread, which is dependent on other threads to start processing, waits on until 
        every other thread has called count down. All threads, which are waiting on 
        await() proceed together once count down reaches to zero.
    countDown() method decrements the count and await() method blocks until count == 0


Example of CountDownLatch in JAVA:
 */
package multithreading;

/* Java Program to demonstrate how to use CountDownLatch,
   Its used when a thread needs to wait for other threads
   before starting its work. */
import java.util.concurrent.CountDownLatch;
 
public class CountDownLatchDemo
{
    public static void main(String args[]) throws InterruptedException
    {
        // Let us create task that is going to wait for four
        // threads before it starts
        CountDownLatch latch = new CountDownLatch(4);
 
        // Let us create four worker threads and start them.
        //kp: These four threads will run in parallel, while 'main' awaits for them to finish
        MyWorker first = new MyWorker(4000, latch, "WORKER-1");
        MyWorker second = new MyWorker(3000, latch, "WORKER-2");
        MyWorker third = new MyWorker(3000, latch, "WORKER-3");
        MyWorker fourth = new MyWorker(3000, latch, "WORKER-4");
        first.start();
        second.start();
        third.start();
        fourth.start();
 
        // The main task waits for four threads
        latch.await();
 
        // Main thread has started
        System.out.println(Thread.currentThread().getName() +
                           " has finished");
    }
}
 
// A class to represent threads for which the main thread
// waits.
class MyWorker extends Thread
{
    private int delay;
    private CountDownLatch latch;
 
    public MyWorker(int delay, CountDownLatch latch,
                                     String name)
    {
        super(name); //kp: Pass the name to the constructor of the parent/super class 'Thread'
        this.delay = delay;
        this.latch = latch;
    }
 
    @Override
    public void run()
    {
        try
        {
            String thName = Thread.currentThread().getName();
            Thread.sleep(delay);
            latch.countDown();
            System.out.println(thName + " finished");
            //Thread.sleep(200);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
