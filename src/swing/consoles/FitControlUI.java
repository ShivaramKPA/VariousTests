/*
 * here I have a main panel with Box layout to put several subpanels of equal width
 *   arranged (layed out) in a vertical stack
*  Then a (sub)panel (second one at the moment - 8/11/17) that uses GridLayout (4 rows * 7 columns) 
*    which uses 1 column for rows, three columns for buttons (like a calculator), 2 for
*    I observed that, once the # of rwos is defined, the # of columns didn't really matter 
 */
//package org.jlab.dc_calibration.domain;
package swing.consoles;

/**
 *
 * @author kpadhikari &
 * http://www.mathcs.emory.edu/~cheung/Courses/377/Syllabus/8-JDBC/GUI/layout.html
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.text.DefaultEditorKit;
//
import static swing.consoles.Constants.debug;
import static swing.consoles.Constants.nFitPars;
import static swing.consoles.Constants.nSL;
import static swing.consoles.Constants.nSectors;
import static swing.consoles.Constants.outFileForFitPars;
import static swing.consoles.Constants.parSteps;
//import varioustests.lookandfeel.SynthApplication;

public class FitControlUI extends JFrame
        implements ActionListener {

    private final int nFieldRows = nFitPars; //Used to handle the field rows
    private final int nLabelRows = 1;
    private final int nRows = nFieldRows + nLabelRows; //Used to define the total # of rows in the Grid Layout.
    private final int nColumns = 3;//7; //This # didn't really matter (had here but used 7 columns of elements)
    private JLabel[] col0Labels = new JLabel[nFieldRows]; //Col1 
    private JTextField[] lowerLimFields = new JTextField[nFieldRows];  //Col2
    private JTextField[] initValueFields = new JTextField[nFieldRows]; //Col3
    private JTextField[] upperLimFields = new JTextField[nFieldRows];  //Col4
    private JTextField[] stepSizeFields = new JTextField[nFieldRows];  //Col5  
    private JCheckBox[] col6CheckBoxes = new JCheckBox[nFieldRows];    //Col6
    //Following strings will be used as commands to control actionListener & actionPerformed
    String[] lowerLimFieldNames = new String[nFieldRows];
    String[] initValueFieldNames = new String[nFieldRows];
    String[] upperLimFieldNames = new String[nFieldRows];
    String[] stepSizeFieldNames = new String[nFieldRows];
    String[] col6CheckBoxNames = new String[nFieldRows];

    private JPanel MyGridLayoutPanel;
    private JPanel topPanelWithComboBoxes, sliderPanel, buttonsPanel, panelWithTextArea;
    private JPanel bottomPanelForButtons;

    String[] SecOrSLnum = {"1", "2", "3", "4", "5", "6"};
    String[] errorTypes = {"RMS in x-slice", "RMS/sqrt(N)", "1.0"};
    String[] ccdbVariations = {"default", "dc_test1", "dc_test2"};
    JComboBox JComboBoxSec, JComboBoxSL, JComboBoxCcdbVars, JComboBoxErr;// = new JComboBox(SecOrSLnum);
    JTextArea jTextAreaForFitResults; //to show Fit Results
    JButton jButtonExit;
    private int width = 700, height = 600;

    //To be used by myRangeSliderNew()
    double xMin = 0.0;
    double xMax = 1.0;
    String options = "";
    private JPanel rangeSliderPanel;
    // This mess is due to the slider only working with integer values
    int xSliderMin = 0;
    int xSliderMax = 1000000;
    double xNormCurrentSliderMin = 0.0;
    double xNormCurrentSliderMax = 100.0;

    JButton buttonAngleSelection, buttonGoFitIt;
    JButton buttonSliceViewer, buttonResiduals, buttonTimes, buttonBfield, buttonLocalAngle, buttonExit;

    private int gSector = 2;
    private int gSuperlayer = 1;
    private String ccdbVariation = "dc_test1";
    private int xMeanErrorType = 2; //0: RMS, 1=RMS/sqrt(N), 2 = 1.0 (giving equal weight to all profile means)
    private boolean[] checkboxVal = {false, false, false, false, false, false, false, false, false, false};
    private boolean checkBoxFixAll = false;
    public boolean[] selectedAngleBins //= new boolean[nThBinsVz];
            = {false, false, false, false, true, true, true, false, false, false, true, true, true, false, false, false, false};
    //By default, I am skipping first and last four and the middle three local angle bins when I am doing the t-vs-x fits.

//    private String[] pName = {"'v0'", "'deltanm'", "'tmax'", "'distbeta'",
//        "'delta_bfield_coeff'", "'b1'", "'b2'", "'b3'", "'b4'", "deltaT0"};
    private double[][] prevFitPars = {
        {0.00425851, 1.45006, 154.907, 0.0544372, 0.162558, 0.4, -2, 10, -6.5, 5.0},
        {0.00507441, 1.54967, 174.951, 0.0506063, 0.149833, 0.4, -2, 10, -6.5, 5.0},
        {0.00470000, 1.50000, 300.000, 0.0500000, 0.160000, 0.4, -2, 10, -6.5, 5.0},
        {0.00470000, 1.50000, 320.000, 0.0500000, 0.160000, 0.4, -2, 10, -6.5, 5.0},
        {0.00450873, 1.38522, 479.106, 0.0514813, 0.167213, 0.4, -2, 10, -6.5, 5.0},
        {0.00482890, 1.56417, 505.953, 0.0519927, 0.147450, 0.4, -2, 10, -6.5, 5.0}
    };
    private double[][] resetFitPars = new double[nSL][nFitPars];
    private double[][] resetFitParsLow = new double[nSL][nFitPars];
    private double[][] resetFitParsHigh = new double[nSL][nFitPars];
    private double[][] resetFitParSteps = new double[nSL][nFitPars];
    private double[][][] parsFromCCDB_default = new double[nSectors][nSL][nFitPars];//nFitPars = 9
    private double[][][] parsFromCCDB_dc_test1 = new double[nSectors][nSL][nFitPars];//nFitPars = 9    
    private double[][][] parsFromCCDB_dc_test2 = new double[nSectors][nSL][nFitPars];//nFitPars = 9  
    private double xNormLow = 0.0, xNormHigh = 0.8;
    DC_TimeToDistanceFitter fitter;
    //FitControlBinSelectionUIold binSelector;
    FitControlBinSelectionUI binSelector;

    //public FitControlUI() {
    public FitControlUI(DC_TimeToDistanceFitter fitter) {

        System.out.println("kp: Hello from FitControlUI(..) constructor and\n"
                + "\t I am currently on " + Thread.currentThread().getName() + " thread.\n");

        this.fitter = fitter;
        initLookAndFeel();
        createNamesForTextFieldsToUseAsCommands();
        getParametersFromCCDB();
        createAndShowGUI();

        int sector = Integer.parseInt(JComboBoxSec.getSelectedItem().toString());
        int superlayer = Integer.parseInt(JComboBoxSL.getSelectedItem().toString());
        ccdbVariation = JComboBoxCcdbVars.getSelectedItem().toString(); //0 for default, 1 for dc_test1 tables
//
        putCCDBvaluesToResetArrays(sector, ccdbVariation); //Initializing reset arrays for par, parLow, & parHigh
        putStepSizeFromConstantsToResetArrays(sector);     //Initializing reset array for stepSizes
        assignParValuesToTextFields(sector, superlayer);   //Make the numbers in reset arrays show up in the text fields
//
        openFileToWriteFitParameters(); //7/20/17        
    }

    public void createNamesForTextFieldsToUseAsCommands() {
        //Following strings will be used as commands to control actionListener & actionPerformed 
        for (int i = 0; i < nFieldRows; i++) {
            lowerLimFieldNames[i] = String.format("lowerLimField%02d", i);
            initValueFieldNames[i] = String.format("initValueField%02d", i);
            upperLimFieldNames[i] = String.format("upperLimField%02d", i);
            stepSizeFieldNames[i] = String.format("stepSizeField%02d", i);
            col6CheckBoxNames[i] = String.format("col6CheckBox%02d", i);
        }
    }

    public void createAndShowGUI() {
//        JFrame f = new JFrame("Fit Control");
//        //JFrame f = new JFrame(); f.setTitle("Fit Control");
//        f.setSize(width, height);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        //We must get the ContentPane of the JFrame to add anything. 
//        Container pane = f.getContentPane();

        //Following lines invokes the methods of JFrame class that we extend here.
        setTitle("Fit Control");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = getContentPane();

        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        createAndAddComponentsToTopPanelWithComboBoxes();
        pane.add(topPanelWithComboBoxes);

        JPanel emptyPanel1 = createAnEmptyPanel(0);//Empty panel of zero width
        pane.add(emptyPanel1);

        createAndAddComponentsToMyGridLayoutPanel();
        pane.add(MyGridLayoutPanel);//, "Center"); // Paste MyGridLayoutPanel into JFrame   

        createAddComponentsToPanelWithSlider();
        pane.add(sliderPanel);

        createAndComponentsToButtonsPanel();
        pane.add(buttonsPanel);

        createAndAddComponentsToPanelWithTextArea();
        pane.add(panelWithTextArea);

        createAndAddComponentsToBottomPanelForButtons();
        pane.add(bottomPanelForButtons);
//        f.pack(); //Needed if we have more than one elements (panes) added to the frame
//        f.setVisible(true);
        pack(); //Needed if we have more than one elements (panes) added to the frame
        setVisible(true);
    }

    //It will contain only three comboboxes and their labels
    public void createAndAddComponentsToTopPanelWithComboBoxes() {
        topPanelWithComboBoxes = new JPanel(new GridBagLayout());
        topPanelWithComboBoxes.setPreferredSize(new Dimension(width, 40));
        //buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
//        buttonsPanel.setBorder(BorderFactory.createTitledBorder("Set Parameters"));
//        TitledBorder titledBorder = (TitledBorder) buttonsPanel.getBorder();
//        titledBorder.setTitleColor(Color.blue);
        GridBagConstraints c = new GridBagConstraints();

        addComponentToAGridBagCell(topPanelWithComboBoxes, new JLabel("<html><b>Sector</b></html>"), c, 0, 0, GridBagConstraints.LINE_END);
        //1 for Sectr=2 : //Select second sector by default (KPP data is only on 2nd sector)
        JComboBoxSec = makeActivateComboBox(1, SecOrSLnum, "SelectSector");

        addComponentToAGridBagCell(topPanelWithComboBoxes, JComboBoxSec, c, 1, 0, GridBagConstraints.LINE_START);

        addComponentToAGridBagCell(topPanelWithComboBoxes, new JLabel("            "), c, 2, 0, GridBagConstraints.LINE_END);
        addComponentToAGridBagCell(topPanelWithComboBoxes, new JLabel("<html><b>Superlayer</b></html>"), c, 3, 0, GridBagConstraints.LINE_END);
        JComboBoxSL = makeActivateComboBox(0, SecOrSLnum, "SelectSuperlayer");//new JComboBox(SecOrSLnum);
        addComponentToAGridBagCell(topPanelWithComboBoxes, JComboBoxSL, c, 4, 0, GridBagConstraints.LINE_START);

        addComponentToAGridBagCell(topPanelWithComboBoxes, new JLabel("            "), c, 5, 0, GridBagConstraints.LINE_END);
        //topPanelWithComboBoxes.add(new JLabel("<html>CCDB-variation for par-initialization</html>", JLabel.RIGHT));
        addComponentToAGridBagCell(topPanelWithComboBoxes,
                new JLabel("<html><b>CCDB-variation for <br>par-initialization</b></html>"), c, 6, 0, GridBagConstraints.LINE_END);
        JComboBoxCcdbVars = makeActivateComboBox(1, ccdbVariations, "SelectCcdbVariation");//new JComboBox(ccdbVariations);
//        Dimension d = JComboBoxCcdbVars.getPreferredSize();
//        //JComboBoxCcdbVars.setPreferredSize(new Dimension(25, 35));
//        JComboBoxCcdbVars.setSize(70, d.height);
//        //JComboBoxCcdbVars.setPreferredSize(new Dimension(50, d.height));
//        //JComboBoxCcdbVars.setPopupWidth(d.width);
//        JComboBoxCcdbVars.setPrototypeDisplayValue(70);
        addComponentToAGridBagCell(topPanelWithComboBoxes, JComboBoxCcdbVars, c, 7, 0, GridBagConstraints.LINE_START);
    }

    public JPanel createAnEmptyPanel(int height) {
        JPanel newPanel = new JPanel();
        Dimension dim = new Dimension(width, 40);
        newPanel.setPreferredSize(dim);
        return newPanel;
    }

    public void createAndAddComponentsToMyGridLayoutPanel() {
        MyGridLayoutPanel = new JPanel();

        //MyGridLayoutPanel.setBorder(BorderFactory.createTitledBorder("Set Parameters"));//works
        TitledBorder borderTitle = BorderFactory.createTitledBorder("Set Parameters");
        borderTitle.setTitleJustification(TitledBorder.LEFT);//.CENTER);
        MyGridLayoutPanel.setBorder(borderTitle);

        TitledBorder titledBorder = (TitledBorder) MyGridLayoutPanel.getBorder();
        titledBorder.setTitleColor(Color.darkGray); //deep green);

        Dimension dim = new Dimension(width, 300);
        MyGridLayoutPanel.setPreferredSize(dim);//setSize(dim);
        MyGridLayoutPanel.setLayout(new GridLayout(nRows, nColumns));  // 4x3 Grid (rows * columns)

        //String col0Str[] = { "Row1", "Row2", "Row3", "Row4" };
        String parName[] = {"v0", "deltamn", "tmax", "distbeta", "delta_bfield_coeff.",
            "b1", "b2", "b3", "b4", "deltaT0"};
        String colTitles[] = {
            "<html><font color='black'> <b>Parameter</b></font></html>",
            "<html><font color='black'> <b>Lower Limit</b></font></html>",
            "<html><font color='black'> <b>Initial Value</b></font></html>",
            "<html><font color='black'> <b>Upper Limit</b></font></html>",
            "<html><font color='black'> <b>Step Size</b></font></html>",
            "<html><font color='black'> <b>Fix it?</b></font></html>"
        };

//
//kp: The GridLayout works such that the elements are added in the same order as
//    we write on paper or type on computer pages. We go from left to right on the
//    top line and then we go to the next line and do the same repeatedly. So, in 
//   the following example, elements 1, 2, 3, 4, 5 sit on the first row, then
//   elements (6,..,10) sit on the second row and so on.
//
        //First add the column titles on the first row of the grids
        for (int i = 0; i < colTitles.length; i++) {
            MyGridLayoutPanel.add(new JLabel(colTitles[i], JLabel.CENTER));//, JLabel.CENTER);
        }

        //Next add the columns of labels, fields and check-boxes.
        for (int i = 0; i < nFieldRows; i++) {
            //Defining components to add to the panel (container)
            //col0Labels[i] = new JLabel(col0Str[i],JLabel.RIGHT);
            col0Labels[i] = new JLabel(parName[i], JLabel.RIGHT);
            lowerLimFields[i] = makeActivateTextField(8, "", lowerLimFieldNames[i]);//new JTextField(8);
            initValueFields[i] = makeActivateTextField(8, "", initValueFieldNames[i]);//new JTextField(8);
            upperLimFields[i] = makeActivateTextField(8, "", upperLimFieldNames[i]);//new JTextField(8);
            stepSizeFields[i] = makeActivateTextField(8, "", stepSizeFieldNames[i]);//new JTextField(8);
            //makeActivateTextField(int columns, String iniText, String command)

            col6CheckBoxes[i] = makeActivateCheckBox("Fix me", col6CheckBoxNames[i]);//new JCheckBox("Fix me");
            //col7Labels[i] = new JLabel("Fix me");

            //Now add all the components to the panel
            MyGridLayoutPanel.add(col0Labels[i]);
            MyGridLayoutPanel.add(lowerLimFields[i]);
            MyGridLayoutPanel.add(initValueFields[i]);
            MyGridLayoutPanel.add(upperLimFields[i]);
            MyGridLayoutPanel.add(stepSizeFields[i]);
            MyGridLayoutPanel.add(col6CheckBoxes[i]);
            //MyGridLayoutPanel.add(col7Labels[i]);
        }
    }

    public void createAddComponentsToPanelWithSlider() {
        sliderPanel = new JPanel(new BorderLayout());
        Dimension dim = new Dimension(width, 40);
        sliderPanel.setPreferredSize(dim);
        sliderPanel.setLayout(new GridLayout(1, 1));  // (rows * columns)

        myRangeSliderNew();
        sliderPanel.add(rangeSliderPanel);
    }

    public void createAndComponentsToButtonsPanel() {
        buttonsPanel = new JPanel(new GridBagLayout());
        //panelForm.setSize(new Dimension(650, 400);); //Didn't have any effect
        buttonsPanel.setPreferredSize(new Dimension(width, 40));
        //buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
//        buttonsPanel.setBorder(BorderFactory.createTitledBorder("Set Parameters"));
//        TitledBorder titledBorder = (TitledBorder) buttonsPanel.getBorder();
//        titledBorder.setTitleColor(Color.blue);

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
        buttonsPanel.add(new JLabel("<html><font color='black'> <b>Uncertainty</b></font></html>"), c); //We create objects right here inside add(), because we don't need to do much to them
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;

        JComboBoxErr = makeActivateComboBox(2, errorTypes, "SelectErrorType");
        buttonsPanel.add(JComboBoxErr, c);

        addEmptyGridBagCell(buttonsPanel, c, 2, 0, "                         ");

        //buttonAngleSelection = new JButton("Select Angle Bins");
        buttonAngleSelection = makeActivateButton("Select Angle Bins", "SelectAngularBins");//makeActivateButton(Caption, Command);
        addComponentToAGridBagCell(buttonsPanel, buttonAngleSelection, c, 3, 0, GridBagConstraints.CENTER);

        addEmptyGridBagCell(buttonsPanel, c, 4, 0, "                       ");//This and the next are equivalent.
        //addComponentToAGridBagCell(buttonsPanel, new JLabel("                       "), c, 4, 0);

        buttonGoFitIt = makeActivateButton("Go Fit It", "GoFitIt");//makeActivateButton(Caption, Command);
        addComponentToAGridBagCell(buttonsPanel, buttonGoFitIt, c, 5, 0, GridBagConstraints.CENTER);
    }

    private void createAndAddComponentsToBottomPanelForButtons() {
        bottomPanelForButtons = new JPanel(new GridBagLayout());
        bottomPanelForButtons.setPreferredSize(new Dimension(width, 40)); //setSize(..) Didn't have any effect

        GridBagConstraints c = new GridBagConstraints();
        buttonSliceViewer = makeActivateButton("Slice Viewer", "SliceViewer");//(Caption, Command);
        addComponentToAGridBagCell(bottomPanelForButtons, buttonSliceViewer, c, 0, 0, GridBagConstraints.CENTER);

        buttonResiduals = makeActivateButton("Residuals", "Residuals");//makeActivateButton(Caption, Command);
        addComponentToAGridBagCell(bottomPanelForButtons, buttonResiduals, c, 1, 0, GridBagConstraints.CENTER);

        buttonTimes = makeActivateButton("Times", "Times");//makeActivateButton(Caption, Command);
        addComponentToAGridBagCell(bottomPanelForButtons, buttonTimes, c, 2, 0, GridBagConstraints.CENTER);

        buttonBfield = makeActivateButton("B-field", "B-field");//makeActivateButton(Caption, Command);
        addComponentToAGridBagCell(bottomPanelForButtons, buttonBfield, c, 3, 0, GridBagConstraints.CENTER);

        buttonLocalAngle = makeActivateButton("Local-angle", "Local-angle");//makeActivateButton(Caption, Command);
        addComponentToAGridBagCell(bottomPanelForButtons, buttonLocalAngle, c, 4, 0, GridBagConstraints.CENTER);

        addEmptyGridBagCell(bottomPanelForButtons, c, 5, 0, "                       ");//This and the next are equivalent

        buttonExit = makeActivateButton("Exit", "Exit");//makeActivateButton(Caption, Command);
        addComponentToAGridBagCell(bottomPanelForButtons, buttonExit, c, 6, 0, GridBagConstraints.CENTER);
    }

    public void addEmptyGridBagCell(JPanel panel, GridBagConstraints cc,
            int gridx, int gridy, String EmptySpace) {

        addComponentToAGridBagCell(panel, new JLabel(EmptySpace), cc, gridx, gridy, GridBagConstraints.CENTER);
    }

    public void addComponentToAGridBagCell(Container container, Component component, GridBagConstraints cc,
            int gridx, int gridy, int anchor) {
        cc.gridx = gridx;
        cc.gridy = gridy;
        //cc.anchor = GridBagConstraints.CENTER;    
        cc.anchor = anchor;
        container.add(component, cc);
    }

    public void createAndAddComponentsToPanelWithTextArea() {
        panelWithTextArea = new JPanel(new BorderLayout());
        Dimension dim = new Dimension(width, 200);
        panelWithTextArea.setPreferredSize(dim);//.setSize(dim);
        panelWithTextArea.add(new JLabel("<html><font color='black'> <b>Fit Results</b></font></html>"), BorderLayout.NORTH);
        jTextAreaForFitResults = new JTextArea();
        //HTML spaces: &nbsp;    &ensp;   &emsp;  //http://www.wikihow.com/Insert-Spaces-in-HTML
        String title = "<html><font color='darkGray'> "
                + "v0 &ensp; deltanm &ensp; tmax &ensp; distbeta &ensp; delta_bfield_coeff.  &ensp; "
                + "b1 &ensp; b2 &ensp; b3 &ensp; b4  &ensp;  deltaT0</font></html>";
        jTextAreaForFitResults.setBorder(BorderFactory.createTitledBorder(title));
        addJPopupMenuToJTextArea1();
        JScrollPane jScrollPane = new JScrollPane(jTextAreaForFitResults, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        /*
        //creating an anonymous inner class implementing ActionListener interface by providing an actionPerformed method. 
        jButtonExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
         */
        //centralPanel.add(jTextAreaForFitResults, BorderLayout.CENTER);
        panelWithTextArea.add(jScrollPane, BorderLayout.CENTER);
    }

    private JComboBox makeActivateComboBox(int selectedItemIndex, String[] options, String command) {
        JComboBox jComboBox = new JComboBox(options);
        jComboBox.setActionCommand(command);
        jComboBox.setSelectedIndex(selectedItemIndex); //Select second superlayer by default (KPP data is only on 2nd superlayer)
        //jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6" }));
        jComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (command.equals("SelectSector")) {
                    JComboBoxSecActionPerformed(evt);
                }
                if (command.equals("SelectSuperlayer")) {
                    JComboBoxSLActionPerformed(evt);
                }
                if (command.equals("SelectCcdbVariation")) {
                    JComboBoxCCDBVarActionPerformed(evt);
                }
                if (command.equals("SelectErrorType")) {
                    JComboBoxErrActionPerformed(evt);
                }
            }
        });
        return jComboBox;
    }

    private void JComboBoxSecActionPerformed(ActionEvent evt) {
        gSector = Integer.parseInt(JComboBoxSec.getSelectedItem().toString());
        putCCDBvaluesToResetArrays(gSector, ccdbVariation);
        assignParValuesToTextFields(gSector, gSuperlayer);
        if (debug == true) {
            System.out.println("Sector = " + gSector + " is selected.");
            /*
        for(int i=0; i<9; i++) {
            if(checkboxVal[i]==true) System.out.println("Parameter " + parName[i] + " has been fixed.");
        }
             */
        }
    }

    private void JComboBoxSLActionPerformed(ActionEvent evt) {
        gSuperlayer = Integer.parseInt(JComboBoxSL.getSelectedItem().toString());
        if (debug == true) {
            System.out.println("Superlayer = " + gSuperlayer + " is selected.");
        }
        putCCDBvaluesToResetArrays(gSector, ccdbVariation);
        assignParValuesToTextFields(gSector, gSuperlayer);
        printValuesOfSelectedAngularBins();
    }

    private void JComboBoxCCDBVarActionPerformed(ActionEvent evt) {
        ccdbVariation = JComboBoxCcdbVars.getSelectedItem().toString();
        if (debug == true) {
            System.out.println("CCDB variation = " + ccdbVariation + " is selected to initialize the fit parameters.");
        }
        putCCDBvaluesToResetArrays(gSector, ccdbVariation);
        assignParValuesToTextFields(gSector, gSuperlayer);
    }

    private void JComboBoxErrActionPerformed(ActionEvent evt) {
        xMeanErrorType = JComboBoxErr.getSelectedIndex();//JComboBoxCcdbVars, JComboBoxErr
        System.out.println("Selected: item " + (xMeanErrorType + 1) + " i.e., "
                + JComboBoxErr.getSelectedItem());
    }

    private JButton makeActivateButton(String caption, String command) {
        JButton b = new JButton(caption);
        b.setActionCommand(command);
        b.addActionListener(this);
        //getContentPane().add(b, constraints);
        return b;
    }

    private JTextField makeActivateTextField(int columns, String iniText, String command) {
        JTextField tf = new JTextField(columns);
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

        // ########################
        //Process the command string and identify which field was acted on. (clicked, typed ..)
        //https://docs.oracle.com/javase/tutorial/java/data/manipstrings.html (both works)
        fieldName = command.substring(0, len - 2); //(int beginIndex, int endIndex));
        //System.out.println("# = " + command.substring(len-2, len));
        //System.out.println("# = " + command.substring(len-2)); //Both works
        fieldIndex = Integer.parseInt(command.substring(len - 2, len));//Gives a primitive int
        //Alternatively, we can use Integer.valueOf() to get an Integer object, rather than a primitive int.

        //System.out.println(fieldName.equals("lowerLimField") ); //(fieldName == "lowerLimField") returned false.
        if (fieldName.equals("lowerLimField")) {
            fieldValue = lowerLimFields[fieldIndex].getText();
        } else if (fieldName.equals("initValueField")) {
            fieldValue = initValueFields[fieldIndex].getText();
        } else if (fieldName.equals("upperLimField")) {
            fieldValue = upperLimFields[fieldIndex].getText();
        } else if (fieldName.equals("stepSizeField")) {
            fieldValue = stepSizeFields[fieldIndex].getText();
        }

        if (debug == true) {
            System.out.println(String.format("Field name and index: %s %02d", fieldName, fieldIndex));
            System.out.println("The current field has the following text:  " + fieldValue);
        }

    }

    private JCheckBox makeActivateCheckBox(String label, String command) {
        JCheckBox cb = new JCheckBox(label);
        cb.setActionCommand(command);
        //cb.addActionListener(this);
        cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxActionPerformed(evt);
            }
        });
        return cb;
    }

    //col6CheckBoxesNames[i] = String.format("col6CheckBox%02d", i);
    private void checkBoxActionPerformed(ActionEvent e) {
        String command = e.getActionCommand();//command is like String.format("col6CheckBox%02d", i);
        int len = command.length(), icb = -1;

        //Process the command string and identify which field was acted on. (clicked, typed ..)
        //https://docs.oracle.com/javase/tutorial/java/data/manipstrings.html (both works)
        //cbName = command.substring(0, len - 2); //(int beginIndex, int endIndex));
        //System.out.println("# = " + command.substring(len-2)); //Both works
        icb = Integer.parseInt(command.substring(len - 2, len));//Gives a primitive int
        //Alternatively, we can use Integer.valueOf() to get an Integer object, rather than a primitive int.

        if (col6CheckBoxes[icb].isSelected()) {
            checkboxVal[icb] = true;
        } else {
            checkboxVal[icb] = false;
        }
        if (debug == true) {
            System.out.println("checkbox" + icb + " = " + checkboxVal[icb]);
        }
    }

    public void actionPerformed(ActionEvent e) {
        //buttonAngleSelection, buttonGoFitIt
        if ("SelectAngularBins" == e.getActionCommand()) {
            //buttonAngleSelection.setEnabled(false);//After one click, it is disabled.
            //(flipTask = new FlipTask()).execute();
            AngularBinSelectionActionPerformed(e);
            if (debug == true) {
                System.out.println("Button for AngleSelection clicked");
            }
        } else if ("GoFitIt" == e.getActionCommand()) {
            //buttonGoFitIt.setEnabled(true);
            buttonGoFitItActionPerformed(e);
//            flipTask.cancel(true);
//            flipTask = null;
            if (debug == true) {
                System.out.println("Button for GoFitIt clicked");
            }
        } else if ("SliceViewer" == e.getActionCommand()) {
            //fitter.SliceViewer(fitter);
            if (debug == true) {
                System.out.println("Button for Slice Viewer clicked");
            }
        } else if ("Residuals" == e.getActionCommand()) {
            //fitter.showResidualDistributions(this, gSector, gSuperlayer, xNormLow, xNormHigh);
            if (debug == true) {
                System.out.println("Button for Residuals clicked");
            }
        } else if ("Times" == e.getActionCommand()) {
            //fitter.showTimeDistributions(this, gSector, gSuperlayer, xNormLow, xNormHigh);
            if (debug == true) {
                System.out.println("Button for Times clicked");
            }
        } else if ("B-field" == e.getActionCommand()) {
            //fitter.showBFieldDistributions(this, gSector, gSuperlayer, xNormLow, xNormHigh);
            if (debug == true) {
                System.out.println("Button for B-field clicked");
            }
        } else if ("Local-angle" == e.getActionCommand()) {
            //fitter.showLocalAngleDistributions(this, gSector, gSuperlayer, xNormLow, xNormHigh);                                       
            if (debug == true) {
                System.out.println("Button for Local angle clicked");
            }
        } else if ("Exit" == e.getActionCommand()) {
            System.exit(0);
            if (debug == true) {
                System.out.println("Button for Exit clicked");
            }
        }
    }

    private void AngularBinSelectionActionPerformed(ActionEvent evt) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                System.out.println("kp: Hello from AngularBinSelectionActionPerformed(ActionEvent evt) and\n"
                        + "\t I am currently on " + Thread.currentThread().getName() + " thread.\n");

                //In this context, 'this' wont be referring to the object of FitControlUI class
                //    rather that of the anonymous Runnable class and so we'll get the following 
                //error if we put only 'this' as the first argument:
                //       incompatible types <anonymous Runnable> cannot be converted to FitControlUI
                //new FitControlBinSelectionUI(FitControlUI.this, fitter).setVisible(true);   
                binSelector = new FitControlBinSelectionUI(FitControlUI.this, fitter);
                binSelector.setVisible(true);
            }
        });
    }

    private void buttonGoFitItActionPerformed(ActionEvent evt) {
        putNumbersFromTextFieldsIntoResetArrays(gSuperlayer); //Just in case textField values are changed

        if (debug) {
            System.out.println("From buttonGoFitItActionPerformed(evt)");
            System.out.println("Lows  Inits  Highs  Steps");
            int iSL = gSuperlayer - 1;
            for (int i = 0; i < resetFitParsLow[iSL].length; i++) {
                System.out.println(resetFitParsLow[iSL][i] + "  " + resetFitPars[iSL][i] + "  "
                        + resetFitParsHigh[iSL][i] + "  " + resetFitParSteps[iSL][i]);
            }
            System.out.println("xNormLow/High: " + xNormLow + "/" + xNormHigh);
        }

        System.out.println("fitter.runFitterAndDrawPlots(..) clicked.");
//        fitter.runFitterAndDrawPlots(this, jTextAreaForFitResults, gSector, gSuperlayer,
//                xMeanErrorType, xNormLow, xNormHigh, checkboxVal, checkBoxFixAll,
//                resetFitParsLow, resetFitPars, resetFitParsHigh, resetFitParSteps, selectedAngleBins);
    }

    private void printValuesOfSelectedAngularBins() {
        if (debug == true) {
            System.out.println("Tmp line for debug ..");
        }
        //FitControlBinSelectionUI binSelector = new FitControlBinSelectionUI(this, fitter);
        if (!(binSelector == null)) {
            selectedAngleBins = binSelector.checkboxVals;
        }

        //Following is simply to print the indices of the bins that were selected
        int countSelectedBins = 0;
        System.out.print("The selected angular bins (indices) are = (");
        for (int i = 0; i < selectedAngleBins.length; i++) {
            //if(selectedAngleBins[i] == true) System.out.println((i+1) + "th bin has been selected");  
            //System.out.println("selectedAngleBins["+i+"] = " + selectedAngleBins[i]);
            if (selectedAngleBins[i] == true) {
                if (countSelectedBins == 0) {
                    System.out.print(i);
                } else {
                    System.out.print(", " + i);
                }
                countSelectedBins++;
            }
        }
        System.out.println(")");
    }

    public void openFileToWriteFitParameters() {
        boolean append_to_file = false;
        System.out.println("Fit results are printed on to an output file.");
//        FileOutputWriter file = null;
//        try {
//            file = new FileOutputWriter(outFileForFitPars, append_to_file);
//            file.Write("#Sec  SL  v0  deltanm  tMax  distbeta  delta_bfield_coefficient  b1  b2  b3  b4");
//            file.Close();
//        } catch (IOException ex) {
//            Logger.getLogger(TimeToDistanceFitter.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
        slider.setUpperValue((int) xSliderMax * 80 / 100);

        xNormCurrentSliderMin = slider.getValue() * (xMax - xMin) / (double) (xSliderMax - xSliderMin) + xMin;
        xNormCurrentSliderMax = slider.getUpperValue() * (xMax - xMin) / (double) (xSliderMax - xSliderMin) + xMin;
        JLabel rangeSliderValue1 = new JLabel("" + String.format("%4.2f", xNormCurrentSliderMin));
        JLabel rangeSliderValue2 = new JLabel("" + String.format("%4.2f", xNormCurrentSliderMax));
        //fitFunction.setRange(xNormCurrentSliderMin, xNormCurrentSliderMax);

        rangeSelector.add(xLabel);
        rangeSelector.add(rangeSliderValue1);
        rangeSelector.add(slider);
        rangeSelector.add(rangeSliderValue2);
        rangeSliderPanel.add(rangeSelector);

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                RangeSlider slider = (RangeSlider) e.getSource();
                xNormCurrentSliderMin = slider.getValue() * (xMax - xMin) / (double) (xSliderMax - xSliderMin) + xMin;
                xNormCurrentSliderMax = slider.getUpperValue() * (xMax - xMin) / (double) (xSliderMax - xSliderMin) + xMin;
                rangeSliderValue1.setText(String.valueOf("" + String.format("%4.2f", xNormCurrentSliderMin)));
                rangeSliderValue2.setText(String.valueOf("" + String.format("%4.2f", xNormCurrentSliderMax)));
                //System.out.println(String.format("xNormCurrentSliderMin/Max: %4.2f / %4.2f",xNormCurrentSliderMin, xNormCurrentSliderMax));
                // fitFunction.setRange(xNormCurrentSliderMin, xNormCurrentSliderMax);
            }
        });
    }

    private void getParametersFromCCDB() {
//        //Instead of reading the two tables again and again whenever we select the item from
//        //   the corresponding jComboBox4, it's better to read both once at the beginning,
//        //   keep them stored in two different array variables and use those arrays later.
//        ReadT2DparsFromCCDB rdTable1 = new ReadT2DparsFromCCDB("dc_test1");
//        parsFromCCDB_dc_test1 = rdTable1.parsFromCCDB;
//
//        ReadT2DparsFromCCDB rdTable = new ReadT2DparsFromCCDB("default");
//        parsFromCCDB_default = rdTable.parsFromCCDB;
//
//        ReadT2DparsFromCCDB rdTable2 = new ReadT2DparsFromCCDB("dc_test2");
//        parsFromCCDB_dc_test2 = rdTable2.parsFromCCDB;
    }

    private void putCCDBvaluesToResetArrays(int sector, String ccdbVariation) {
        for (int i = 0; i < nSL; i++) {
            for (int j = 0; j < nFitPars; j++) {
                //Get the init values from CCDB
                if (ccdbVariation == "default") {
                    resetFitPars[i][j] = parsFromCCDB_default[sector - 1][i][j];
                } else if (ccdbVariation == "dc_test1") {
                    resetFitPars[i][j] = parsFromCCDB_dc_test1[sector - 1][i][j];
                } else if (ccdbVariation == "dc_test2") {
                    resetFitPars[i][j] = parsFromCCDB_dc_test2[sector - 1][i][j];
                }

                //Calculate and assign lower and upper limits based on sign and values of the init-values
                if (resetFitPars[i][j] < 0.0) {
                    resetFitParsLow[i][j] = 2.0 * resetFitPars[i][j];
                    resetFitParsHigh[i][j] = 0.2 * resetFitPars[i][j];
                } else if (resetFitPars[i][j] > 0.0) {
                    resetFitParsLow[i][j] = 0.2 * resetFitPars[i][j];
                    resetFitParsHigh[i][j] = 2.0 * resetFitPars[i][j];
                } else if (resetFitPars[i][j] == 0.0) {
                    resetFitParsLow[i][j] = -1.0;
                    resetFitParsHigh[i][j] = 1.0;
                }
            }

            //6/5/17: as of now, deltaT0 is not in CCDB table, so I am assigning by hard-coding
            resetFitPars[i][9] = 0.0;
            resetFitParsLow[i][9] = -30.0;
            resetFitParsHigh[i][9] = 30.0;
        }
    }

    private void assignParValuesToTextFields(int sector, int superlayer) {
        int iSL = superlayer - 1;
        for (int i = 0; i < nFieldRows; i++) {
            //Setting the first column of text-fields to 0.4 times the previous values of fit-pars
            lowerLimFields[i].setText(String.format("%5.4f", resetFitParsLow[iSL][i]));
            //Setting the second column of text-fields to previous values of fit-pars
            //jTextField10.setText(String.valueOf(resetFitPars[iSL][0]));//works but string format is ugly
            initValueFields[i].setText(String.format("%5.4f", resetFitPars[iSL][i]));

            //Setting the third column of text-fields to 2.0 times the previous values of fit-pars
            upperLimFields[i].setText(String.format("%5.4f", resetFitParsHigh[iSL][i]));
            //Now setting the fourth column (for step sizes) in terms of parSteps array (see Constants.java)
            //double parSteps[] = {0.00001, 0.001, 0.01, 0.0001, 0.001, 0.001, 0.001, 0.001, 0.001, 0.001};
            stepSizeFields[i].setText(String.format("%6.5f", resetFitParSteps[iSL][i]));
        }

//        jTextField28.setText(String.format("%5.4f", xNormLow));
//        jTextField29.setText(String.format("%5.4f", xNormHigh));
    }

    private void putNumbersFromTextFieldsIntoResetArrays(int superlayer) {
        int iSL = superlayer - 1;
        for (int i = 0; i < nFieldRows; i++) {
            resetFitParsLow[iSL][i] = Float.parseFloat(lowerLimFields[i].getText());
            resetFitPars[iSL][i] = Float.parseFloat(initValueFields[i].getText());
            resetFitParsHigh[iSL][i] = Float.parseFloat(upperLimFields[i].getText());
            resetFitParSteps[iSL][i] = Float.parseFloat(stepSizeFields[i].getText());
        }

        xNormLow = xNormCurrentSliderMin;//Float.parseFloat(jTextField28.getText());
        xNormHigh = xNormCurrentSliderMax; //Float.parseFloat(jTextField29.getText());
        if (xNormLow < 0.0) {
            xNormLow = 0.0;
        }
        if (xNormHigh > 1.0) {
            xNormHigh = 1.0;
        }
    }

    private void putStepSizeFromConstantsToResetArrays(int sector) {
        for (int i = 0; i < nSL; i++) {
            for (int j = 0; j < nFitPars; j++) {
                resetFitParSteps[i][j] = parSteps[j];
            }
        }
    }

    private void addJPopupMenuToJTextArea1() {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem item = new JMenuItem(new DefaultEditorKit.CutAction());
        item.setText("Cut");
        popup.add(item);
        item = new JMenuItem(new DefaultEditorKit.CopyAction());
        item.setText("Copy");
        popup.add(item);
        item = new JMenuItem(new DefaultEditorKit.PasteAction());
        item.setText("Paste");
        popup.add(item);
        jTextAreaForFitResults.setComponentPopupMenu(popup);
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
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
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

//    private static void initSynthLookAndFeel() {
//        //https://docs.oracle.com/javase/tutorial/uiswing/examples/lookandfeel/index.html
//        //private static String synthFile = "buttonSkin.xml";
//        String synthFile = "buttonSkin.xml";
//
//        // String lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
//        SynthLookAndFeel lookAndFeel = new SynthLookAndFeel();
//
//        try {
//            lookAndFeel.load(SynthApplication.class.getResourceAsStream(synthFile),
//                    SynthApplication.class);
//            UIManager.setLookAndFeel(lookAndFeel);
//        } catch (Exception e) {
//            System.err.println("Couldn't get specified look and feel ("
//                    + lookAndFeel
//                    + "), for some reason.");
//            System.err.println("Using the default look and feel.");
//            e.printStackTrace();
//        }
//
//    }
    //Entry point of program
    public static void main(String[] args) {
        OrderOfAction OA = null;
        boolean isLinearFit = true;
        ArrayList<String> fileArray = null;

        DC_TimeToDistanceFitter fitter = new DC_TimeToDistanceFitter(OA, fileArray, isLinearFit);
        //Create a frame and show it through SwingUtilities
        SwingUtilities.invokeLater(() -> {
            //new FitControlUIold(fitter).setVisible(true);
            new FitControlUI(fitter).setVisible(true);
        });
    }
}
