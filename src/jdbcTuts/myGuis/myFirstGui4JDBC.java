/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jdbcTuts.myGuis;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.synth.SynthLookAndFeel;
import varioustests.lookandfeel.SynthApplication;

/**
 *
 * @author kpadhikari
 */


public class myFirstGui4JDBC extends JFrame implements ActionListener {
    private final int nCols = 6;
    private JTextField[] txtFields = new JTextField[nCols]; 
    private String[] txtFieldValues = {"ID","FName","LName","DOB","Class","Email"};//new String[nCols]; //To temporarily store values gotten from above txtFields.

    public myFirstGui4JDBC () {
        initLookAndFeel();

        createView();
        
        //Make window exit application on close
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Set display size (int width, int height) in pixels
        setSize(400, 300);
        //Center the frame to middle of screen
        setLocationRelativeTo(null); 
        //kp: other JFrame object is passed instead of 'null', this frame will open wrt the middle of the first frame
        
        //Disable resize
        //setResizable(false);
        setResizable(true);
    }

    //Initialize all UI components
    public void createView() {
        JPanel panelMain = new JPanel(new BorderLayout());
        //getContentPane().add(panelMain);
        Container pane = getContentPane();
        pane.add(panelMain);
        //pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));        
        //panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));     
        
        JPanel panelBtn = new JPanel();
        panelMain.add(panelBtn, BorderLayout.SOUTH);
        panelBtn.add(makeActivateButton("Print on Console", "cmdPrint"));//(new JButton("Print"));
        
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelMain.add(panelForm, BorderLayout.NORTH);

        
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
        panelForm.add(new JLabel("ID: "),c); //We create objects right here inside add(), because we don't need to do much to them
        c.gridy++; //add another label immediately below above one.
        panelForm.add(new JLabel("First Name: "),c);
        c.gridy++; 
        panelForm.add(new JLabel("Last Name "),c);        
        c.gridy++; 
        panelForm.add(new JLabel("DOB: "),c);        
        c.gridy++; 
        panelForm.add(new JLabel("Class: "),c); 
        c.gridy++; 
        panelForm.add(new JLabel("Email: "),c); 
        
        c.gridx = 1; //kp: Now put corresponding textfields in the 2nd column 
        c.gridy = 0; //kp:Reset it to 0 because we have incremented it 4 times (to reach upto 4)
        c.anchor = GridBagConstraints.LINE_START;
        txtFields[0] = makeActivateTextField(8, "Enter ID", "dbColumnsVar00");
        panelForm.add(txtFields[0],c); //We create objects right here inside add(), because we don't need to do much to them
        // .    But, if we want to handle from outside (update value or read from it), then it's better to create it outside
        // .    and add the object. But, for now, we're not doing much here, so it's fine.
        c.gridy++;
        txtFields[1] = makeActivateTextField(8, "Enter FN", "dbColumnsVar01");
        panelForm.add(txtFields[1],c); 
        c.gridy++;
        txtFields[2] = makeActivateTextField(15, "Enter LN", "dbColumnsVar02");
        panelForm.add(txtFields[2],c); 
        c.gridy++;
        txtFields[3] = makeActivateTextField(10, "Enter BD", "dbColumnsVar03");
        panelForm.add(txtFields[3],c); 
        c.gridy++;
        //panelForm.add(new JPasswordField(10),c); 
        txtFields[4] = makeActivateTextField(10, "Enter CL", "dbColumnsVar04");
        panelForm.add(txtFields[4],c); 
        c.gridy++;
        txtFields[5] = makeActivateTextField(10, "Enter Em", "dbColumnsVar05");
        panelForm.add(txtFields[5],c); 
    }
    

    private JButton makeActivateButton(String caption, String command) {
        JButton b = new JButton(caption);
        b.setActionCommand(command);
        b.addActionListener(this);
        //getContentPane().add(b, constraints);
        return b;
    }

    
    //Please note that tfColumns is the # of columns in the JTextField
    //and dbColumn as in dbColumnVar indicates the # of columns in a database table
    //  makeActivateTextField(20,"nothing","dbColumnVar01");
    private JTextField makeActivateTextField(int tfColumns, String iniText, String command) {
        JTextField tf = new JTextField(tfColumns);
        tf.setText(iniText);
        tf.setActionCommand(command);
        //tf.addActionListener(this);
        //getContentPane().add(b, constraints);
        tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldActionPerformed(evt);
            }
        });
        return tf;
    }

    public void textFieldActionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        int len = command.length(), fieldIndex = -1;
        String fieldName = "", fieldValue = "";

        System.out.println("Dbg: Command = " + command);
        // ########################
        //Process the command string and identify which field was acted on. (clicked, typed ..)
        //https://docs.oracle.com/javase/tutorial/java/data/manipstrings.html (both works)
        fieldName = command.substring(0, len - 2); //(int beginIndex, int endIndex));
        //System.out.println("# = " + command.substring(len-2, len));
        //System.out.println("# = " + command.substring(len-2)); //Both works
        fieldIndex = Integer.parseInt(command.substring(len - 2, len));//Gives a primitive int
        //Alternatively, we can use Integer.valueOf() to get an Integer object, rather than a primitive int.

        //System.out.println(fieldName.equals("lowerLimField") ); //(fieldName == "lowerLimField") returned false.
        if (fieldName.equals("dbColumnsVar")) { //It will require the 'Enter' key pressed after typing something in to the fields
            txtFieldValues[fieldIndex] = txtFields[fieldIndex].getText();
        } 
    }    
    
    public void readTextFieldsAndPutValuesToCorrespondingVariables() {
        for (int i = 0; i < txtFieldValues.length; i++) {
            txtFieldValues[i] = txtFields[i].getText();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        for (int i = 0; i < txtFieldValues.length; i++) {
            System.out.println(txtFieldValues[i]);
            
        }
    }
    
    private static void initLookAndFeel() {
        //
        // Ideas used from 
        // https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/ and
        // https://docs.oracle.com/javase/tutorial/uiswing/examples/lookandfeel/index.html
        //
        initNimbusLookAndFeel();
        //initSynthLookAndFeel();
    }

    private static void initNimbusLookAndFeel() {

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
            System.err.println("Couldn't get specified look and feel ("
                    + "Nimbus" //+ lookAndFeel
                    + "), for some reason.");
            System.err.println("Using the default look and feel.");
            e.printStackTrace();            
        }
    }

    private static void initSynthLookAndFeel() {
        //https://docs.oracle.com/javase/tutorial/uiswing/examples/lookandfeel/index.html
        //private static String synthFile = "buttonSkin.xml";
        String synthFile = "buttonSkin.xml";

        // String lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
        SynthLookAndFeel lookAndFeel = new SynthLookAndFeel();

        try {
            lookAndFeel.load(SynthApplication.class.getResourceAsStream(synthFile),
                    SynthApplication.class);
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            System.err.println("Couldn't get specified look and feel ("
                    + lookAndFeel
                    + "), for some reason.");
            System.err.println("Using the default look and feel.");
            e.printStackTrace();
        }

    }


    //Entry point of program
    public static void main(String[] args) {
        //Create a frame and show it through SwingUtilities
        SwingUtilities.invokeLater(()-> {new myFirstGui4JDBC().setVisible(true);});
    }
}
