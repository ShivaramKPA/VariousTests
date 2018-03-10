/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author kpadhikari
 */
public class CalculatorController {
    
    //It is the only thing that knows about the View and the Model
    private CalculatorView theView;
    private CalculatorModel theModel;
    
    //constructor
    public CalculatorController(CalculatorView theView, CalculatorModel theModel) {
        this.theView = theView;
        this.theModel = theModel;
        
        //Now tell the view that whenever the Calculate button is clicked,
        //     do execute the actionPerformed method that is going to be in the 
        //     inner class named CalculateListener below
        // The input arg below is the object of the inner class (created by
        //     calling the default constructor of the inner class
        // Nowadays, after Java8, one can also use the Lambda-expression which
        //    reduces the code further by defining and instantiating the class
        //    in a single expression i.e. through the Lambda-expression.
        this.theView.addCalculationListener(new CalculateListener());
    }
    
    //Inner class (will have the default constructor, and that's what is used above)
    class CalculateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            int firstNumber, secondNumber = 0;
            
            try {
                //Get the two inputs from the View
               firstNumber = theView.getFirstNumber();
               secondNumber = theView.getSecondNumber();
               
               //Process these two numbers using the Model
               theModel.addTwoNumbers(firstNumber, secondNumber);
               
               //Now send the result of the processing back to the View
               theView.setCalcSolution(theModel.getCalculationValue());
                
            } catch (NumberFormatException ex) {
                
                theView.displayErrorMessage("You Need to Enter two Integers.");
            }
            
        }
        
    }
    
    
    
}
