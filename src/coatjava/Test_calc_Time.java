/*
 * Wanted to compare my and Veronique's calc times but didn't complete the task.
 */
package coatjava;

import org.jlab.rec.dc.CalibrationConstantsLoader;
import org.jlab.rec.dc.timetodistance.TableLoader;

/**
 *
 * @author kpadhikari
 */
public class Test_calc_Time {
    
    public void calculateTime() {
        int newRun = 810;
        //CalibrationConstantsLoader.LoadDevel(newRun, "default","dc_test1");
        CalibrationConstantsLoader.LoadDevel(newRun, "default","default");
//        TableLoader.Fill(); //Not needed
        TableLoader tableLoader = new TableLoader();
        //public static synchronized double calc_Time(double x, double dmax, double tmax, double alphaDeg, double bfield, int s, int r)
        double calcTime = tableLoader.calc_Time(0.1,2.0,300,20,1.0,2,1);
        System.out.println("calcTime=" + calcTime);
        
        //Call my dist-to-time function and call Veronique's and
        // Make three graphs: one for each and one for the difference
        
        
//    private Map<Coordinate, DCFitDrawerForXDoca> mapOfFitLinesX = new HashMap<Coordinate, DCFitDrawerForXDoca>();
        
//        
//        for (int i = iSecMin; i < iSecMax; i++) { //2/15/17: Looking only at the Sector2 (KPP) data (not to waste time in empty hists)
//            for (int j = 0; j < nSL; j++) {
//                dMax = 2 * wpdist[j];
//                for (int k = 0; k < nThBinsVz; k++) {
//                    String title = "timeVsNormDoca Sec=" + (i + 1) + " SL=" + (j + 1) + " Th=" + k;
//                    double maxFitValue = h2timeVtrkDocaVZ.get(new Coordinate(i, j, k)).getDataX(getMaximumFitValue(i, j, k));
//                    mapOfFitLines.put(new Coordinate(i, j, k), new DCFitDrawer(title, 0.0, 1.0, j, k, isLinearFit));
//                    mapOfFitLines.get(new Coordinate(i, j, k)).setLineColor(4);//(2);
//                    mapOfFitLines.get(new Coordinate(i, j, k)).setLineWidth(3);
//                    mapOfFitLines.get(new Coordinate(i, j, k)).setLineStyle(4);
//                    //mapOfFitLines.get(new Coordinate(i, j, k)).setParameters(mapOfUserFitParameters.get(new Coordinate(i, j, k)));
//                    //Because we do the simultaneous fit over all theta bins, we have the same set of pars for all theta-bins.
//                    mapOfFitLines.get(new Coordinate(i, j, k)).setParameters(mapOfUserFitParameters.get(new Coordinate(i, j)));

//        String title = "kpLine";
////        DCFitDrawer(String name, double xmin, double xmax, int sector, int superlayer,
////            double thetaInDeg, double bField, int KPorVZ, boolean isLinearFit)
//        DCFitDrawer fitKP = new DCFitDrawer(title, 0.0, 1.0, 2, 5, 15.0, 0.5, 1, false);
//        fitKP.setParameters(params);
//        title = "vzLine";
//        DCFitDrawer fitVZ = new DCFitDrawer(title, 0.0, 1.0, 2, 5, 15.0, 0.5, 1, false);
//        fitVZ.setParameters(params);
    }
    
    public static void main(String[] args) {
        Test_calc_Time  test = new Test_calc_Time();
        test.calculateTime();
    }
    
}
