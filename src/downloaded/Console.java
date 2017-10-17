//
// A simple Java Console for your application (Swing version)
// Requires Java 1.1.5 or higher
//
// Disclaimer the use of this source is at your own risk. 
//
// Permision to use and distribute into your own applications
//
// RJHM van den Bergh , rvdb@comweb.nl
package downloaded;   //kp: http://www.comweb.nl/java/Console/Console.html

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Console extends WindowAdapter implements WindowListener, ActionListener, Runnable {

    private JFrame frame;
    private JTextArea textArea;
    private Thread reader;
    private Thread reader2;
    private boolean quit;

    private final PipedInputStream pin = new PipedInputStream();
    private final PipedInputStream pin2 = new PipedInputStream();

    Thread errorThrower; // just for testing (Throws an Exception at this Console

    //kp: Job of createAndShowGUI() is done by the constructor itself
    public Console() {
        // create all components and add them
        //=====kp: First of all define the size of the main GUI window
        frame = new JFrame("Java Console");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension((int) (screenSize.width / 2), (int) (screenSize.height / 2));
        int x = (int) (frameSize.width / 2);
        int y = (int) (frameSize.height / 2);
        frame.setBounds(x, y, frameSize.width, frameSize.height);

        //=======kp: Define textArea for the console
        textArea = new JTextArea();
        textArea.setEditable(false); //kp: we don't want to make the console editable, rather only show messages/errors.
        
        //======kp: Define the clear button
        JButton button = new JButton("clear");

        //=====kp: Define content pane, add to the frame, and make the frame visiable
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.getContentPane().add(button, BorderLayout.SOUTH);
        frame.setVisible(true);

        //=====kp: Add listeners to the Window and the Button
        frame.addWindowListener(this);
        button.addActionListener(this);

        //redirection of System.out.
        try {
            PipedOutputStream pout = new PipedOutputStream(this.pin);
            System.setOut(new PrintStream(pout, true));
            //System.out.println(""); //kp:
        } catch (java.io.IOException io) {
            textArea.append("Couldn't redirect STDOUT to this console\n" + io.getMessage());
        } catch (SecurityException se) {
            textArea.append("Couldn't redirect STDOUT to this console\n" + se.getMessage());
        }

        //redirection of System.err.
        try {
            PipedOutputStream pout2 = new PipedOutputStream(this.pin2);
            System.setErr(new PrintStream(pout2, true));
        } catch (java.io.IOException io) {
            textArea.append("Couldn't redirect STDERR to this console\n" + io.getMessage());
        } catch (SecurityException se) {
            textArea.append("Couldn't redirect STDERR to this console\n" + se.getMessage());
        }

        //==== kp:  if true (see windowClosed(*) below), it signals the Threads that they should exit
        quit = false; // signals the Threads that they should exit

        // Starting two seperate threads to read from the PipedInputStreams				
        //
        //kp: see comments before 'run()' method below that says object of a Runnable-implemented class
        //    is passed to a Thread object, and when start() is called, the run() method is invoked.
        //    Which means, the code in run() method is executed three times because three objects -
        //    reader, reader2 and errorThrower call start() method. But, as we see inside the run()
        //    code, we have lines such as "while (Thread.currentThread() == reader)", which means different
        //    parts/blocks of code in the run() method will be executed in the three different threads.
        reader = new Thread(this);
        reader.setDaemon(true);
        reader.start();
        //
        reader2 = new Thread(this);
        reader2.setDaemon(true);
        reader2.start();

        // testing part
        // you may omit this part for your application
        // 
        System.out.println("Hello World 2");
        System.out.println("All fonts available to Graphic2D:\n");
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        for (int n = 0; n < fontNames.length; n++) {
            System.out.println(fontNames[n]);
        }
        // Testing part: simple an error thrown anywhere in this JVM will be printed on the Console
        // We do it with a seperate Thread becasue we don't wan't to break a Thread used by the Console.
        System.out.println("\nLets throw an error on this console");
        errorThrower = new Thread(this);
        errorThrower.setDaemon(true);
        errorThrower.start();
    }

    
    /*
    https://www.tutorialspoint.com/java/lang/object_notifyall.htm
    The java.lang.Object.notifyAll() wakes up all threads that are waiting on this object's monitor. 
    A thread waits on an object's monitor by calling one of the wait methods.

The awakened threads will not be able to proceed until the current thread relinquishes the lock on 
    this object. The awakened threads will compete in the usual manner with any other threads that might 
    be actively competing to synchronize on this object; for example, the awakened threads enjoy no 
    reliable privilege or disadvantage in being the next thread to lock this object.

This method should only be called by a thread that is the owner of this object's monitor.
    */
    
    
    //kp: ==== https://www.tutorialspoint.com/awt/awt_windowadapter.htm
    //   Invoked when a window has been closed.
    public synchronized void windowClosed(WindowEvent evt) {
        quit = true;
        this.notifyAll(); // stop all threads
        try {
            reader.join(1000);
            pin.close();
        } catch (Exception e) {
        }
        try {
            reader2.join(1000);
            pin2.close();
        } catch (Exception e) {
        }
        System.exit(0);
    }

    //kp: Invoked when a window is in the process of being closed.
    public synchronized void windowClosing(WindowEvent evt) {
        frame.setVisible(false); // default behaviour of JFrame	
        frame.dispose();
    }

    //kp: This is the action performed when the "clear" button is clicked (see actionListener added above) 
    public synchronized void actionPerformed(ActionEvent evt) {
        textArea.setText("");
    }

    //kp: About void run()
    //kp: ====== https://docs.oracle.com/javase/7/docs/api/java/lang/Runnable.html
    //void run(): When an object implementing interface Runnable is used to create a thread, starting the thread 
    //causes the object's run method to be called in that separately executing thread.
    // The general contract of the method run is that it may take any action whatsoever.
    
    /*
    // http://www.dummies.com/programming/java/how-to-use-the-runnable-interface-in-java-to-create-and-start-a-thread/
    //
    The Runnable interface marks an object that can be run as a thread. It has only one method, run, that 
    contains the code that’s executed in the thread. (The Thread class itself implements Runnable, which 
    is why the Thread class has a run method.)

    To use the Runnable interface to create and start a thread, you have to do the following:

    1) Create a class that implements Runnable.
    2) Provide a run method in the Runnable class.
    3) Create an instance of the Thread class and pass your Runnable object to its constructor as a parameter.
        A Thread object is created that can run your Runnable class.
    4) Call the Thread object’s start method.
        The run method of your Runnable object is called and executed in a separate thread.
    
    The first two of these steps are easy. The trick is in the third and fourth steps, because you can 
    complete them in several ways. Here’s one way, assuming that your Runnable class is named RunnableClass:

        RunnableClass rc = new RunnableClass();
        Thread t = new Thread(rc);
        t.start();

    Java programmers like to be as concise as possible, so you often see this code compressed to 
    something more like

        Thread t = new Thread(new RunnableClass());
        t.start();

    or even just this:
    
        new Thread(new RunnableClass()).start();
    
    This single-line version works — provided that you don’t need to access the thread object later in the program.
    */
    public synchronized void run() {
        try {
            while (Thread.currentThread() == reader) {
                try {
                    this.wait(100);
                } catch (InterruptedException ie) {
                }
                if (pin.available() != 0) {
                    String input = this.readLine(pin);
                    textArea.append(input);
                }
                if (quit) {
                    return;
                }
            }

            while (Thread.currentThread() == reader2) {
                try {
                    this.wait(100);
                } catch (InterruptedException ie) {
                }
                if (pin2.available() != 0) {
                    String input = this.readLine(pin2);
                    textArea.append(input);
                }
                if (quit) {
                    return;
                }
            }
        } catch (Exception e) {
            textArea.append("\nConsole reports an Internal error.");
            textArea.append("The error is: " + e);
        }

        // just for testing (Throw a Nullpointer after 1 second)
        if (Thread.currentThread() == errorThrower) {
            try {
                this.wait(1000);
            } catch (InterruptedException ie) {
            }
            throw new NullPointerException("Application test: throwing an NullPointerException It should arrive at the console");
        }

    }

    public synchronized String readLine(PipedInputStream in) throws IOException {
        String input = "";
        do {
            int available = in.available();
            if (available == 0) {
                break;
            }
            byte b[] = new byte[available];
            in.read(b);
            input = input + new String(b, 0, b.length);
        } while (!input.endsWith("\n") && !input.endsWith("\r\n") && !quit);
        return input;
    }

    public static void main(String[] arg) {
        new Console(); // create console with not reference	
    }
}
