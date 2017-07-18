//package org.jlab.groot.demo;
package coatjava.groottests;

import java.util.Random;

import javax.swing.JFrame;

import org.jlab.groot.math.Axis;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.groot.math.FunctionFactory;

public class BasicDemoSliceFitterTest {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Basic GROOT Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EmbeddedCanvas canvas = new EmbeddedCanvas();
        frame.setSize(800, 500);

        //H2F hist2d = FunctionFactory.randomGausian2D(200, 0.4, 7.6, 80000000, 3.3, 0.8);
        H1F histogram = new H1F("histogram", 100, -5, 5);        
        Random randomGenerator = new Random();
        for (int i = 0; i < 50000; i++) {
            histogram.fill(randomGenerator.nextGaussian());
            //histogram.fill(randomGenerator.);
        }
        //The nextGaussian() method returns random numbers with a mean of 0 and a standard deviation of 1. 
        //r.nextGaussian() * 15;   gives random numbers with a mean and sigma of 0 and 15 respectively.
        //http://www.javamex.com/tutorials/random_numbers/gaussian_distribution_2.shtml
        
        H2F hist2d = new H2F("hist2d", 100, -5, 5, 200, 0.4, 7.6);
        //Wanted to test if I could use lines as follows:
        //this.xAxis.getNBins(),this.xAxis.min(),this.xAxis.max(),
        //this.yAxis.getNBins(),this.yAxis.min(),this.yAxis.max()
        for (int i = 0; i < 500000; i++) {
            hist2d.fill(randomGenerator.nextGaussian(), 4.0 + randomGenerator.nextGaussian());
            //histogram.fill(randomGenerator.nextGaussian(), Math.sin(randomGenerator.nextGaussian()));
        }  
        
        System.out.println("Print the binning parameters of the 2D histogram 'histogram1d':");
        Axis xAxis = hist2d.getXAxis();
        Axis yAxis = hist2d.getYAxis();
        int nBinsX = hist2d.getXAxis().getNBins();
        int nBinsY = yAxis.getNBins();
        double xMin = xAxis.min(), xMax = yAxis.max();
        double yMin = xAxis.min(), yMax = yAxis.max();
        //double xWidth = xAxis.getBinWidth(bin);
        System.out.println("X(n,min,max) = (" + nBinsX + ", " + xMin + ", " + xMax + ")");
        System.out.println("Y(n,min,max) = (" + nBinsY + ", " + yMin + ", " + yMax + ")");
        System.out.println("Now checking the bin positions (coordinates of the bin centers) ");
        for (int i = 0; i < nBinsX; i++) {
            System.out.println("xBin " + i + ": " +  hist2d.getDataX(i));
        }
        for (int i = 0; i < nBinsY; i++) {
            System.out.println("yBin " + i + ": " +  hist2d.getDataY(i));
        }

        //H2F hist2d = FunctionFactory.
        hist2d.setTitleX("Randomly Generated Function");
        hist2d.setTitleY("Randomly Generated Function");        
        canvas.getPad(0).setTitle("Histogram2D Demo");
        canvas.draw(hist2d);
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
