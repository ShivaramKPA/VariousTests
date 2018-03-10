/*
 * https://www.youtube.com/watch?v=zaQp3f6Bmf4
*  Learn Java Programming - wait(), .notify(), and .notifyAll() Tutorial
*  by Daniel Ross
*
* There are a total of 11 methods in the Object class. Three of those methods 
are overloaded versions of the .wait() method. Combine those three with the .notify() 
and the .notifyAll() methods and you have almost half of the Object class methods 
grouped up. What does this group of methods really do? They represent the primitive 
core communication methods between threads. I went back and forth on whether or not 
to create a tutorial on these methods because they are pretty much legacy now. The 
java.util.concurrent package contains many classes and methods that will handle 
communication between threads in a much more efficient manner. You need to have a 
general idea of how a Java monitor lock works so you can understand the necessary 
overhead that comes with using these methods. The Monitor Lock (Intrinsic lock) I 
am going to use a simple analogy of how a ship (boat) lock works in order to describe 
the stages of a monitor lock. The Panama Canal allows ships to cross back and forth 
from the Atlantic ocean to the Pacific ocean.

spu·ri·ous
ˈsp(y)o͝orēəs/
adjective
adjective: spurious

    not being what it purports to be; false or fake.
    "separating authentic and spurious claims"
    synonyms:	bogus, fake, false, counterfeit, forged, fraudulent, sham, artificial, imitation, simulated, feigned, deceptive, misleading, specious; More
    informalphony, pretend
    "an attempt to be excused due to some spurious medical condition"
    antonyms:	genuine
        (of a line of reasoning) apparently but not actually valid.
        "this spurious reasoning results in nonsense"
        archaic
        (of offspring) illegitimate.
 */
package varioustests.multithreadingtests;

public class WaitNotifyMonitorTest {

    public static void main(String[] args) {
        System.out.println("Hi from " + Thread.currentThread().getName()
                + " thread which has ID = " + Thread.currentThread().getId()
                + " and I am at the very start of the main() method.");
        CleanHair clean = new CleanHair(); //single object
        //The reference or handle to the same object 'clean' is passed to 
        // two threads (two more objects). These two objects are like works
        // that work on the first object 'clean'.
        //   Which means the two threads will be interacting/dealing with the 
        //   same/single object (not different copies).
        new HairThread("Lather", clean); //This thread object uses 'clean' object
        new HairThread("Rinse", clean);  //This thread to uses the same object

        /*
        kp: This program will have three objects in the Heap, one is 'clean' and the other
        two are the two threads for Lather and Rinse. And, the two threads interact with
        the 'clean' object in coordination with each other.
         */
        System.out.println("Hi from " + Thread.currentThread().getName()
                + " thread which has ID = " + Thread.currentThread().getId()
                + " and I am at the very end of the main() method.");
    }
}

class CleanHair {

    String currentState = ""; //object state

    synchronized void Lather() {
        currentState = "Lather";
        System.out.println("\t\t ######## Hi from: " + currentState);
        System.out.println("kp: which is running in " + Thread.currentThread().getName()
                + " thread with ID = " + Thread.currentThread().getId());
        notify();
        System.out.println("kp: Just called notify(), next calling wait() from synchronized Lather() method.");
        try {
            //In the following we can have 'wait();' without while loop but
            // it's better if we enclose in the while loop to avoid the spurous wakeup
            while (currentState.equals("Lather")) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void Rinse() {
        currentState = "Rinse";
        System.out.println("\t\t ######## Hi from: " + currentState);
        System.out.println("kp: which is running in " + Thread.currentThread().getName()
                + " thread with ID = " + Thread.currentThread().getId());
        notify();
        System.out.println("kp: Just called notify(), next calling wait()  from synchronized Rinse() method.");
        try {
            //In the following we can have 'wait();' without while loop but
            // it's better if we enclose in the while loop to avoid the spurous wakeup
            while (currentState.equals("Rinse")) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class HairThread extends Thread {

    String name; //Name of the thread (object)
    CleanHair cleanRef; //The reference to the CleanHair object that's passed to it when created.

    //Constructor
    HairThread(String name, CleanHair cleanRef) {
        this.name = name;
        this.cleanRef = cleanRef;

        setName(name); //method inherited from Thread class
        //https://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html
        //   setName(String name): Changes the name of this thread to be equal to the argument name.   

        start(); //This thread is started right when it's created. (calls run() method)
    }

    @Override
    public void run() {

        //The following two will talk back and forth between each other.
        //If we enable lines without while or for loop below, it will print Lather and Rinse
        // once and the 'Rinse' thread will be waiting and waiting because the other
        // thread will already have have finished it's job and exited.
        //
        // If we enable the for loops, it will do the same for 10 times and again will
        //   the 'Rinse' thread will be waiting for notify() from the other thread which
        //   will have already vanished as before (but after doing it's job 10 times)
        if (name.equals("Lather")) {
            //cleanRef.Lather();
            //while(true) { cleanRef.Lather(); } //Endless loop
            for (int i = 0; i < 10; i++) {
                cleanRef.Lather();
            }
        }

        if (name.equals("Rinse")) {
            //cleanRef.Rinse();
            //while(true) {cleanRef.Rinse();} //Endless loop
            for (int i = 0; i < 10; i++) {
                cleanRef.Rinse();
            }
        }
    }
}
