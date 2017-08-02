/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.components;
// Most of the idea of this code is from the followign youtube tutorial:
// .   https://www.youtube.com/watch?v=l7nFNdUr52U
// .     FlowLayout organizes the components in a horizontal manner.
// .     If a next component being added doesn't fit to width of the current horizonal space,
//       then, it shifts over and creates the next line.
// . For example, if the container is resizable and you shrink or stretch the panel/container
// .   later, then the end components will move to upper or lower horizontal line/space if 
// .   the width becomes inadequate or wider.


import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author kpadhikari
 */
public class TryFlowLayout extends JFrame {
    public TryFlowLayout() {
        createView();
        
        setTitle("FlowLaout Demo");
        //Make window exit application on close
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Set display size
        setSize(500, 200);
        //Center the frame to middle of screen
        setLocationRelativeTo(null); 
        //kp: other JFrame object is passed instead of 'null', this frame will open wrt the middle of the first frame
        
        //Disable resize
        //setResizable(false);
        setResizable(true);
    }
    
    //Initialize all UI components
    public void createView() {
        JPanel panelMain = new JPanel(new FlowLayout());
        //Eqvt to: JPanel panelMain = new JPanel(); panelMain.setLayout(new FlowLayout());
        getContentPane().add(panelMain);

        JLabel label01 = new JLabel("FlowLayout organizes components in a horizontal manner.");
        panelMain.add(label01);
        JLabel label02 = new JLabel("If next component doesn't fit, it goes to next line. ");
        panelMain.add(label02);
        JLabel label03 = new JLabel("If window is resized: ");
        panelMain.add(label03);
        JLabel label04 = new JLabel("components may flow to next or previous line.");
        panelMain.add(label04);
        panelMain.add(new JButton("Inactive Button"));

        
        JLabel label1 = new JLabel("Username: ");
        panelMain.add(label1);
        JTextField fieldUsername = new JTextField(20);
        panelMain.add(fieldUsername);

        JLabel label2 = new JLabel("Password: ");
        panelMain.add(label2);
        JTextField fieldPassword = new JPasswordField(20);
        panelMain.add(fieldPassword);

    }
    
    //Entry point of program
    public static void main(String[] args) {
        //Create a frame and show it through SwingUtilities
        SwingUtilities.invokeLater(()-> {new TryFlowLayout().setVisible(true);});
    }
}
