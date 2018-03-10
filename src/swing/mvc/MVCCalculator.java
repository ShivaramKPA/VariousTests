/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.mvc;

/**
 *
 * @author kpadhikari
 */
public class MVCCalculator {
    
    //kp: I think the ideal/better way to run it is to dispatch it
    //    to the EventDistpatchThread (EDT) using invokeLater() of SwingUtility
    //   
    public static void main(String[] args) {
        CalculatorView theView = new CalculatorView();
        CalculatorModel theModel = new CalculatorModel();
        CalculatorController theController = new CalculatorController(theView, theModel);
        theView.setVisible(true);
    }
    
}
