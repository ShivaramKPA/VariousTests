/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.tutorialtests;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author kpadhikari
 */
public class HelloWorld {
    
    public static void main(String[] args) {
        //Checking which thread it is running on.
        System.out.println("Main thread name: " + Thread.currentThread().getName());
        
        //creating the GUI from the same i.e. the initial thread
        //createGUI(); //If enabled, it prints "GUI creating thread name: main" & "It is EDT: false"
        
        //creating the GUI from a different thread i.e. from the EDT
        createGUIfromEDT(); //If enabled, it prints "GUI creating thread name: AWT-EventQueue-0" &  & "It is EDT: true"
    }
    
    //If we want to call this method without intantiating HelloWorld class, then
    //    we must have it as a 'public static' as well.
    public static void createGUI() {
        System.out.println("GUI creating thread name: " + Thread.currentThread().getName());
        System.out.println("It is EDT: " + SwingUtilities.isEventDispatchThread());
        JFrame frame = new JFrame("Hello World Swing!");
        frame.setSize(500,400);
        //EXIT_ON_CLOSE below is a static constant from the JFrame class.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);        
    }
    
    public static void createGUIfromEDT() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI(); //I think in this case, it doesn't have to be static
            }
        });        
    }
}
