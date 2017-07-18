//package org.jlab.groot.demo;
package coatjava.groottests;

import java.util.Random;
import javax.swing.JFrame;

import org.jlab.groot.data.H1F;
import org.jlab.groot.math.F1D;
import org.jlab.groot.fitter.DataFitter;
import org.jlab.groot.graphics.EmbeddedCanvas;

public class BasicDemo {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Basic GROOT Demo");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		EmbeddedCanvas canvas = new EmbeddedCanvas();
		frame.setSize(800,500);
		H1F histogram = new H1F("histogram",100,-5,5); 
		Random randomGenerator = new Random();
		for(int i=0; i<50000; i++){
			histogram.fill(randomGenerator.nextGaussian());
		}
		histogram.setTitleX("Randomly Generated Function");
		histogram.setTitleY("Counts");
		canvas.getPad(0).setTitle("BasicDemo Test");
		histogram.setLineWidth(2);
		histogram.setLineColor(21);
		histogram.setFillColor(34);
                //WPhelps: The least significant bit is on the right, basically the 0 
                //is to not show the name, the 1 next to that is to show entries, the 
                //one to the left of that is the mean, etc.
		histogram.setOptStat(1110);
 		//histogram.setOptStat(11111111);
 		//histogram.setOptStat(110);
                
                
        // =======kp: Part added to test DataFitter & showing the parameters in the statbox.    
        F1D func = new F1D("func","[amp]*gaus(x,[mean],[sigma])",-3.0,3.0);
        func.setLineColor(2);
        func.setLineStyle(2);
        func.setLineWidth(2);
        func.setParameter(0, 1000);
        func.setParameter(1, -0.4);
        func.setParameter(2, 0.7);
        DataFitter.fit(func, histogram, "E");  
        func.setOptStat(1110);
        func.show(); //Prints fit parameters
        // =======kp: Part added to test DataFitter & showing the parameters in the statbox.    
                
                
                
                //histogram.
		canvas.draw(histogram);
                //canvas.draw(func,"same");
		canvas.setFont("HanziPen TC");  
		canvas.setTitleSize(32);
		canvas.setAxisTitleSize(24);
		canvas.setAxisLabelSize(18);
		canvas.setStatBoxFontSize(18);
		frame.add(canvas);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
