/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package coatjavatests;
package coatjava.groottests;

/**
 *
 * @author pubuduni
 */
import java.util.Random;
import javax.swing.JFrame;
import org.jlab.groot.data.H1F;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.groot.base.GStyle;

public class CoatjavaTests {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // First define the 1D histogram of H1F type
        H1F h1 = new H1F("histogram",100,-5,5); 

        //Next, fill the histogram with random Gaussian numbers
        Random randomGenerator = new Random();
        for(int i=0; i<5000; i++){
            h1.fill(randomGenerator.nextGaussian());
        }
        
        //Next draw and show the histogram in a canvas
        JFrame frame = new JFrame("Basic GROOT Demo 2");
        EmbeddedCanvas canvas = new EmbeddedCanvas();
        frame.setSize(1200, 750);
        canvas.getPad(0).setTitle("A Gaussian distribution");

        h1.setLineWidth(2);
        h1.setLineColor(2);

        canvas.draw(h1);

        //canvas.setFont("HanziPen TC");
        //canvas.setTitleSize(48);
        //canvas.setStatBoxFontSize(18);

        frame.add(canvas);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}
