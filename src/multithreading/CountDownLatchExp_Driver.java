/*
https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CountDownLatch.html
public class CountDownLatch
extends Object
A synchronization aid that allows one or more threads to wait until a set of operations
being performed in other threads completes.

A CountDownLatch is initialized with a given count. The await methods block until the 
current count reaches zero due to invocations of the countDown() method, after which 
all waiting threads are released and any subsequent invocations of await return 
immediately. This is a one-shot phenomenon -- the count cannot be reset. If you need 
a version that resets the count, consider using a CyclicBarrier.

A CountDownLatch is a versatile synchronization tool and can be used for a number of 
purposes. A CountDownLatch initialized with a count of one serves as a simple on/off 
latch, or gate: all threads invoking await wait at the gate until it is opened by a 
thread invoking countDown(). A CountDownLatch initialized to N can be used to make one 
thread wait until N threads have completed some action, or some action has been completed
N times.

A useful property of a CountDownLatch is that it doesn't require that threads calling 
countDown wait for the count to reach zero before proceeding, it simply prevents any 
thread from proceeding past an await until all threads could pass. 

Sample usage: Here is a pair of classes in which a group of worker threads use two countdown latches:

    The first is a start signal that prevents any worker from proceeding until the driver is ready for them to proceed;
    The second is a completion signal that allows the driver to wait until all workers have completed. 
*/
package multithreading;

import java.util.concurrent.CountDownLatch;

 class CountDownLatchExp_Driver { // ...
   void main() throws InterruptedException {
     CountDownLatch startSignal = new CountDownLatch(1);
     CountDownLatch doneSignal = new CountDownLatch(N);

     for (int i = 0; i < N; ++i) // create and start threads
       new Thread(new Worker(startSignal, doneSignal)).start();

     doSomethingElse();            // don't let run yet
     startSignal.countDown();      // let all threads proceed
     doSomethingElse();
     doneSignal.await();           // wait for all to finish
   }
 }

 class Worker implements Runnable {
   private final CountDownLatch startSignal;
   private final CountDownLatch doneSignal;
   Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
      this.startSignal = startSignal;
      this.doneSignal = doneSignal;
   }
   public void run() {
      try {
        startSignal.await();
        doWork();
        doneSignal.countDown();
      } catch (InterruptedException ex) {} // return;
   }

   void doWork() { 
       System.out.println("kp: Hi there.");
   }
 }

/*
AAnother typical usage would be to divide a problem into N parts, describe each part 
with a Runnable that executes that portion and counts down on the latch, and queue 
all the Runnables to an Executor. When all sub-parts are complete, the coordinating 
thread will be able to pass through await. (When threads must repeatedly count down 
in this way, instead use a CyclicBarrier.)

 class Driver2 { // ...
   void main() throws InterruptedException {
     CountDownLatch doneSignal = new CountDownLatch(N);
     Executor e = ...

     for (int i = 0; i < N; ++i) // create and start threads
       e.execute(new WorkerRunnable(doneSignal, i));

     doneSignal.await();           // wait for all to finish
   }
 }

 class WorkerRunnable implements Runnable {
   private final CountDownLatch doneSignal;
   private final int i;
   WorkerRunnable(CountDownLatch doneSignal, int i) {
      this.doneSignal = doneSignal;
      this.i = i;
   }
   public void run() {
      try {
        doWork(i);
        doneSignal.countDown();
      } catch (InterruptedException ex) {} // return;
   }

   void doWork() { ... }
 }

 

Memory consistency effects: Until the count reaches zero, actions in a thread prior 
to calling countDown() happen-before actions following a successful return from a 
corresponding await() in another thread.
*/ 