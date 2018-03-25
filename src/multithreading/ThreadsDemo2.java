/*
 * https://www.youtube.com/watch?v=UXW5a-iHjso
 * Testing the second method of creating threads i.e.
 *    implementing the Runnable interface.
 */
package multithreading;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kpadhikari
 */
class MyThreadClass implements Runnable {

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet."); 
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getId() + " i=" + i);
            System.out.println(Thread.currentThread().getName() + " i=" + i);
        }

        try {
            //Without followign .sleep(t) line, the threads may run too fast
            //   and we may not feel like they are running in separate threads.
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            //Logger.getLogger(MyThreadSubclass.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}

public class ThreadsDemo2 {

    public static void main(String[] args) {
        //Using the external implementation of the Runnable interface (see above)
        Thread t1 = new Thread(new MyThreadClass());
        Thread t2 = new Thread(new MyThreadClass());
        t1.start();
        t2.start();

        //Without creating the external class implementing the Runnable interface
        //  rather by using an anonymous class i.e. anonymously implementing the 
        //   same Runnable interface: 
//        https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html
//        Anonymous classes enable you to make your code more concise. They enable 
//        you to declare and instantiate a class at the same time. They are 
//        like local classes except that they do not have a name. 
//        Use them if you need to use a local class only once.
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                //============ Simply copy/pasting the content from run() method above
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getId() + " i=" + i);
                }

                try {
                    //Without followign .sleep(t) line, the threads may run too fast
                    //   and we may not feel like they are running in separate threads.
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MyThreadSubclass.class.getName()).log(Level.SEVERE, null, ex);
                }
                //===============
            }
        });
        t3.start();
    }
}
