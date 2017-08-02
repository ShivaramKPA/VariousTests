/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.groot.math.FunctionFactory;

/**
 *
 * @author kpadhikari
 */
/**
 * @see http://stackoverflow.com/questions/5654926
 */
public class PrevNextButtonsDemo extends JPanel {

    private static int kpCounter = 0;// 
    private static final Random random = new Random();
    private final String name;
    private static H2F hist2d;
    private static final int nBins = 200;
    private static final double xLow = 0.4, xHigh = 7.6, binW = (xHigh - xLow)/nBins;

    public PrevNextButtonsDemo(String name) {
        this.name = name;
        this.setPreferredSize(new Dimension(1000, 500));
        //this.setBackground(new Color(random.nextInt()));//kp: Background gets a random color
        this.setBackground(Color.lightGray);//kp: Background gets a light gray color
        this.add(new JLabel(name));

        //Preparing the 2D histo
        hist2d = FunctionFactory.randomGausian2D(nBins, xLow, xHigh, 800000, 3.3, 0.8);
        hist2d.setTitleX("Randomly Generated Function");
        hist2d.setTitleY("Randomly Generated Function");
        
        //Following lines 
        System.out.println("nBinsX = " + hist2d.getDataSize(0));//returned nBins
        System.out.println("nBinsY = " + hist2d.getDataSize(1));//returned nBins
        System.out.println("nBinsZ = " + hist2d.getDataSize(4));//whatever value (3,4, ..) gives the same #
        H2F h2 = new H2F("h2",100,0.3,4.0,150,0.2,3.0);
        System.out.println("h2 nBinsX = " + h2.getDataSize(0));//Returned 100
        System.out.println("h2 nBinsY = " + h2.getDataSize(1));//returned 150
        System.out.println("h2 nBinsZ = " + h2.getDataSize(5));//returned 150
        
    }

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                create();
            }
        });
    }

    private static void updateSlicePad(EmbeddedCanvas c1, int padID, JLabel label, int xBin4Slice) {
        double xBinPos = xLow + binW*xBin4Slice - binW/2; //x-position of current bin-center.
        c1.cd(padID);
        c1.getPad(padID).setTitle("x-Slice " + String.valueOf(xBin4Slice));        
        if (xBin4Slice > 0) {
            label.setText("x-Bin # = " + String.valueOf(xBin4Slice));
            H1F xSlice = hist2d.sliceX(xBin4Slice);
            xSlice.setTitleX(xBin4Slice + String.format("th x-slice at x = %3.2f", xBinPos));
            c1.draw(xSlice);
        }
        c1.update();
    }

    private static void create() {
        PrevNextButtonsDemo thisPanel = new PrevNextButtonsDemo("");
        //I can replace above line by simply having another function for all the initializations
        //   done in the constructor above. I am not really using the object created here.

        JPanel upPanel = new JPanel(new GridLayout(0,1));
        upPanel.setPreferredSize(new Dimension(1200, 400));//this one only worked to control the size 

        JPanel control = new JPanel();
        JLabel jLabel = new JLabel("x-Bin # = " + String.valueOf(kpCounter));

        EmbeddedCanvas c1 = new EmbeddedCanvas();
        //c1.setSize(1200, 400); //Didn't have any effect
        c1.divide(3, 1);
        //f.setSize(1000, 400);//Didn't have any effect
        c1.cd(0);
        c1.getPad(0).setTitle("Histogram2D Demo");
        c1.draw(hist2d);
        c1.cd(1);
        c1.getPad(1).setTitle("X-profile of hist2D");
        GraphErrors profileX = hist2d.getProfileX();
        profileX.setTitleX("X-Profile");
        c1.draw(profileX);
        //kp: defining a button, implementing the actionListener and adding to the panel all at the same time.
        control.add(new JButton(new AbstractAction("\u22b2Prev") {

            @Override
            public void actionPerformed(ActionEvent e) {
                kpCounter--;
                updateSlicePad(c1, 2, jLabel, kpCounter);
            }
        }));
        control.add(new JButton(new AbstractAction("Next\u22b3") {

            @Override
            public void actionPerformed(ActionEvent e) {
                kpCounter++;
                updateSlicePad(c1, 2, jLabel, kpCounter);
            }
        }));

        control.add(jLabel);
        //upPanel.add(jLabel);
        upPanel.add(c1);

        JFrame f = new JFrame("Slice Selector");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println("(int)(screensize.getWidth()*.9 = " + (int)(screensize.getWidth()*.9));
        System.out.println("(int)(screensize.getHeight()*.9 = " + (int)(screensize.getHeight()*.9));
       // f.setSize((int)(screensize.getWidth()*.9),(int)(screensize.getHeight()*.9)); //Didn't have any effect
        
        f.add(upPanel, BorderLayout.CENTER);//This is like an overayed tabbed panes (like a stack of cards)
        f.add(control, BorderLayout.SOUTH);
        
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
