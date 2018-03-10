/*
* Ref:
* https://www.youtube.com/watch?v=gKU7ZeCNbqU MVC Java Tutorial
* What is MVC?
*   => Completely separates the Calculations and Interface from each other
*   => Model:  Provides access to Data and Methods used to work with it
*   => View: The Interface
*   => Controller: Coordinates interactions between the View and Model
*/
package swing.mvc;

/**
 *
 * @author kpadhikari
 */

//This class will do all the calculations. It wont even know that the View exists.
public class CalculatorModel {
    
    private int calculationValue;
    
    public void addTwoNumbers(int firstNumber, int secondNumber) {
        
        calculationValue = firstNumber + secondNumber;
       
    }
    
    public int getCalculationValue() {
        
        return calculationValue;
    }
    
}
