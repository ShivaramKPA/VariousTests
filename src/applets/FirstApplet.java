/*
 *https://www.youtube.com/watch?v=aUlwgdakBug
 *https://www.youtube.com/watch?v=wjlRF6Mpyhg
 */
package applets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import javax.swing.JApplet;

/**
 *
 * @author kpadhikari
 */
public class FirstApplet extends JApplet {
    
    @Override //Not essential (but put it anyway just to remind myself that it's an override fn)
    public void start() {
        
    }
    
    //Acts like a constructor. Will always run at the beginning.
    @Override
    public void init() { 
        setBackground(Color.orange);
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawString("Hi Mom", 100, 50); //("Hi Mom", WIDTH, WIDTH);//(Str, int x, int y);
        g.drawRect(100, 70, 200, 100); //(WIDTH, WIDTH, WIDTH, HEIGHT); in pixels
        
        Graphics2D g2D = (Graphics2D) g;
        Point2D.Double topLeft = new Point2D.Double(0.0, 0.0);//At complete top-left of the screen
        Point2D.Double topRight = new Point2D.Double(100.0, 40.0);//At complete top-left of the screen
        
        Line2D.Double line = new Line2D.Double(topLeft, topRight);
        g2D.draw(line);
    }    
}
