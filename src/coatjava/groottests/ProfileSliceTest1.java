/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coatjava.groottests;

/**
 *
 * @author kpadhikari
 */
import java.util.Random;
import javax.swing.JFrame;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.groot.math.FunctionFactory;

public class ProfileSliceTest1 {
    private H1F hist1;
    
    public ProfileSliceTest1() {
        prepareAn1DHistogram();
    }

    public void prepareAn1DHistogram() {
        hist1 = new H1F("hist1", 100, -5, 5);
        Random randomGenerator = new Random();
        for (int i = 0; i < 50000; i++) {
            hist1.fill(randomGenerator.nextGaussian());
        }
        hist1.setTitleX("Randomly Generated Function");
        hist1.setTitleY("Counts");
        hist1.setLineWidth(2);
        hist1.setLineColor(21);
        hist1.setFillColor(34);
        hist1.setOptStat(1110);
        System.out.println("# of entries in hist1 = " + hist1.getEntries());
        System.out.println("# of entries in hist1 = " + hist1.integral());
        
    }

    public void make2DHistProfileAndSlices() {
        JFrame frame = new JFrame("Basic GROOT Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EmbeddedCanvas canvas = new EmbeddedCanvas();
        //canvas.setSize(3 * 400, 3 * 400);
        canvas.divide(2, 2);
        frame.setSize(2 * 600, 2 * 400);

        H2F histogram2d = FunctionFactory.randomGausian2D(200, 0.4, 7.6, 800000, 3.3, 0.8);        
        histogram2d.setTitleX("Randomly Generated Function");
        histogram2d.setTitleY("Randomly Generated Function");
        canvas.cd(0);
        canvas.getPad(0).setTitle("Histogram2D Demo");
        canvas.draw(histogram2d);
        canvas.setFont("HanziPen TC");
        canvas.setTitleSize(32);
        canvas.setAxisTitleSize(24);
        canvas.setAxisLabelSize(18);
        canvas.setStatBoxFontSize(18);

        canvas.cd(1);
        canvas.draw(histogram2d.getProfileX());

        canvas.cd(2);
        canvas.draw(histogram2d.sliceX(10));
        System.out.println("# of entries in sliceX(10): " + histogram2d.sliceX(10).getEntries());
        System.out.println("# of entries in sliceX(100): " + histogram2d.sliceX(100).getEntries());
        System.out.println("# of entries in sliceX(10): " + histogram2d.sliceX(10).integral());
        System.out.println("# of entries in sliceX(100): " + histogram2d.sliceX(100).integral());

        canvas.cd(3);
        canvas.draw(histogram2d.sliceX(100));

        frame.add(canvas);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        ProfileSliceTest1 myObj = new ProfileSliceTest1();
        myObj.make2DHistProfileAndSlices();
    }

    public void make2DRandomHist() {
        JFrame frame = new JFrame("Basic GROOT Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EmbeddedCanvas canvas = new EmbeddedCanvas();
        frame.setSize(800, 500);
        H2F histogram2d = FunctionFactory.randomGausian2D(200, 0.4, 7.6, 800000, 3.3, 0.8);
        //H2F histogram2d = FunctionFactory.
        histogram2d.setTitleX("Randomly Generated Function");
        histogram2d.setTitleY("Randomly Generated Function");
        canvas.getPad(0).setTitle("Histogram2D Demo");
        canvas.draw(histogram2d);
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
