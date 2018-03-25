/*  +__^_________,_________,_____,________^-.-------------------,
 *  | |||||||||   `--------'     |          |                   O
 *  `+-------------USMC----------^----------|___________________|
 *    `\_,---------,---------,--------------'
 *      / X MK X /'|       /'
 *     / X MK X /  `\    /'
 *    / X MK X /`-------'
 *   / X MK X /
 *  / X MK X /
 * (________(                @author m.c.kunkel, kpdhikari
 *  `------'
 */
//package org.jlab.dc_calibration.domain;
//package swing.consoles;
package multithreading;

import java.util.Set;

public final class Constants {

    protected static final double rad2deg = 180.0 / Math.PI;
    protected static final double deg2rad = Math.PI / 180.0;

    protected static final double cos30 = Math.cos(30.0 / rad2deg);
    protected static final double beta = 1.0;

    protected static final boolean debug = true;
    protected static final boolean[] angularBinsSelectedByDefault = { false, false, false, false };

    private Constants() {
    }

    public static void showAllThreads() {
        System.out.println("Currently, there are following threads in this program or process:");
        //http://www.codejava.net/java-core/concurrency/how-to-list-all-threads-currently-running-in-java
        //The following code snippet will list all threads that are currently running 
        // in the JVM along with their information like name, state, priority, and daemon status:
        Set<Thread> threads = Thread.getAllStackTraces().keySet();

        System.out.println("############################################################");
        System.out.println("############## List of Current threads #####################");
        System.out.println("thread-name    thread-state      Id     priority       type");
        System.out.println("############################################################");
        for (Thread t : threads) {
            String name = t.getName();
            Thread.State state = t.getState();
            int priority = t.getPriority();
            String type = t.isDaemon() ? "Daemon" : "Normal";
            System.out.printf("%-20s \t %s \t %d \t %d \t %s\n", name, state,
                    //Thread.currentThread().getId(), priority, type);
                    t.getId(), priority, type);
        }
        System.out.println("############################################################");
    }
}
