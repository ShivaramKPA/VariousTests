/*
 * http://stackoverflow.com/questions/7313657/should-you-synchronize-the-run-method-why-or-why-not
Go through the code comments and uncomment and run the different blocks to clearly see the difference, 
note synchronization will have a difference only if the same runnable instance is used, if each thread 
started gets a new runnable it won't make any difference.
 */
package varioustests;

/**
 *
 * @author kpadhikari
 */
public class SynchronizedVoidRunTest {
//class Kat{

public static void main(String... args){
  Thread t1;
  // MyUsualRunnable is usual stuff, only this will allow concurrency
  MyUsualRunnable m0 = new MyUsualRunnable();
  for(int i = 0; i < 5; i++){
  t1 = new Thread(m0);//*imp*  here all threads created are passed the same runnable instance
  t1.start();
  }

  // run() method is synchronized , concurrency killed
  // uncomment below block and run to see the difference

  MySynchRunnable1 m1 = new MySynchRunnable1();
  for(int i = 0; i < 5; i++){
  t1 = new Thread(m1);//*imp*  here all threads created are passed the same runnable instance, m1
  // if new insances of runnable above were created for each loop then synchronizing will have no effect

  t1.start();
}

  // run() method has synchronized block which lock on runnable instance , concurrency killed
  // uncomment below block and run to see the difference
  /*
  MySynchRunnable2 m2 = new MySynchRunnable2();
  for(int i = 0; i < 5; i++){
  // if new insances of runnable above were created for each loop then synchronizing will have no effect
  t1 = new Thread(m2);//*imp*  here all threads created are passed the same runnable instance, m2
  t1.start();
}*/

}
}

class MyUsualRunnable implements Runnable{
  @Override
  public void  run(){
    try {Thread.sleep(1000);} catch (InterruptedException e) {}
}
}

class MySynchRunnable1 implements Runnable{
  // this is implicit synchronization
  //on the runnable instance as the run()
  // method is synchronized
  @Override
  public synchronized void  run(){
    try {Thread.sleep(1000);} catch (InterruptedException e) {}
}
}

class MySynchRunnable2 implements Runnable{
  // this is explicit synchronization
  //on the runnable instance
  //inside the synchronized block
  // MySynchRunnable2 is totally equivalent to MySynchRunnable1
  // usually we never synchronize on this or synchronize the run() method
  @Override
  public void  run(){
    synchronized(this){
    try {Thread.sleep(1000);} catch (InterruptedException e) {}
  }
}
}