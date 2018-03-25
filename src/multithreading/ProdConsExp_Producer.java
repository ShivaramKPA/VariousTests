/*
 * https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html
 */
package multithreading;

/*
The producer thread, defined in ProdConsExp_Producer, sends a series of familiar messages. The 
string "DONE" indicates that all messages have been sent. To simulate the unpredictable
nature of real-world applications, the producer thread pauses for random intervals 
between messages.
*/

import java.util.Random;

public class ProdConsExp_Producer implements Runnable {
    private ProdConsExp_Drop drop;

    public ProdConsExp_Producer(ProdConsExp_Drop drop) {
        this.drop = drop;
    }

    public void run() {
        String importantInfo[] = {
            "Mares eat oats",
            "Does eat oats",
            "Little lambs eat ivy",
            "A kid will eat ivy too"
        };
        Random random = new Random();

        for (int i = 0;
             i < importantInfo.length;
             i++) {
            drop.put(importantInfo[i]);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {}
        }
        drop.put("DONE");
    }
}
