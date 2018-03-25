/*
 * http://www.ryanhmckenna.com/2014/12/multi-core-programming-with-java.html
 Multi-Core Programming with Java
December 29, 2014
Processors speeds are no longer growing at the rate we've grown accustomed to. 
Moore's law states that computers double in speed every 18 months. Moore's law is 
still alive and well, but we just need to find alternate ways to speed up computers.
That's where multi-core programming comes into play. The idea behind it is that 2 
heads are better than one and in the right situation 2 processors can work on the 
same problem to solve it twice as fast. Parallel programming is becoming increasingly 
important these days and there are many different flavors of it including distributed 
computing, shared memory computing, and GPU computing. Today I am going to focus on 
shared memory computing, or using multi-core computers rather than many single core 
computers. This blog post is intended to serve as a beginners guide to parallel 
programming with Java. It is recommended that the reader have some java experience 
and is familiar with some of the features of Java 8 (namely lambda expressions and 
streams). Most modern day computers are multi-core and 4 cores is the norm from what 
I've seen, but depending on your computer you may have more or less.

The Problem

I will demonstrate multi-core programming with java by means of example. For 
simplicity and demonstration purposes, I am going to use the Monte Carlo method for 
approximating Pi. If you are unfamiliar with the algorithm, I created an animation 
to demonstrate how it works:

... Animated Picture ...
... The animated picture shows dots/points thrown randomly onto a square box (in 
..  of side length = 1), in the first quadrant of the coordinate space. The dots
..  which have sqrt(x2 + y2) <= 1 is given green color and the rest is given 'red'.
... These colored dots gradually fill up both the reasons and we want to calculate
... the ratio of the numbers of the dots in the two areas, which should be equal to 
..   pi/4.


 Note that you can restart the animation by pressing the Space Bar. The algorithm 
works by taking a random sample of points from a uniform distribution with 0<x,y<1 
and determining if that point lies within the first quadrant of the unit circle 
centered at (0,0). If it does, then that is considered a success. If the random number 
generator is truly uniform, then success/total≈π/4, since the area of the first quadrant 
of the unit circle is π/4 and the total area of the unit cube is 1.  

Java Implementation

The Java Runnable interface is designed for handling parallel and concurrent programs.

...
There are a few things to note about this implementation. First, the information about 
what is being computed is being passed in to the constructor. In this case, that is 
just the number of trials to compute. Second, the CountDownLatch is a java class in 
the java.util.concurrent package. It is a mechanism to safely handle counting the 
number of completed tasks. You should call latch.countDown() whenever the run method 
competes.

 So now we have a Runnable class that can handle a single set of trials. Now, I want 
to create multiple instances of this Runnable and let my quad-core computer quadruple 
the performance! The ExecutorService is exactly what we need to accomplish this. 

The ExecutorService class enables you to easily run and execute Runnables (and Callables). 
It is created with the Executors.newFixedThreadPool(threads) method. The input argument 
is the number of threads you want in your pool. I typically like to choose this number 
to be twice as many cores on my computer, but depending on the situation you might want 
to do something different. If you expect each job to take the same amount of time, then 
it's a good idea to make this a multiple of the number of cores on your computer. If the 
amount of time for each job varies based on the input, then it's a good idea to set this 
value to be larger so that 1 job wont be a major bottleneck. Next, you need to instantiate 
a CountDownLatch with the same number of jobs as before. Next, you need to create the jobs; 
I store them in an array so I can collect the results at the end. If your runnable just 
updates a global variable, then it's not necessary to store these runnables objects. 
exec.execute(r) adds r to the thread pool. After all the runnables have been added to 
the pool, you need to call latch.await() which blocks until the latch reaches 0 (indicating
all the compute units finished computing). Then, it's safe to shutdown the ExecutorService 
and collect the results.

Conclusion

The above code should provide a minimal working parallel program for you to play around 
with. You can tinker with the existing code or implement your own Runnable class and try 
it out for yourself. You'll need to have Java 8 installed but other than that it should 
be good to go!
 */
package multithreading.Executors;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

class EvalPi_ComputeUnit implements Runnable {

    private int trials, success;
    private CountDownLatch latch;

    public EvalPi_ComputeUnit(int trials) {
        this.trials = trials;
        this.success = 0;
    }

    public int getTrials() {
        return trials;
    }

    public int getSuccess() {
        return success;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("kp: Thread Name & ID: " + Thread.currentThread().getName()
                + ", " + Thread.currentThread().getId());
        for (int i = 0; i < trials; i++) {
            double x = ThreadLocalRandom.current().nextDouble();
            double y = ThreadLocalRandom.current().nextDouble();
            if (x * x + y * y < 1) {
                success++;
            }
        }
        latch.countDown();
    }
}
