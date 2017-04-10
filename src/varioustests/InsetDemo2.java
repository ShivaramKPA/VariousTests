//http://docstore.mik.ua/orelly/java/awt/ch06_03.htm
package varioustests;

/**
 *
 * @author kpadhikari
 */


import java.awt.*;
import java.applet.*;
public class InsetDemo2 extends Applet {
    public Insets insets () {
        return new Insets (50, 50, 50, 50);
    }
    public void init () {
        setLayout (new BorderLayout ());
        add ("Center", new Button ("Insets"));
    }
    public void paint (Graphics g) {
        Insets i = insets();
        int width  = size().width - i.left - i.right;
        int height = size().height - i.top - i.bottom;
        g.drawRect (i.left-2, i.top-2, width+4, height+4);
        g.drawString ("Insets Example", 25, size().height - 25);
    }
}

//http://stackoverflow.com/questions/7486092/what-does-strikethrough-mean-in-netbeans
//Q:
//What does it mean when things are written in strikethrough in Netbeans?
//
//A:
//The method has been deprecated (i.e. replaced by a newer method, which you should probably use instead).
//
//In other words, for some backward compatibility, the method you're calling is still in the API, but has 
//been replaced by newer code/methods. Often this happens when an API is redesigned or updated, especially 
//when a given API update changes its fundamental approach to a problem. When that happens, the old way of 
//doing something will be deprecated, and you are therefore encouraged (though not required, so long as your 
//code compiles and runs) to use the new stuff.
