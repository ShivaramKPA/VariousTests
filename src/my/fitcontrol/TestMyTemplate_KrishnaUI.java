/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.fitcontrol;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.synth.SynthLookAndFeel;
import varioustests.lookandfeel.SynthApplication;

/**
 *
 * @author kpadhikari
 */
public class TestMyTemplate_KrishnaUI extends JFrame {
    
    JPanel panelMain;
    private static final int nFitPars = 9;
    private JTextField[] lowerLimField = new JTextField[nFitPars];
    private JTextField[] initValField  = new JTextField[nFitPars];
    private JTextField[] upperLimField = new JTextField[nFitPars];
    private JCheckBox[] checkBoxes = new JCheckBox[nFitPars];
    
    String[] SecOrSLnum = {"1", "2", "3", "4", "5", "6"}; 
    String[] errorTypes = {"RMS in x-slice", "RMS/sqrt(N)","1.0"};
    JComboBox JComboBoxSec, JComboBoxSL, JComboBoxErr;// = new JComboBox(SecOrSLnum);
    JTextArea jTextArea1; //to show Fit Results
    JButton jButtonExit;
    
    
    //Components used to prepare the RangeSlider object
    private JLabel rangeSliderLabel1 = new JLabel();
    private JLabel rangeSliderValue1 = new JLabel();
    private JLabel rangeSliderLabel2 = new JLabel();
    private JLabel rangeSliderValue2 = new JLabel();
    private RangeSlider rangeSlider = new RangeSlider();
    private JPanel rangeSliderPanel;
    
    //To be used by myRangeSliderNew()
    double xMin = 0.0;
    double xMax = 1.0;
    String options = "";
    // This mess is due to the slider only working with integer values
    int xSliderMin = 0;
    int xSliderMax = 1000000;
    double currentRangeMin = 0.0;
    double currentRangeMax = 100.0;
   
    
    //private static final Insets insets = new Insets(0, 0, 0, 0);//kp: minimum empty space between grids or components    
    //   Just like the spaces between pads in TCanvas that I used to control by giving very small numbers
    //  for the last two arguments in Divide(nCol or nx, nRow or ny, xMargin, yMargin);
    //  we can also use Insets(..) to control the margins/spaces between the enclosing window and the containers
    //  that occupy each of the grids.
    private static final Insets insets = new Insets(5, 5, 5, 5); //kp: didn't change the spacing
    
    public TestMyTemplate_KrishnaUI() {
        initLookAndFeel();
        
        createView();

        //Make window exit application on close
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Set display size (int width, int height) in pixels
        setSize(600, 600);
        //Center the frame to middle of screen
        setLocationRelativeTo(null);
        //kp: other JFrame object is passed instead of 'null', this frame will open wrt the middle of the first frame

        //Disable resize
        //setResizable(false);
        setResizable(true);
    }

    //Initialize all UI components
    public void createView() {
        //panelMain = new JPanel(new BorderLayout());
        panelMain = new JPanel(new BorderLayout());
        getContentPane().add(panelMain);
        
        //Add new panel at the bottom & add create TextArea for Fit Results
        createTopPanelForParameterForm(); 
        
        //Add new panel at the bottom & add create TextArea for Fit Results
        createPanelAndAddTextArea(); 
        
        //Add new panel at the bottom & add exit button (with listner)
        createPanelAndAddExitButton(); 
    }
    
