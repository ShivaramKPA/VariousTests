/*
https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html

 * Finally, here is the main thread, defined in ProducerConsumerExample, that 
launches the producer and consumer threads.
 */
package multithreading;



//public class ProducerConsumerExample {
public class ProdConsExp_Main {
    public static void main(String[] args) {
        ProdConsExp_Drop drop = new ProdConsExp_Drop();
        (new Thread(new ProdConsExp_Producer(drop))).start();
        (new Thread(new ProdConsExp_Consumer(drop))).start();
    }
}
/*
Note: The ProdConsExp_Drop class was written in order to demonstrate guarded blocks. To avoid 
re-inventing the wheel, examine the existing data structures in the Java Collections 
Framework (https://docs.oracle.com/javase/tutorial/collections/index.html) 
before trying to code your own data-sharing objects. For more information, 
refer to the Questions and Exercises (https://docs.oracle.com/javase/tutorial/essential/concurrency/QandE/questions.html) 
section. 
*/