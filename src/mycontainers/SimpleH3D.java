/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycontainers;

/**
 *
 * @author kpadhikari
 */
public class SimpleH3D {
    private double[][][] array3D;    
    public double[] axisMarginsX;
    public double[] axisMarginsY;
    public double[] axisMarginsZ;
    private int nBinsX, nBinsY, nBinsZ;
    
    private double minValX, maxValX;
    private double minValY, maxValY;
    private double minValZ, maxValZ;
    private double binWidthX, binWidthY, binWidthZ;
    
    /**
     * Creates a default axis with 1 bin, a minimum value of 0, and maximum
     * value of 1.0.
     */
    /*
    public container3DForTXB() {
        set(1, 0.0, 1.0);
    }
    */
    
    /**
     * Creates a 3D Histogram (like container) class with the specified parameters.
     *
     * @param name the name of the histogram
     * @param nx the number of x axis bins
     * @param xmin the minimum x axis value
     * @param xmax the maximum x axis value
     * @param ny the number of y axis bins
     * @param ymin the minimum y axis value
     * @param ymax the maximum y axis value
     */
    public SimpleH3D(int nx, double xmin, double xmax, int ny,
            double ymin, double ymax, int nz, double zmin, double zmax) {        
        nBinsX = nx;
        nBinsY = ny;
        nBinsZ = nz;

        if (xmin <= xmax) {
            minValX = xmin;
            maxValX = xmax;
        } else {
            minValX = xmax;
            maxValX = xmin;
        }        

        if (ymin <= ymax) {
            minValY = ymin;
            maxValY = ymax;
        } else {
            minValY = ymax;
            maxValY = ymin;
        }
        
        if (zmin <= zmax) {
            minValZ = zmin;
            maxValZ = zmax;
        } else {
            minValZ = zmax;
            maxValZ = zmin;
        }
        
        binWidthX = (maxValX - minValX) / nBinsX;
        axisMarginsX = new double[nBinsX + 1];
        for (int i = 0; i <= nBinsX; i++) {
            axisMarginsX[i] = minValX + i * binWidthX;
        } 
        
        binWidthY = (maxValY - minValY) / nBinsY;
        axisMarginsY = new double[nBinsY + 1];
        for (int i = 0; i <= nBinsY; i++) {
            axisMarginsY[i] = minValY + i * binWidthY;
        } 
        
        binWidthZ = (maxValZ - minValZ) / nBinsZ;
        axisMarginsZ = new double[nBinsZ + 1];
        for (int i = 0; i <= nBinsZ; i++) {
            axisMarginsZ[i] = minValZ + i * binWidthZ;
        }   
        
        array3D = new double [nBinsX][nBinsY][nBinsZ];
    }
    
    public void fill(double x, double y, double z) {
        int binX = (int) ((x - minValX)/binWidthX);
        int binY = (int) ((y - minValY)/binWidthY);
        int binZ = (int) ((z - minValZ)/binWidthZ);
        //System.out.println("bins: " + binX + " " + binY + " " + binZ);
        if (binX >= 0 && binY >= 0 && binZ >= 0 && binX<nBinsX && binY<nBinsY && binZ<nBinsZ) {             
            array3D[binX][binY][binZ] =  array3D[binX][binY][binZ] + 1.0;
            //System.out.println("bins: " + binX + " " + binY + " " + binZ + " Val="+array3D[binX][binY][binZ]);
        }
    }
    public double getBinContent(int binx, int biny, int binz) {
    	if (binx >= 0 && binx < nBinsX && biny >= 0 && biny < nBinsY && binz >= 0 && binz < nBinsZ) {
                /*if(array3D[binx][biny][binz]>0.0)
                System.out.println("val("+binx+","+biny+","+binz+") " + array3D[binx][biny][binz]); */
    		return array3D[binx][biny][binz];
    	}
    	return 0.0; //No need for else here, it will have the same effect
    }  
    
    public double getXBinProj(int binx) {
        double sum = 0;
        for(int j=0; j< nBinsY; j++) {
            for(int k=0; k< nBinsZ; k++) {
                sum = sum + array3D[binx][j][k];
            }
        }
        return sum; 
    }
    
    public double getYBinProj(int biny) {
        double sum = 0;
        for(int j=0; j< nBinsX; j++) {
            for(int k=0; k< nBinsZ; k++) {
                sum = sum + array3D[j][biny][k];
            }
        }
        return sum; 
    }
    
    public double getZBinProj(int binz) {
        double sum = 0;
        for(int j=0; j< nBinsX; j++) {
            for(int k=0; k< nBinsY; k++) {
                sum = sum + array3D[j][k][binz];
            }
        }
        return sum; 
    } 
    public double getXYBinProj(int binx, int biny) {
        double sum = 0;
        for (int k = 0; k < nBinsZ; k++) {
            sum = sum + array3D[binx][biny][k];

        }
        return sum;
    }
    
    public double getYZBinProj(int biny, int binz) {
        double sum = 0;
        for(int j=0; j< nBinsX; j++) {        
                sum = sum + array3D[j][biny][binz];
        }
        return sum; 
    }
    
    public double getXZBinProj(int binx, int binz) {
        double sum = 0;
            for(int k=0; k< nBinsY; k++) {
                sum = sum + array3D[binx][k][binz];
        }
        return sum; 
    }    
}
