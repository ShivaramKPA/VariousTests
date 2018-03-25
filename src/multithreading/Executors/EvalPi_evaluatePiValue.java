/*
 * http://www.ryanhmckenna.com/2014/12/multi-core-programming-with-java.html
 */
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
*/
package multithreading.Executors;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author kpadhikari
 */
public class EvalPi_evaluatePiValue {

    static double montecarlo(int numOfThreads, int trials) throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(numOfThreads);
        CountDownLatch latch = new CountDownLatch(numOfThreads);
        EvalPi_ComputeUnit[] runnables = new EvalPi_ComputeUnit[numOfThreads];

        /*
        //https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html
        static void 	    setAll(double[] array, IntToDoubleFunction generator)
        static void 	    setAll(int[] array, IntUnaryOperator generator)
        static void 	    setAll(long[] array, IntToLongFunction generator)    
        static <T> void     setAll(T[] array, IntFunction<? extends T> generator)
        *         Set all elements of the specified array, using the provided generator 
        *         function to compute each element.          
        */
        //kp: Initializing above created runnables array, using lambda expression
        //kp: Below 'n' can be replaced with 'anything' - a, b, c, d, whatever or anything.
        Arrays.setAll(runnables, n -> new EvalPi_ComputeUnit(trials)); 
        
        for (EvalPi_ComputeUnit r : runnables) {
            r.setLatch(latch);
            exec.execute(r);
        }

        System.out.println("kp: Thread Name & ID: " + Thread.currentThread().getName()
                + ", " + Thread.currentThread().getId());

        latch.await();
        exec.shutdown();

        int success = Arrays.stream(runnables).mapToInt(r -> r.getSuccess()).sum();
        int total = numOfThreads * trials;
        double pi = 4.0 * success / total;
        return pi;
    }

    public static void main(String[] args) throws InterruptedException {
        int processors = Runtime.getRuntime().availableProcessors();//kp:
        System.out.println("CPU cores: " + processors); //kp:

        //kp: the second argument in montecarlo is the iteration no.
        //    Higher the number, better the precision.
        System.out.println(" pi = " + montecarlo(8, 1000000));
    }
}
