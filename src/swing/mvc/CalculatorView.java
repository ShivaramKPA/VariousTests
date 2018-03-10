/*
 * View part is probably the most complicated part of MVC.
*     particularly due to the need to have the graphical (user) interface (GUI)
*     with the capability to perform and catch events, whenever
*     the user is going to click on things.
*
*  The model doesn't know anyting about the view. It doesn't even know
*     it exists. And, this is not specific to Java, but any other lanaguage
*     that uses the MVC model.
 */
package swing.mvc;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author kpadhikari
 */
public class CalculatorView extends JFrame {
    
    private JTextField firstNumber = new JTextField(10);
    private JLabel additionLabel = new JLabel("+");
    private JTextField secondNumber = new JTextField(10);
    private JButton calculateButton = new JButton("Calculate");
    private JTextField calcSolution = new JTextField(10);
    
    //Constructor
    CalculatorView() {
        JPanel calcPanel = new JPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,200);
        
        calcPanel.add(firstNumber);
        calcPanel.add(additionLabel);
        calcPanel.add(secondNumber);
        calcPanel.add(calculateButton);
        calcPanel.add(calcSolution);
        
        this.add(calcPanel);
    }
    
    public int getFirstNumber() {
        
        return Integer.parseInt(firstNumber.getText());
        
    }
    
    public int getSecondNumber() {
        
        return Integer.parseInt(secondNumber.getText());
        
    }

    public int getCalcSolution() {
        
        return Integer.parseInt(calcSolution.getText());
        
    } 
    

    public void setCalcSolution(int solution) {
        
        calcSolution.setText(Integer.toString(solution));
        
    }
    
    
    //Following is probably the most complicated part of the whole thing.
    //    The method is a way to alert the controller when a little button 
    //    is clicked on the interface
    //kp: Here, the argument is the object of a class that implements the 
    //     interface 'ActionListener'. Usually, that implementation is
    //     as well as instantiation is done directly through lambda-expression
    //     since Java8 or through an inner class as can be seen in the 
    //      CalculatorController.java file.
    //  As you'll see from the Controller file and the main file MVCCalculator.java
    //     when the View object is created, the interface has bare elements 
    //     with no listener plugged into them. But, when the object of Controller
    //     is created, the listener is added (plugged in) to 'Calculate' button.
    // So, we can perhaps say that the Controller makes alive (gives life to) the 
    //     initially created but still lifeless View GUI.
    void addCalculationListener(ActionListener listenerForCalcButton) {
        
        calculateButton.addActionListener(listenerForCalcButton);
          
    }
    
    //Will be called if the Calculate button is clicked without entering
    // the numbers to be added.
    void displayErrorMessage(String errorMessage) {
        
        JOptionPane.showMessageDialog(this, errorMessage);
    }
}
