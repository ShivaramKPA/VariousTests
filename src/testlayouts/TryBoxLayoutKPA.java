/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testlayouts;

//http://www.java2s.com/Tutorial/Java/0240__Swing/UsingaBoxLayoutManager.htm
import components.SpringUtilities;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpringLayout;

public class TryBoxLayoutKPA {

    public static void main(String[] args) {
        JFrame aWindow = new JFrame("This is a Box Layout");
        aWindow.setBounds(30, 30, 500, 500);
        aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create left column of radio buttons
        Box left = Box.createVerticalBox();
        ButtonGroup radioGroup = new ButtonGroup();
        JRadioButton rbutton;
        radioGroup.add(rbutton = new JRadioButton("Red"));
        left.add(rbutton);
        radioGroup.add(rbutton = new JRadioButton("Green"));
        left.add(rbutton);
        radioGroup.add(rbutton = new JRadioButton("Blue"));
        left.add(rbutton);
        radioGroup.add(rbutton = new JRadioButton("Yellow"));
        left.add(rbutton);
        Box center = Box.createVerticalBox();
        center.add(new JCheckBox("A"));
        center.add(new JCheckBox("B"));
        center.add(new JCheckBox("C"));
        center.add(new JCheckBox("D"));

        // Create right column of radio buttons
        Box right = Box.createVerticalBox();
        
        ButtonGroup radioGroupR = new ButtonGroup();
        JRadioButton rbuttonR;
        radioGroup.add(rbuttonR = new JRadioButton("Red"));
        right.add(rbuttonR);
        radioGroup.add(rbuttonR = new JRadioButton("Green"));
        right.add(rbuttonR);
        radioGroup.add(rbuttonR = new JRadioButton("Blue"));
        right.add(rbuttonR);
        radioGroup.add(rbuttonR = new JRadioButton("Yellow"));
        right.add(rbuttonR);
        
        Box rightMost = Box.createVerticalBox();
        JRadioButton rbuttonR2;
        rbuttonR2 = new JRadioButton("Red"); rightMost.add(rbuttonR2);
        rbuttonR2 = new JRadioButton("Green"); rightMost.add(rbuttonR2);
        rbuttonR2 = new JRadioButton("Blue"); rightMost.add(rbuttonR2);
        rbuttonR2 = new JRadioButton("Yellow"); rightMost.add(rbuttonR2);
        
        Box top = Box.createHorizontalBox();
        top.add(left);
        top.add(center);
        top.add(right);
        top.add(rightMost);
        
        //JPanel formPanel = createEntryFields();
        
        Container content = aWindow.getContentPane();
        content.setLayout(new BorderLayout());
        content.add(top, BorderLayout.CENTER);
        //content.add(createEntryFields());//, BorderLayout.Line_END);
        aWindow.pack();
        aWindow.setVisible(true);
    }
        
}
