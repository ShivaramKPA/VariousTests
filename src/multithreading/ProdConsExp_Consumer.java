/*
 * https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html
The consumer thread, defined in ProdConsExp_Consumer, simply retrieves the messages and prints 
them out, until it retrieves the "DONE" string. This thread also pauses for random 
intervals.

 */
package multithreading;



import java.util.Random;

public class ProdConsExp_Consumer implements Runnable {
    private ProdConsExp_Drop drop;

    public ProdConsExp_Consumer(ProdConsExp_Drop drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();
        for (String message = drop.take();
             ! message.equals("DONE");
             message = drop.take()) {
            System.out.format("MESSAGE RECEIVED: %s%n", message);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {}
        }
    }
}


