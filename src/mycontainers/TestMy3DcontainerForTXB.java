/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycontainers;

import java.util.Random;
import javax.swing.JFrame;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.groot.math.FunctionFactory;
/**
 *
 * @author kpadhikari
 */
public class TestMy3DcontainerForTXB {
    
	public static void main(String[] args) {
                double xVal, yVal, zVal;
                int nGaus=50, nFlat=48, nHisto = 50;
		JFrame frame = new JFrame("Basic GROOT Demo");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		EmbeddedCanvas canvas = new EmbeddedCanvas();
		frame.setSize(4*380,3*350); 
                canvas.divide(4,3);
		H1F h1Gaus = new H1F("Gaussian",nGaus,-5,5); 
 		H1F h1Flat = new H1F("FlatRand",nFlat,-0.1,1.1); 
 		H1F histogram = new H1F("asinFlat",nHisto,-0.1,1.9);
                H2F h2GausVsFlat = new H2F("GausVsFlat", nGaus,-5,5,nFlat,-0.1,1.1);
                SimpleH3D myH3 = new SimpleH3D(nGaus,-5,5,nFlat,-0.1,1.1,nHisto,-0.1,1.9);
                SimpleH3D[] myH3n = new SimpleH3D[2]; //Also testing array of the objects of this class
                myH3n[0] = new SimpleH3D(nGaus,-5,5,nFlat,-0.1,1.1,nHisto,-0.1,1.9);
                myH3n[1] = new SimpleH3D(nGaus,-5,5,nFlat,-0.1,1.1,nHisto,-0.1,1.9);
                //Following four histos are to test the array
                H1F h1Gaus2 = new H1F("Gaussian2",nGaus,-5,5); 
                H2F h2GausVsFlat2 = new H2F("GausVsFlat2", nGaus,-5,5,nFlat,-0.1,1.1);
		H1F h1Gaus3 = new H1F("Gaussian3",nGaus,-5,5); 
                H2F h2GausVsFlat3 = new H2F("GausVsFlat3", nGaus,-5,5,nFlat,-0.1,1.1);
                
		Random randomGenerator = new Random();
		for(int i=0; i<50000; i++){
                    xVal = randomGenerator.nextGaussian();
                    yVal = randomGenerator.nextDouble();
                    zVal = //FunctionFactory.acos(randomGenerator.nextDouble()) + 
                    FunctionFactory.asin(randomGenerator.nextDouble()) ; 
                    //+ FunctionFactory.atan(randomGenerator.nextDouble());
                    h1Gaus.fill(xVal);
                    h1Flat.fill(yVal);
                    histogram.fill(zVal);
                    h2GausVsFlat.fill(xVal, yVal);
                    myH3.fill(xVal, yVal, zVal);
                    //For testing array of my container                    
                    myH3n[0].fill(xVal+zVal, yVal, zVal);
                    myH3n[1].fill(Math.sin(xVal), yVal, zVal);
                    h1Gaus2.fill(xVal+zVal);
                    h2GausVsFlat2.fill(Math.sin(xVal), yVal);
		}
                
                double binContent=0.0;
                //Now defining similar three histos and filling them with the contents of myH3
                H1F h1Gaus1 = new H1F("Gaussian1",nGaus,-5,5); 
 		H1F h1Flat1 = new H1F("FlatRand1",nFlat,-0.1,1.1); 
 		H1F histogram1 = new H1F("asinFlat1",nHisto,-0.1,1.9);
                H2F h2GausVsFlat1 = new H2F("GausVsFlat1", nGaus,-5,5,nFlat,-0.1,1.1);
                double iSum=0.0, jSum=0.0, kSum=0.0, ijSum=0.0;
                for(int i=0; i<nGaus; i++) {
                    h1Gaus1.setBinContent(i, myH3.getXBinProj(i));
                    h1Gaus3.setBinContent(i, myH3n[0].getXBinProj(i));
                    for(int j=0; j<nFlat; j++) {
                        h2GausVsFlat1.setBinContent(i,j, myH3.getXYBinProj(i,j));
                        h2GausVsFlat3.setBinContent(i,j, myH3n[1].getXYBinProj(i,j));
                    }                    
                }
                for(int j=0; j<nFlat; j++) {
                    h1Flat1.setBinContent(j, myH3.getYBinProj(j));
                }
                for(int k=0; k<nHisto; k++) {
                    histogram1.setBinContent(k, myH3.getZBinProj(k));
                }
                
                
                canvas.cd(0);
                canvas.draw(h1Gaus);
                canvas.cd(1);
                canvas.draw(h1Flat);
                canvas.cd(2);
		histogram.setTitleX("Randomly Generated Function");
		histogram.setTitleY("Counts");
		canvas.getPad(2).setTitle("BasicDemo Test");
		histogram.setLineWidth(2);
		histogram.setLineColor(21);
		histogram.setFillColor(34);
		histogram.setOptStat(1110);
		canvas.draw(histogram);
		canvas.setFont("HanziPen TC");  
		canvas.setTitleSize(32);
		canvas.setAxisTitleSize(24);
		canvas.setAxisLabelSize(18);
		canvas.setStatBoxFontSize(18);
                
                canvas.cd(3);
                canvas.draw(h2GausVsFlat);
                
                canvas.cd(4);
                canvas.draw(h1Gaus1);
                canvas.cd(5);
                canvas.draw(h1Flat1);
                canvas.cd(6); 
                canvas.draw(histogram1);                
                canvas.cd(7); 
                canvas.draw(h2GausVsFlat1);                
                canvas.cd(8);
                canvas.draw(h1Gaus2);
                canvas.cd(9);
                canvas.draw(h1Gaus3);
                canvas.cd(10); 
                canvas.draw(h2GausVsFlat2);                
                canvas.cd(11); 
                canvas.draw(h2GausVsFlat3);                
                
		frame.add(canvas);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);                
	}    
}
