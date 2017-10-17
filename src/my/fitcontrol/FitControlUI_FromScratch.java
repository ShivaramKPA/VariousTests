/*
 * here I have a main panel with Box layout to put several subpanels of equal width
 *   arranged (layed out) in a vertical stack
*  Then a (sub)panel (second one at the moment - 8/11/17) that uses GridLayout (4 rows * 7 columns) 
*    which uses 1 column for rows, three columns for buttons (like a calculator), 2 for
*    I observed that, once the # of rwos is defined, the # of columns didn't really matter 
 */
package my.fitcontrol;

/**
 *
 * @author kpadhikari &
 * http://www.mathcs.emory.edu/~cheung/Courses/377/Syllabus/8-JDBC/GUI/layout.html
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.synth.SynthLookAndFeel;
//import varioustests.lookandfeel.SynthApplication;

public class FitControlUI_FromScratch {

    private final int nRows = 11;
    private final int nColumns = 3;//7; //This # didn't really matter (had here but used 7 columns of elements)
    private JLabel[] col0Labels = new JLabel[nRows]; //Col1 
    private JTextField[] lowerLimFields = new JTextField[nRows];  //Col2
    private JTextField[] initValueFields = new JTextField[nRows]; //Col3
    private JTextField[] upperLimFields = new JTextField[nRows];  //Col4
    private JTextField[] stepSizeFields = new JTextField[nRows];  //Col5  
    private JCheckBox[] col6CheckBoxes = new JCheckBox[nRows];    //Col6

    private JPanel MyGridLayoutPanel;
    private JPanel topPanelWithComboBoxes, sliderPanel, buttonsPanel, panelWithTextArea;

    String[] SecOrSLnum = {"1", "2", "3", "4", "5", "6"};
    String[] errorTypes = {"RMS in x-slice", "RMS/sqrt(N)", "1.0"};
    String[] ccdbVariations = {"default", "dc_test1", "dc_test2"};
    JComboBox JComboBoxSec, JComboBoxSL, JComboBoxCcdbVars, JComboBoxErr;// = new JComboBox(SecOrSLnum);
    JTextArea jTextArea1; //to show Fit Results
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
    double currentRangeMin = 0.0;
    double currentRangeMax = 100.0;

    JButton buttonAngleSelection, buttonGoFitIt;

    public FitControlUI_FromScratch() {
        initLookAndFeel();
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        JFrame f = new JFrame("Fit Control");
        f.setSize(width, height);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //We must get the ContentPane of the JFrame to add anything. 
        Container pane = f.getContentPane();
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

        f.pack(); //Needed if we have more than one elements (panes) added to the frame
        f.setVisible(true);
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
        JComboBoxSec = new JComboBox(SecOrSLnum);
        JComboBoxSec.setSelectedIndex(1); //Select second superlayer by default (KPP data is only on 2nd superlayer)
        addComponentToAGridBagCell(topPanelWithComboBoxes, JComboBoxSec, c, 1, 0, GridBagConstraints.LINE_START); 

        addComponentToAGridBagCell(topPanelWithComboBoxes, new JLabel("            "), c, 2, 0, GridBagConstraints.LINE_END);         
        addComponentToAGridBagCell(topPanelWithComboBoxes, new JLabel("<html><b>Superlayer</b></html>"), c, 3, 0, GridBagConstraints.LINE_END);         
        JComboBoxSL = new JComboBox(SecOrSLnum);
        JComboBoxSL.setSelectedIndex(0); //Select second superlayer by default (KPP data is only on 2nd superlayer)
        addComponentToAGridBagCell(topPanelWithComboBoxes, JComboBoxSL, c, 4, 0, GridBagConstraints.LINE_START);

        addComponentToAGridBagCell(topPanelWithComboBoxes, new JLabel("            "), c, 5, 0, GridBagConstraints.LINE_END); 
        //topPanelWithComboBoxes.add(new JLabel("<html>CCDB-variation for par-initialization</html>", JLabel.RIGHT));
        addComponentToAGridBagCell(topPanelWithComboBoxes, 
                new JLabel("<html><b>CCDB-variation for <br>par-initialization</b></html>"), c, 6, 0, GridBagConstraints.LINE_END); 
        JComboBoxCcdbVars = new JComboBox(ccdbVariations);
//        Dimension d = JComboBoxCcdbVars.getPreferredSize();
//        //JComboBoxCcdbVars.setPreferredSize(new Dimension(25, 35));
//        JComboBoxCcdbVars.setSize(70, d.height);
//        //JComboBoxCcdbVars.setPreferredSize(new Dimension(50, d.height));
//        //JComboBoxCcdbVars.setPopupWidth(d.width);
//        JComboBoxCcdbVars.setPrototypeDisplayValue(70);

        JComboBoxCcdbVars.setSelectedIndex(0); //Select second superlayer by default (KPP data is only on 2nd superlayer)
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
            MyGridLayoutPanel.add(new JLabel(colTitles[i]), JLabel.CENTER);
        }

        //Next add the columns of labels, fields and check-boxes.
        for (int i = 0; i < nRows - 1; i++) {
            //Defining components to add to the panel (container)
            //col0Labels[i] = new JLabel(col0Str[i],JLabel.RIGHT);
            col0Labels[i] = new JLabel(parName[i], JLabel.RIGHT);
            lowerLimFields[i] = new JTextField(8);
            initValueFields[i] = new JTextField(8);
            upperLimFields[i] = new JTextField(8);
            stepSizeFields[i] = new JTextField(8);
            col6CheckBoxes[i] = new JCheckBox("Fix me");
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

        JComboBoxErr = new JComboBox(errorTypes);
        JComboBoxErr.setSelectedIndex(2);
        buttonsPanel.add(JComboBoxErr, c);

        addEmptyGridBagCell(buttonsPanel, c, 2, 0, "                         ");

        buttonAngleSelection = new JButton("Select Angle Bins");
        addComponentToAGridBagCell(buttonsPanel, buttonAngleSelection, c, 3, 0, GridBagConstraints.CENTER);

        addEmptyGridBagCell(buttonsPanel, c, 4, 0, "                       ");//This and the next are equivalent.
        //addComponentToAGridBagCell(buttonsPanel, new JLabel("                       "), c, 4, 0);

        buttonGoFitIt = new JButton("Go Fit It");
        addComponentToAGridBagCell(buttonsPanel, buttonGoFitIt, c, 5, 0, GridBagConstraints.CENTER);
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
        jTextArea1 = new JTextArea();
        //HTML spaces: &nbsp;    &ensp;   &emsp;  //http://www.wikihow.com/Insert-Spaces-in-HTML
        String title = "<html><font color='darkGray'> "
                + "v0 &ensp; deltanm &ensp; tmax &ensp; distbeta &ensp; delta_bfield_coeff.  &ensp; "
                + "b1 &ensp; b2 &ensp; b3 &ensp; b4  &ensp;  deltaT0</font></html>";
        jTextArea1.setBorder(BorderFactory.createTitledBorder(title));
        JScrollPane jScrollPane = new JScrollPane(jTextArea1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
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
        panelWithTextArea.add(jScrollPane, BorderLayout.CENTER);
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
        //Create a frame and show it through SwingUtilities
        SwingUtilities.invokeLater(() -> {
            new FitControlUI_FromScratch();
        });
    }
}
