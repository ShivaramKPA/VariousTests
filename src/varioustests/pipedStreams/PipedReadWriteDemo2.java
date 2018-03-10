/*
 * http://tutorials.jenkov.com/java-io/pipes.html Java IO: Pipes by Jakob Jenkov Last update: 2014-10-05
 */
package varioustests.pipedStreams;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedReadWriteDemo2 {

    public static void main(String[] args) throws IOException {

        //final PipedOutputStream output = new PipedOutputStream();
        //final PipedInputStream  input  = new PipedInputStream(output);
        
        //kp:   Alternatively, we can first declare input and pass that to output as follows
        final PipedInputStream input = new PipedInputStream();
        final PipedOutputStream output = new PipedOutputStream(input);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String str = "Hello world, pipe!";
                    str = "Hello world, I'm a pipe data which is first passed on to \n"
                            + " PipedOutputStream object (using write(..)), which is next received \n"
                            + " by PipedInputStream object (using read()), and finally is printed out to std output!\n";
                    // https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#getBytes()
                    //  public byte[] getBytes()
                    //       Encodes this String into a sequence of bytes using the platform's default charset, 
                    //       storing the result into a new byte array. 
                    System.out.println("str.getBytes = " + str.getBytes()); //kp: Returned: str.getBytes = [B@33ee7f84
                    byte[] bytes = str.getBytes(); //kp: Returned: [B@3ecc4da0
                    System.out.println("bytes: " + bytes);
                    output.write(str.getBytes());
                } catch (IOException e) {
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int data = input.read();//data will have a value of 72 which is dec. representation of 'H' in ASCII
                    System.out.println("data = " + data + "(char) data = " + (char) data);//kp: returned: data = 72(char) data = H
                    while (data != -1) {
                        //kp: following line will print each letter of above 'str' in a new line (first one being 'H')
                        //System.out.println("data = " + data + "(char) data = " + (char) data); //kp:
                        System.out.print((char) data); //This one prints each letter of 'str' in the same line.
                        data = input.read();
                    }
                } catch (IOException e) {
                }
            }
        });

        thread1.start();
        thread2.start();

    }
}
