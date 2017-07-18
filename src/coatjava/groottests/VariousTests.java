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
public class VariousTests {

    /**
     * @param args the command line arguments
     */
    int[][] Crates = { //[3][5]  //18 crates in total (as many as DCs, one crate per chamber)
        {3, 4, 5, 7, 1},
        {13, 14, 15, 17, 11},
        {23, 24, 25, 27, 21}
      };
    
    public void printIt() {
        for(int i=0; i<3; i++) {
            for(int j=0; j<5; j++) {
                System.out.print(Crates[i][j] + " ");
            }
            System.out.println("");
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
        VariousTests test1 = new VariousTests();
        test1.printIt();
    }
    
}
