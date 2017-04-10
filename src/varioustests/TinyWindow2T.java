// File   : gui-tutorial/tw2/TinyWindow2T.java
// Purpose: Based on TinyWindow2, this shows how to initialize
//          the construction of a GUI on the EDT thread.
//          This is the recommended way, but for simple programs
//          is typically omitted.
// Author : Fred Swartz, 2007-01-15, Placed in public domain.

package varioustests; //Copied from https://www.leepoint.net/JavaBasics/gui/gui-commentary/guicom-main-thread.html

import javax.swing.*;

////////////////////////////////////////////////////// class TinyWindow2
class TinyWindow2T extends JFrame {

    //====================================================== method main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {  //Note 1
            public void run() {
                TinyWindow2T window = new TinyWindow2T();
                window.setVisible(true);
            }
        });
    }
    
    //====================================================== constructor
    public TinyWindow2T() {   
        //... Set window characteristics
        setTitle("Tiny Window using JFrame Subclass");  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }
}