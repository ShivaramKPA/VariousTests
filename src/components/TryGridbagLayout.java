/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;//https://www.youtube.com/watch?v=Ts5fsHXIuvI

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author kpadhikari
 */
public class TryGridbagLayout extends JFrame {
    public TryGridbagLayout() {
        createView();
        
        //Make window exit application on close
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Set display size
        setSize(400, 200);
        //Center the frame to middle of screen
        setLocationRelativeTo(null); 
        //kp: other JFrame object is passed instead of 'null', this frame will open wrt the middle of the first frame
        
        //Disable resize
        //setResizable(false);
        setResizable(true);
    }
    
    //Initialize all UI components
    public void createView() {
        JPanel panelMain = new JPanel();
        getContentPane().add(panelMain);
        
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelMain.add(panelForm);
        
        GridBagConstraints c = new GridBagConstraints();
        //Think of it as an excel sheet where a cell is defined by letters for columns
        // .   and integers for rows. For example A1 is first cell in first col., B1 is first cell in 
        // .   second column, C4 is 4th cell in third column and so on
        //In the similar fashion gridx and gridy represent the column and row ids respectively.
        //  For example, gridx=3 and gridy=5 means the 6th grid-cell on the 4th column and so forth.
        c.gridx = 0;
        c.gridy = 0;
        
        c.anchor = GridBagConstraints.LINE_END; //kp: to align components to the right side of grid cells.
        //Add all of the following labels in the first column.
        panelForm.add(new JLabel("First Name: "),c); //We create objects right here inside add(), because we don't need to do much to them
        c.gridy++; //add another label immediately below above one.
        panelForm.add(new JLabel("Last Name: "),c);
        c.gridy++; 
        panelForm.add(new JLabel("Email: "),c);        
        c.gridy++; 
        panelForm.add(new JLabel("Username: "),c);        
        c.gridy++; 
        panelForm.add(new JLabel("Password: "),c); 
        
        c.gridx = 1; //kp: Now put corresponding textfields in the 2nd column 
        c.gridy = 0; //kp:Reset it to 0 because we have incremented it 4 times (to reach upto 4)
        c.anchor = GridBagConstraints.LINE_START;
        panelForm.add(new JTextField(8),c); //We create objects right here inside add(), because we don't need to do much to them
        // .    But, if we want to handle from outside (update value or read from it), then it's better to create it outside
        // .    and add the object. But, for now, we're not doing much here, so it's fine.
        c.gridy++;
        panelForm.add(new JTextField(8),c); 
        c.gridy++;
        panelForm.add(new JTextField(15),c); 
        c.gridy++;
        panelForm.add(new JTextField(10),c); 
        c.gridy++;
        panelForm.add(new JPasswordField(10),c); 

    }
    
    //Entry point of program
    public static void main(String[] args) {
        //Create a frame and show it through SwingUtilities
        SwingUtilities.invokeLater(()-> {new TryGridbagLayout().setVisible(true);});
    }
}
