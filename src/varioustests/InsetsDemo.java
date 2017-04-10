//http://java06.blogspot.com/2012/04/insets.html
// Apr 17, 2012
//Insets
//
//If you want to leave a small space between the container that holds your components and the window that 
//contains it. To do this, override the getInsets() method that is defined by the container.
//      
//          Insets(int top, int left, int bottom, int right)
//
//The values passed in top, left,  bottom and right specify the amount of space between the container and its enclosing window.
//
//Running this Example in Netbeans: Use "Shift+F6". 
//   Put the cursor in this file for "Shift+F6" to work.
//   A nice window will open up.

package varioustests; 

import java.awt.*;
import java.applet.*;


public class InsetsDemo extends Applet
{
 
        public void init()
        {
            setBackground(Color.cyan);
            setLayout(new BorderLayout());
            add(new Button("RMI"),BorderLayout.NORTH);
            add(new Button("SERVLET"),BorderLayout.EAST);
            add(new Button("JDBC"),BorderLayout.SOUTH);
            add(new Button("BEANS"),BorderLayout.WEST);
            add(new Button("JAVA"),BorderLayout.CENTER);
        }


        public Insets getInsets()
        {
            return new Insets(10,30,5,20);
        }
}
/*
<applet code=”InsetsDemo.class” width=300 height=200>
</applet>
*/


//http://stackoverflow.com/questions/27573220/debug-run-applet-in-netbeans
//Q:
//How can I debug or run a class that extends java.applet.Applet? I am able to run the applet 
//from the command line with appletviewer.
//
//appletviewer runapp.html
//
//A:
//As you don't have public static void main(String[] args) in your FancyApplet class, so the above error is produced.
//You can run your java applet in the NetBeans IDE by pressing Shift + F6 key from your main class(keeping cursor 
//inside FancyApplet class here). It'll run the applet program without searching for the main method.
//This is an alternate shortcut method of running individual classes in NetBeans IDE.
