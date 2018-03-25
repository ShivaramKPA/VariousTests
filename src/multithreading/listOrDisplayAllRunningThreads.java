/*
 * https://www.tutorialspoint.com/javaexamples/thread_showall.htm
* https://stackoverflow.com/questions/1323408/get-a-list-of-all-threads-currently-running-in-java
 */
package multithreading;

import java.util.Set;

/**
 *
 * @author kpadhikari
 */
public class listOrDisplayAllRunningThreads extends Thread {

    public static void main(String[] args) {
        //Main t1 = new Main();
        listOrDisplayAllRunningThreads t1 = new listOrDisplayAllRunningThreads();
        t1.setName("thread1");
        t1.start();

        //showAllThreads();
        //showAllThreads2();
        showAllThreads3();
    }

    public static void showAllThreads() {
        //https://www.tutorialspoint.com/javaexamples/thread_showall.htm
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        System.out.println("kp: # of threads = " + noThreads);
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);

        for (int i = 0; i < noThreads; i++) {
            System.out.println("Thread No:" + i + " = " + lstThreads[i].getName());
        }
    }

    public static void showAllThreads2() {
        //https://stackoverflow.com/questions/1323408/get-a-list-of-all-threads-currently-running-in-java
        Thread.getAllStackTraces().keySet().forEach((t) -> System.out.println(t.getName() + "\nIs Daemon " + t.isDaemon() + "\nIs Alive " + t.isAlive()));
    }

    public static void showAllThreads3() {
        System.out.println("Currently, there are following threads in this program or process:");        
        //http://www.codejava.net/java-core/concurrency/how-to-list-all-threads-currently-running-in-java
        //The following code snippet will list all threads that are currently running 
        // in the JVM along with their information like name, state, priority, and daemon status:
        Set<Thread> threads = Thread.getAllStackTraces().keySet();

        System.out.println("############################################################");
        System.out.println("thread-name    thread-state      Id     priority       type");
        System.out.println("############################################################");
        for (Thread t : threads) {
            String name = t.getName();
            Thread.State state = t.getState();
            int priority = t.getPriority();
            String type = t.isDaemon() ? "Daemon" : "Normal";
            System.out.printf("%-20s \t %s \t %d \t %d \t %s\n", name, state, 
                    //Thread.currentThread().getId(), priority, type); //This will give the same Id (of current thread) for all threads
                    t.getId(), priority, type);
        }
        System.out.println("####################################################");
    }
}
