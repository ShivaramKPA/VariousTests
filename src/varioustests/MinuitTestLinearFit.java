//
//8/1/16:
//
//Finally, I successfully minimized a set of data that I generated myself using m = 1.2 and c = 10.0 and giving
//some small errors on the generated values of y (using y = m*x + c).
//
//I was also able to read off the fit parameters and their errors.
package varioustests;

// kp: 8/1/16
//Source: http://www.programcreek.com/java-api-examples/index.php?source_dir=freehep-ncolor-pdf-master/math/freehep-jminuit/src/test/java/org/freehep/math/minuit/example/sim/ReneTest.java  
//package org.freehep.math.minuit.example.sim;
import java.util.List;
import org.freehep.math.minuit.FCNBase;
import org.freehep.math.minuit.FunctionMinimum;
import org.freehep.math.minuit.MnMigrad;
import org.freehep.math.minuit.MnPlot;
import org.freehep.math.minuit.MnScan;
import org.freehep.math.minuit.MnStrategy;
import org.freehep.math.minuit.MnUserParameters;
import org.freehep.math.minuit.Point;

/**
 *
 * @version $Id: ReneTest.java 8584 2006-08-10 23:06:37Z duns $
 */
public class MinuitTestLinearFit {

    public static void main(String[] args) {
        //double[] x = {1.5751,1.5825,1.6069,1.6339,1.6706};
        //double[] y = {1.0642,0.97685,1.13168,1.128654,1.44016};
        double[] x = {-1.0, -0.5, 0.0, 0.5, 1.0, 1.5};
        //double[] y = {1.8,2.4,3,3.6,4.2,4.8};//Generated using y = m*x + c with m = 1.2 and c = 3.0
        double[] y
                = //{1.7,2.5,3.05,3.65,4.15,4.7}; //Giving some arbitrary errors on above 6 values of y
                {8.7, 9.5, 10.05, 10.5, 11.3, 11.85};
        double[] theXvalues = x.clone();
        double[] measurements = y.clone();

        //FitterFunction funcFitter = new FitterFunction(ds,func,options);
        //int npars = funcFitter.getFunction().getNParams();
        int npars = 2;

        double aLen = measurements.length;
        System.out.println("Size or length of array 'measurements' is " + aLen);
        System.out.print("xArray[] = "); printArray(theXvalues);
        //System.out.println("exit...");   System.exit(0); //kp


        //ReneFcn theFCN = new ReneFcn(measurements); 
        KrishnaFcn theFCN = new KrishnaFcn(theXvalues, measurements);

        MnUserParameters upar = new MnUserParameters();
        upar.add("p0", -1.0, 0.2);
        upar.add("p1", -1.5, 0.2);

        System.out.println("Initial parameters: " + upar);
        //System.out.println("exit...");   System.exit(0);   //kp

        System.out.println("start migrad");
        MnMigrad migrad = new MnMigrad(theFCN, upar);
        FunctionMinimum min = migrad.minimize();

        //System.out.println("exit...");   System.exit(0);   //kp
        if (!min.isValid()) {
            //try with higher strategy 
            System.out.println("FM is invalid, try with strategy = 2.");
            MnMigrad migrad2 = new MnMigrad(theFCN, min.userState(), new MnStrategy(2));
            min = migrad2.minimize();
        }

        //System.out.println("exit...");   System.exit(0);   //kp
        System.out.println("minimum: " + min);

        System.out.println("kp: ===================================== ");

        MnUserParameters userpar = min.userParameters();
        /*    
       for(int loop = 0; loop < npars; loop++)
	   {
	       //RealParameter par = funcFitter.getFunction().parameter(loop);
	       //par.setValue(userpar.value(par.name()));
	       //par.setError(userpar.error(par.name()));
	       System.out.println("par" + loop + " = " + userpar.value(upar.name(loop)));
	   }
         */
        System.out.println("par0 = " + userpar.value("p0") + " +/- " + userpar.error("p0"));
        System.out.println("par1 = " + userpar.value("p1") + " +/- " + userpar.error("p1"));
        System.out.println("kp: ===================================== ");

        /*
       { 
	   double[] params = {1.0,1.5}; //{-1.0,1.0}; 
	   double[] error = {0.1,0.1}; //{0.5,0.5}; 
	   MnScan scan = new MnScan(theFCN, params, error); 
	   System.out.println("scan parameters: "+scan.parameters()); 
	   MnPlot plot = new MnPlot(); 
	   for(int i = 0; i < upar.variableParameters(); i++) 
	       { 
		   List<Point> xy = scan.scan(i); 
		   plot.plot(xy); 
	       } 
	   System.out.println("scan parameters: "+scan.parameters()); 
       } 
        
       //System.out.println("exit...");   System.exit(0);   //kp

       { 
	   double[] params = {1.0,1.5}; //{-1.0,1.0}; //{1,1}; 
	   double[] error = {0.1,0.1}; //{0.5,0.5}; //{1,1}; 
	   MnScan scan = new MnScan(theFCN, params, error); 
	   System.out.println("scan parameters: "+scan.parameters()); 
	   FunctionMinimum min2 = scan.minimize(); 
	   //     std::cout<<min<<std::endl; 
	   System.out.println("scan parameters: "+scan.parameters()); 
       } 
         */
    }
    
    
    static void printArray(double[] array) {
        double aLen = array.length;
        for (int i = 0; i < aLen; i++) {
            if (i > 0 && i % 10 == 0) {
                System.out.println("");
            }
            System.out.print(array[i] + " ");
        }
        System.out.println("");        
    }
    
    

    //static class ReneFcn implements FCNBase 
    static class KrishnaFcn implements FCNBase {

        /*
       ReneFcn(double[] meas) 
       { 
	   theMeasurements = meas; 
       } 
         */
        KrishnaFcn(double[] xVals, double[] meas) {
            theXvalues = xVals;
            theMeasurements = meas;
        }

        public double errorDef() {
            return 1;
        }

        public double valueOf(double[] par) {
            double m = par[1]; //straight line equation:   y = m*x + c
            double c = par[0];
            double chisq = 0., fval = 0.;
            for (int i = 0; i < theMeasurements.length; i++) {
                double yi = theMeasurements[i]; // if(ni < 1.e-10) continue; 

                double xi = theXvalues[i]; //(i+1.)/40. - 1./80.; //xi=0-3 
                double ei = yi;
                // double nexp = a*xi*xi + b*xi + c + (0.5*p0*p1/Math.PI)/Math.max(1.e-10, (xi-p2)*(xi-p2) + 0.25*p1*p1); 
                double nexp = m * xi + c;
                //fval += (ni-nexp)*(ni-nexp)/ei; 
                chisq += (yi - nexp) * (yi - nexp);///ei; 
            }
            return chisq;//fval; 
        }
        private double[] theXvalues;// =  {-1.0, -0.5, 0.0, 0.5, 1.0, 1.5}; //{1.5751,1.5825,1.6069,1.6339,1.6706};
        //{8.7,9.5,10.05,10.5,11.3,11.85};
        private double[] theMeasurements;
    }
}
