/*
* https://www.youtube.com/watch?v=QLD2xp1ZYpk
 * Synchronization in Java using Synchronized Keyword
*
*  If this program is executed without 'synchronized' keyword in 
*      front of 'public void processData()', both threads can call that method
*      simultaneously, and so the output will look as follows:
*      
*      [
*      [
*      ]
*      ]
*
*  But, if the 'synchronized' key word is there, only one thread will call the
*      method at one time (that thread will have the lock) and so the output will
*      look as follows:
*      
*      [
*      ]
*      [
*      ]
*
 */
package varioustests.multithreadingtests;

/**
 *
 * @author https://www.youtube.com/watch?v=QLD2xp1ZYpk
 */
class one {
    public void processData() {
        System.out.println("[");
        try {
            Thread.sleep(5000);            
        } catch (Exception e) {
        }
        System.out.println("]");
    }
}

class myth implements Runnable {
    one ob;
    myth(one a) {
        ob = a;
    }
    public void run() {
        ob.processData();
    }
}

class three {
    public static void main(String[] args) {
        one x = new one();
        myth a = new myth(x);
        myth b = new myth(x);
        
        Thread th = new Thread(a);
        Thread th1 = new Thread(b);
        
        th.start(); //run method from th object of myth class will be executed in a new thread
        th1.start();//run method from th1 object of myth class will be executed in another thread
    }
}

public class synchronizedTest {
    //public static void main(String ... arg) {
//    public static void main(String[] args) {
//        
//    }
}
