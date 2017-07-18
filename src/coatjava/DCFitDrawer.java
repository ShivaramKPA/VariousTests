/*  +__^_________,_________,_____,________^-.-------------------,
 *  | |||||||||   `--------'     |          |                   O
 *  `+-------------USMC----------^----------|___________________|
 *    `\_,---------,---------,--------------'
 *      / X MK X /'|       /'
 *     / X MK X /  `\    /'
 *    / X MK X /`-------'
 *   / X MK X /
 *  / X MK X /
 * (________(                @author m.c.kunkel
 *  `------'
*/
package coatjava;

import static coatjava.Constants.thEdgeVzH;
import static coatjava.Constants.thEdgeVzL;
import static coatjava.Constants.wpdist;

//import static coatjava.Constants.*;
import java.util.ArrayList;

import org.jlab.groot.math.Func1D;
import org.jlab.rec.dc.CalibrationConstantsLoader;
import org.jlab.rec.dc.timetodistance.TableLoader;

public class DCFitDrawer extends Func1D {
        private int sector = 2;
	private int superlayer;
	private int thetaBin;
        private double thetaInDeg = 0.0;
        private double bField = 0.0; //In Tesla
	private boolean isLinearFit;
	private double[] fPars;
        private int KPorVZ = 1; //1 for my own (KP), 2 for Veronique's (VZ) time func.
	private DCTimeFunction timeFunc;
        
        double dMax = 0.0;
        TableLoader tableLoader;

	public DCFitDrawer() {
		super("calibFnToDraw", 0.0, 1.0);
		this.initParameters();
	}

	public DCFitDrawer(String name, double xmin, double xmax, int superlayer, 
                int thetaBin, boolean isLinearFit) {
            
		super(name, xmin, xmax);
		this.initParameters();
		this.superlayer = superlayer;
		this.thetaBin = thetaBin;
                this.thetaInDeg = 0.5 * (thEdgeVzL[thetaBin] + thEdgeVzH[thetaBin]); // Center of theta bin
		this.isLinearFit = isLinearFit;
	}
        
    public DCFitDrawer(String name, double xmin, double xmax, int sector, int superlayer,
            double thetaInDeg, double bField, int KPorVZ, boolean isLinearFit) {

        super(name, xmin, xmax);
        this.initParameters();
        this.sector = sector;
        this.superlayer = superlayer;
        //this.thetaBin = thetaBin;
        this.thetaInDeg = thetaInDeg;
        this.bField = bField;
        this.isLinearFit = isLinearFit;
        this.KPorVZ = KPorVZ;
        
        double dMax = 2*wpdist[superlayer];
        int newRun = 810;
        CalibrationConstantsLoader.LoadDevel(newRun, "default", "dc_test1");
        //CalibrationConstantsLoader.LoadDevel(newRun, "default","default");
//        TableLoader.Fill(); //Not needed
        this.tableLoader = new TableLoader();
        //public static synchronized double calc_Time(double x, double dmax, double tmax, double alphaDeg, double bfield, int s, int r)
//        double calcTime = this.tableLoader.calc_Time(0.1,2.0,300,20,1.0,2,1);
//        System.out.println("calcTime=" + calcTime);                      
    }       

	private void initParameters() {
		ArrayList<String> pars = new ArrayList<String>();
		pars.add("v0");
		pars.add("deltamn");
		pars.add("tmax");
		pars.add("distbeta");
		for (int loop = 0; loop < pars.size(); loop++) {
			this.addParameter(pars.get(loop));
		}
		double prevFitPars[] = { 62.92e-04, 1.35, 148.02, 0.055 };

		this.setParameters(prevFitPars);
	}

	private void setParmLength(int i) {
		this.fPars = new double[i + 1];
	}

	@Override
	public void setParameters(double[] params) {
		setParmLength(params.length);
		for (int i = 0; i < params.length; i++) {
			this.setParameter(i, params[i]);
			fPars[i] = params[i];
		}
	}

	@Override
	public double evaluate(double xNorm) {
               double calcTime = 0.0, x = 0.0, tMax = fPars[2];
		//double thetaDeg = 0.5 * (thEdgeVzL[thetaBin] + thEdgeVzH[thetaBin]); // Center of theta bin
                if(KPorVZ==1) {
                    timeFunc = new DCTimeFunction(superlayer, thetaInDeg, xNorm, fPars);
                    //timeFunc = new DCTimeFunction(superlayer, thetaInDeg, bField, xNorm, KPorVZ, fPars);
                    calcTime = isLinearFit ? timeFunc.linearFit() : timeFunc.nonLinearFit();
                } else if(KPorVZ == 2) {
                    x = xNorm * dMax;
                    ////public static synchronized double calc_Time(double x, double dmax, double tmax, double alphaDeg, double bfield, int s, int r)
                    calcTime = this.tableLoader.calc_Time(x,dMax,tMax,thetaInDeg,bField,sector,superlayer);
                }
                
		return calcTime;
	}

}