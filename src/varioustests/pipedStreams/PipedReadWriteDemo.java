/* 
* Code copied from 
* https://www.youtube.com/watch?v=euQvR81fXgY 
* Java Tutorial : Java IO (PipedInputStream and PipedOutputStream-Introduction)
*
* Here, we have created two threads - thread1 and thread2. The thread1 thread 
*   writes the data using the PipedOutputStream object and the thread2 thread 
*   reads the data from that pipe using the PipedInputStream object. Both the 
*   piped stream object are connected with each other.
* 
*/
package varioustests.pipedStreams;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 *
 * @author kpadhikari
 */
public class PipedReadWriteDemo {
    
    public static void main(String[] args) throws IOException { 
            
        final PipedOutputStream pout = new PipedOutputStream();
        final PipedInputStream pin = new PipedInputStream();
        
        /*
        * Connects this piped output stream to a receiver (i.e pin).
        * If this piped output stream object is already connected to
        * some other piped input stream, an IOExceptin is thrown.
        */
        pout.connect(pin);
        
        /*
        * Creating one thread which writes the data (kp: uses a anonymous class
        *     (not a Lambda Expression??) ), 
        */
        Thread thread1 = new Thread() {
            public void run() {
                try {
                    for (int i = 50; i <= 60; i++) {
                        pout.write(i);
                        System.out.println("PipedOutputStream Writing i=" + i);
                        Thread.sleep(1000);
                    }
                    pout.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }               
            }
        };
        
        /*
        * Creating another thread2 which reads the data
        */
        Thread thread2 = new Thread() {
            public void run() {
                try {
                    int i;
                    while ((i = pin.read()) != -1) {
                        System.out.println("PipedInputStream Reading i = " + i);
                    }
                    pin.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        
        //Starting both threads
        thread1.start();
        thread2.start();        
    }
}