    public void createTopPanelForParameterForm() {
        JPanel panelForm = new JPanel(new GridBagLayout());
        Dimension dim = new Dimension(650, 400);
        //panelForm.setSize(dim); //Didn't have any effect
        //panelForm.setBorder(BorderFactory.createLineBorder(Color.black));
        panelForm.setBorder(BorderFactory.createTitledBorder("Set Parameters"));
        TitledBorder titledBorder = (TitledBorder) panelForm.getBorder();
        titledBorder.setTitleColor(Color.blue);
        //titledBorder.setLineBorder(Color.blue);

        GridBagConstraints c = new GridBagConstraints();
        //Think of it as an excel sheet where a cell is defined by letters for columns
        // .   and integers for rows. For example A1 is first cell in first col., B1 is first cell in 
        // .   second column, C4 is 4th cell in third column and so on
        //In the similar fashion gridx and gridy represent the column and row ids respectively.
        //  For example, gridx=3 and gridy=5 means the 6th grid-cell on the 4th column and so forth.

        //============= Now, add components in the first row.
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END; //kp: to align components to the right side of grid cells.        
        panelForm.add(new JLabel("<html><font color='black'> <b>Sector</b></font></html>"), c); //We create objects right here inside add(), because we don't need to do much to them
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;   
        JComboBoxSec = new JComboBox(SecOrSLnum);
        JComboBoxSec.setSelectedIndex(1); //Select second superlayer by default (KPP data is only on 2nd superlayer)
        panelForm.add(JComboBoxSec, c); 
        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;   
        panelForm.add(new JLabel("<html><font color='black'> <b>Superlayer</b></font></html>"), c); 
        c.gridx = 3;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START; 
        JComboBoxSL = new JComboBox(SecOrSLnum);
        panelForm.add(JComboBoxSL, c);
        //============= Done adding components in the first row grids.
        
        //Now, add labels for parameters names in the first column.
        c.gridx = 0;
        c.gridy = 1;

        c.anchor = GridBagConstraints.LINE_START;//LINE_END //kp: to align components to the left/right side of grid cells.
        //Add all of the following labels in the first column.
        panelForm.add(new JLabel("<html><font color='black'> <b>Parameter</b></font></html>"), c); //We create objects right here inside add(), because we don't need to do much to them
        // .    But, if we want to handle from outside (update value or read from it), then it's better to create it outside
        // .    and add the object. But, for now, we're not doing much here, so it's fine.
        c.gridy++; //add another label immediately below above one.
        panelForm.add(new JLabel("v0 "), c);
        c.gridy++;
        panelForm.add(new JLabel("deltanm "), c);
        c.gridy++;
        panelForm.add(new JLabel("tmax "), c);
        c.gridy++;
        panelForm.add(new JLabel("distbeta "), c);
        c.gridy++;
        panelForm.add(new JLabel("delta_bfield_coeff "), c);
        c.gridy++;
        panelForm.add(new JLabel("b1 "), c);
        c.gridy++;
        panelForm.add(new JLabel("b2 "), c);
        c.gridy++;
        panelForm.add(new JLabel("b3 "), c);
        c.gridy++;
        panelForm.add(new JLabel("b4 "), c);
        
        //Next, add another column for JTextfields to carry the lower limits of the parameter values
        c.gridx = 1; //kp: Now put corresponding textfields in the 2nd column 
        c.gridy = 1; //kp:Reset it to 0 because we have incremented it 4 times (to reach upto 4)
        c.anchor = GridBagConstraints.LINE_START;
        panelForm.add(new JLabel("<html><font color='black'> <b>Lower Limit</b></font></html>"), c); 
        
        for (int i = 0; i < nFitPars; i++) {
            lowerLimField[i] = new JTextField(10);
            c.gridy++;
            panelForm.add(lowerLimField[i],c);
        }

        //Next, add another column for JTextfields to carry the initial parameter values
        c.gridx = 2; //kp: Now put corresponding textfields in the 2nd column 
        c.gridy = 1; //kp:Reset it to 0 because we have incremented it 4 times (to reach upto 4)
        c.anchor = GridBagConstraints.LINE_START;
        panelForm.add(new JLabel("<html><font color='black'> <b>Inital Value</b></font></html>"), c); 
        
        for (int i = 0; i < nFitPars; i++) {
            initValField[i] = new JTextField(10);
            c.gridy++;
            panelForm.add(initValField[i],c);
        }
        
        //Next, add another column for JTextfields to carry the upper limits of the parameter values
        c.gridx = 3; //kp: Now put corresponding textfields in the 2nd column 
        c.gridy = 1; //kp:Reset it to 0 because we have incremented it 4 times (to reach upto 4)
        c.anchor = GridBagConstraints.LINE_START;
        panelForm.add(new JLabel("<html><font color='black'> <b>Upper Limit</b></font></html>"), c); 
        
        for (int i = 0; i < nFitPars; i++) {
            upperLimField[i] = new JTextField(10);
            c.gridy++;
            panelForm.add(upperLimField[i],c);
        }

        //Next, add another column for JTextfields to carry the upper limits of the parameter values
        c.gridx = 4; //kp: Now put corresponding textfields in the 2nd column 
        c.gridy = 1; //kp:Reset it to 0 because we have incremented it 4 times (to reach upto 4)
        c.anchor = GridBagConstraints.LINE_START;
        panelForm.add(new JLabel("<html><font color='black'> <b>Fix it?</b></font></html>"), c); 
        
        for (int i = 0; i < nFitPars; i++) {
            checkBoxes[i] = new JCheckBox("Fix me");
            //Above line eqvt to: checBox = new JCheckBox(); checkBox.SetText("Fix me");
            c.gridy++;
            panelForm.add(checkBoxes[i],c);
        } 
        
        //Now add my Range slider to the first cell of the 11th row:
        c.gridx = 0;
        c.gridy = 11;
        //MyRangeSlider();
        //addComponent(panelForm, rangeSliderPanel, c.gridx, c.gridy, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        myRangeSliderNew();
        //panelForm.add(rangeSliderPanel,c);
        addComponent(panelForm, rangeSliderPanel, c.gridx, c.gridy, 3, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        
        //Now add the combobox to select error types to be used in Chisq calculation
        c.gridx = 0;
        c.gridy = 12;
        c.anchor = GridBagConstraints.LINE_END;   
        panelForm.add(new JLabel("<html><font color='black'> <b>Uncertainty</b></font></html>"), c); 
        c.gridx = 1;
        c.gridy = 12;
        c.anchor = GridBagConstraints.LINE_START; 
        JComboBoxErr = new JComboBox(errorTypes);
        panelForm.add(JComboBoxErr, c);
        JComboBoxErr.setSelectedIndex(2);
        
        c.gridx = 3;
        c.gridy = 12;
        c.anchor = GridBagConstraints.LINE_START;   
        panelForm.add(new JButton("<html><font color='black'> <b>Go Fit It</b></font></html>"), c);    
     
        panelMain.add(panelForm,BorderLayout.NORTH);
    }
    
    public void createPanelAndAddTextArea() {
        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(new JLabel("<html><font color='black'> <b>Fit Results</b></font></html>"),BorderLayout.NORTH);
        jTextArea1 = new JTextArea();
        //HTML spaces: &nbsp;    &ensp;   &emsp;  //http://www.wikihow.com/Insert-Spaces-in-HTML
        String title = "<html><font color='blue'> " 
                + "v0 &ensp; deltanm &ensp; tmax &ensp; distbeta &ensp; delta_bfield_coeff.  &ensp; "
                + "b1 &ensp; b2 &ensp; b3 &ensp; b4</font></html>";
        jTextArea1.setBorder(BorderFactory.createTitledBorder(title));
        JScrollPane jScrollPane = new JScrollPane (jTextArea1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        /*
        //creating an anonymous inner class implementing ActionListener interface by providing an actionPerformed method. 
        jButtonExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        */
        //centralPanel.add(jTextArea1, BorderLayout.CENTER);
        centralPanel.add(jScrollPane, BorderLayout.CENTER);
        panelMain.add(centralPanel, BorderLayout.CENTER);
    }    
    
    public void createPanelAndAddExitButton() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        jButtonExit = new JButton("Exit");
        //creating an anonymous inner class implementing ActionListener interface by providing an actionPerformed method. 
        jButtonExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jButtonExit.setPreferredSize(new Dimension(50,20));
        bottomPanel.add(jButtonExit, BorderLayout.CENTER);//LINE_END);
        //bottomPanel.setPreferredSize();
        panelMain.add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private static void addComponent(Container container, Component component, int gridx, int gridy,
            int gridwidth, int gridheight, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
                anchor, fill, insets, 0, 0);
        container.add(component, gbc);
    }
      
    public void MyRangeSlider() {
        int min = 0, max = 100000;
        int range = max - min;
        rangeSliderPanel = new JPanel();
        rangeSliderPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        rangeSliderPanel.setLayout(new GridBagLayout());
        
        rangeSliderLabel1.setText("Lower value:");
        rangeSliderLabel2.setText("Upper value:");
        rangeSliderValue1.setHorizontalAlignment(JLabel.LEFT);
        rangeSliderValue2.setHorizontalAlignment(JLabel.LEFT);
        
        rangeSlider.setPreferredSize(new Dimension(200, rangeSlider.getPreferredSize().height));
        rangeSlider.setMinimum(min);
        rangeSlider.setMaximum(max);
        
        // Add listener to update display.
        rangeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                RangeSlider slider = (RangeSlider) e.getSource();
                rangeSliderValue1.setText(String.valueOf(slider.getValue()));
                rangeSliderValue2.setText(String.valueOf(slider.getUpperValue()));
            }
        });

        rangeSliderPanel.add(rangeSliderLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
        rangeSliderPanel.add(rangeSliderValue1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));
        rangeSliderPanel.add(rangeSliderLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
        rangeSliderPanel.add(rangeSliderValue2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 6, 0), 0, 0));
        rangeSliderPanel.add(rangeSlider      , new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        // Initialize values.
        rangeSlider.setValue(30*range/100);      //Show lower handle at 30% of the range//Currently it shows only upto 50
        rangeSlider.setUpperValue(70*range/100); //Show upper handle at 70% of the range
    }    
    
    
    private void myRangeSliderNew() {
        //Much of the code here is copied from
        //  https://github.com/KPAdhikari/groot/blob/master/src/main/java/org/jlab/groot/ui/FitPanel.java
        rangeSliderPanel = new JPanel(new GridLayout(2, 1));
        JPanel rangeSelector = new JPanel();
        //JLabel xLabel = new JLabel("<html> <b> X&#770; </b> </html>");
        JLabel xLabel = new JLabel("<html> <b> x/X: </b> </html>");
        RangeSlider slider = new RangeSlider();
        slider.setMinimum((int) xSliderMin);
        slider.setMaximum((int) xSliderMax);
        slider.setValue((int) xSliderMin);
        slider.setUpperValue((int) xSliderMax * 80/100);

        currentRangeMin = slider.getValue() * (xMax - xMin) / (double) (xSliderMax - xSliderMin) + xMin;
        currentRangeMax = slider.getUpperValue() * (xMax - xMin) / (double) (xSliderMax - xSliderMin) + xMin;
        JLabel rangeSliderValue1 = new JLabel("" + String.format("%4.2f", currentRangeMin));
        JLabel rangeSliderValue2 = new JLabel("" + String.format("%4.2f", currentRangeMax));
        //fitFunction.setRange(currentRangeMin, currentRangeMax);

        rangeSelector.add(xLabel);
        rangeSelector.add(rangeSliderValue1);
        rangeSelector.add(slider);
        rangeSelector.add(rangeSliderValue2);
        rangeSliderPanel.add(rangeSelector);

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                RangeSlider slider = (RangeSlider) e.getSource();
                currentRangeMin = slider.getValue() * (xMax - xMin) / (double) (xSliderMax - xSliderMin) + xMin;
                currentRangeMax = slider.getUpperValue() * (xMax - xMin) / (double) (xSliderMax - xSliderMin) + xMin;
                rangeSliderValue1.setText(String.valueOf("" + String.format("%4.2f", currentRangeMin)));
                rangeSliderValue2.setText(String.valueOf("" + String.format("%4.2f", currentRangeMax)));
                // System.out.println("currentRangeMin:"+currentRangeMin+"
                // xOffset:"+xOffset);
                // fitFunction.setRange(currentRangeMin, currentRangeMax);
            }
        });
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
        SwingUtilities.invokeLater(() -> {
            new TestMyTemplate_KrishnaUI().setVisible(true);
        });
    }
}
