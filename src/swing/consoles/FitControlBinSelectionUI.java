/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.consoles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import static swing.consoles.Constants.angularBinsSelectedByDefault;
import static swing.consoles.Constants.debug;
import static swing.consoles.Constants.nThBinsVz;
import static swing.consoles.Constants.thEdgeVzH;
import static swing.consoles.Constants.thEdgeVzL;

/**
 *
 * @author kpadhikari
 */
public class FitControlBinSelectionUI extends JFrame
        implements ActionListener {

    private int width = 700, height = 200;
    JPanel topPanelForAngleBinSelection, panelForBFieldBinSelection, panelForOkButton;
    private JCheckBox[] checkBoxThBin = new JCheckBox[nThBinsVz];
    public boolean[] checkboxVals = new boolean[nThBinsVz];//{false, false, false, false, false, false, false, false, false, false};
    public boolean[] checkboxValsClicked = new boolean[nThBinsVz];
    public boolean atLeastOneAngleBinClicked = false;
    DC_TimeToDistanceFitter fitter;
    //FitControlUIold fitControl;
    FitControlUI fitControl;

    //public FitControlBinSelectionUI(FitControlUIold fitControl, TimeToDistanceFitter fitter) {
    public FitControlBinSelectionUI(FitControlUI fitControl, DC_TimeToDistanceFitter fitter) {
        initLookAndFeel();
        atLeastOneAngleBinClicked = false;
        initCheckBoxValues();
        createButDontShowGUI();
        this.fitter = fitter;
        this.fitControl = fitControl;

        //createAndShowGUI();   //It can be called from EDT (Event Dispatch Thread - see below)
        //   when above line was enabled, nad 'new' line was enabled in the EDT creating method
        //   createAndDisplayTheForm(), two identical GUIs showed up
    }

    public void createButDontShowGUI() {
        //Following lines invokes the methods of JFrame class that we extend here.
        setTitle("Bin Selector");
        //setSize(width, height);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = getContentPane();

        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        createAndAddComponentsToTopPanelForAngleBinSelection();
        pane.add(topPanelForAngleBinSelection);

        createAndAddComponentsToPanelForBFieldBinSelection();
        pane.add(panelForBFieldBinSelection);

        createAndAddComponentsToPanelForOkButton();
        pane.add(panelForOkButton);

        //        f.pack(); //Needed if we have more than one elements (panes) added to the frame        
        pack(); //Needed if we have more than one elements (panes) added to the frame

        //Adding listener to act on CloseWindowAction() when window is closed.
        //    Same will happen if 'Ok' button is pressed
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                CloseWindowAction();
            }
        });

        //setVisible(true); //        f.setVisible(true);
    }

    public void createAndShowGUI() {
        createButDontShowGUI();
        setVisible(true); //        f.setVisible(true);
    }

    private void createAndAddComponentsToTopPanelForAngleBinSelection() {       
        topPanelForAngleBinSelection = new JPanel(new GridBagLayout());
        setBorderProperties(topPanelForAngleBinSelection, "Select Angle Bins");
        topPanelForAngleBinSelection.setPreferredSize(new Dimension(width, 100));
        GridBagConstraints c = new GridBagConstraints();

        int anchor = GridBagConstraints.LINE_START;//.CENTER;//.LAST_LINE_END;       
        
        int cbNum = checkBoxThBin.length;//== nThBinsVz    
        int cbNumHalf = cbNum/2, cbNumRem = cbNum%2;
        System.out.println("cbNum/2 = " + cbNum/2 + " cbNum%2 = " + cbNum%2 + "  8%2 = " + 8%2);

        //======= Drawing checkboxes in the first row
        String thBinRange = "", command = "";
        for (int i = 0; i < cbNumHalf - 1 ; i++) {  //      
            thBinRange = String.format("(%2.0f,%2.0f)", thEdgeVzL[i], thEdgeVzH[i]);
            command = String.format("thBinCheckBox%02d", i);
            checkBoxThBin[i] = makeActivateCheckBox(thBinRange, command);//setText("( -55,-45)");
            //topPanelForAngleBinSelection.add(checkBoxThBin[i]);
            addComponentToAGridBagCell(topPanelForAngleBinSelection, checkBoxThBin[i], c, i, 0, anchor);
        }
        
        //======= Drawing checkboxes in the middle/second row
        int NumOfCheckBoxInMiddleRow = 2 + cbNumRem;//Draw 2 or 3 depending on whether the total is even or odd
        int iMidRowIni = cbNumHalf - 1, iMidRowFin = cbNumHalf - 1 + NumOfCheckBoxInMiddleRow;
        int cbPosition = (cbNumHalf - NumOfCheckBoxInMiddleRow)/2;
        
        for (int i = iMidRowIni; i < iMidRowFin; i++) {  //      
            thBinRange = String.format("(%2.0f,%2.0f)", thEdgeVzL[i], thEdgeVzH[i]);
            command = String.format("thBinCheckBox%02d", i);
            checkBoxThBin[i] = makeActivateCheckBox(thBinRange, command);//setText("( -55,-45)");
            //topPanelForAngleBinSelection.add(checkBoxThBin[i]);
            addComponentToAGridBagCell(topPanelForAngleBinSelection, checkBoxThBin[i], c, cbPosition, 1, anchor);
            cbPosition++;
        }

        //======= Drawing checkboxes in the last/third row
        iMidRowIni = iMidRowFin; iMidRowFin = iMidRowIni + cbNumHalf - 1;
        for (int i = iMidRowIni; i < iMidRowFin; i++) {  //      
            thBinRange = String.format("(%2.0f,%2.0f)", thEdgeVzL[i], thEdgeVzH[i]);
            command = String.format("thBinCheckBox%02d", i);
            checkBoxThBin[i] = makeActivateCheckBox(thBinRange, command);//setText("( -55,-45)");
            //topPanelForAngleBinSelection.add(checkBoxThBin[i]);
            addComponentToAGridBagCell(topPanelForAngleBinSelection, checkBoxThBin[i], c, i - iMidRowIni, 2, anchor);
        }        
    }    
    
    private void createAndAddComponentsToTopPanelForAngleBinSelectionOld() { //In GridLayout
        int nRows = 3, nColumns = 5; //nColumns doesn't matter once nRows is known
        topPanelForAngleBinSelection = new JPanel(new GridLayout(nRows, nColumns));
        setBorderProperties(topPanelForAngleBinSelection, "Select Angle Bins");
        topPanelForAngleBinSelection.setPreferredSize(new Dimension(width, 100));

        String thBinRange = "", command = "";
        for (int i = 0; i < checkBoxThBin.length; i++) { //nThBinsVz           
            thBinRange = String.format("(%2.0f,%2.0f)", thEdgeVzL[i], thEdgeVzH[i]);
            command = String.format("thBinCheckBox%02d", i);
            checkBoxThBin[i] = makeActivateCheckBox(thBinRange, command);//setText("( -55,-45)");
            topPanelForAngleBinSelection.add(checkBoxThBin[i]);
        }
    }

    private void createAndAddComponentsToPanelForBFieldBinSelection() {
        panelForBFieldBinSelection = new JPanel(new GridBagLayout());
        setBorderProperties(panelForBFieldBinSelection, "Select B-Field Bins");
        panelForBFieldBinSelection.setPreferredSize(new Dimension(width, 100));
    }

    private void createAndAddComponentsToPanelForOkButton() {
        panelForOkButton = new JPanel(new BorderLayout()); //new FlowLayout());
        //setBorderProperties(panelForOkButton, "Select B-Field Bins");
        panelForOkButton.setPreferredSize(new Dimension(width, 40));

        JButton okButton = new JButton("OK");
        panelForOkButton.add(okButton);//, BorderLayout.LINE_END);
        //cb.addActionListener(this);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                OkButtonActionPerformed(evt);
            }
        });
    }

    private void setBorderProperties(JPanel pane, String title) {
        //MyGridLayoutPanel.setBorder(BorderFactory.createTitledBorder("Set Parameters"));//works
        TitledBorder borderTitle = BorderFactory.createTitledBorder(title);
        borderTitle.setTitleJustification(TitledBorder.LEFT);//.CENTER);
        pane.setBorder(borderTitle);

        TitledBorder titledBorder = (TitledBorder) pane.getBorder();
        titledBorder.setTitleColor(Color.darkGray); //deep green);

    }

    public void initCheckBoxValues() {
        for (int i = 0; i < checkboxVals.length; i++) {
            checkboxVals[i] = angularBinsSelectedByDefault[i];//false;
            checkboxValsClicked[i] = false;
        }
    }

    private JCheckBox makeActivateCheckBox(String label, String command) {
        JCheckBox cb = new JCheckBox(label);
        cb.setActionCommand(command);
        //cb.addActionListener(this);
        cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionPerformedForAngleBinCheckBox(evt);
            }
        });
        return cb;
    }
    
    public void addComponentToAGridBagCell(Container container, Component component, GridBagConstraints cc,
            int gridx, int gridy, int anchor) {
        cc.gridx = gridx;
        cc.gridy = gridy;
        //cc.anchor = GridBagConstraints.CENTER;    
        cc.anchor = anchor;
        container.add(component, cc);
    }
    
    //col6CheckBoxesNames[i] = String.format("col6CheckBox%02d", i);
    private void actionPerformedForAngleBinCheckBox(ActionEvent e) {
        atLeastOneAngleBinClicked = true;
        String command = e.getActionCommand();//command is like String.format("col6CheckBox%02d", i);
        int len = command.length(), icb = -1;

        //Process the command string and identify which field was acted on. (clicked, typed ..)
        //https://docs.oracle.com/javase/tutorial/java/data/manipstrings.html (both works)
        //cbName = command.substring(0, len - 2); //(int beginIndex, int endIndex));
        //System.out.println("# = " + command.substring(len-2)); //Both works
        icb = Integer.parseInt(command.substring(len - 2, len));//Gives a primitive int
        //Alternatively, we can use Integer.valueOf() to get an Integer object, rather than a primitive int.

        if (checkBoxThBin[icb].isSelected()) {
            checkboxValsClicked[icb] = true;
        } else {
            checkboxValsClicked[icb] = false;
        }
        if (debug == true) {
            System.out.println("checkbox" + icb + " = " + checkboxValsClicked[icb]);
        }
    }
//    
// kp: https://stackoverflow.com/questions/19433358/difference-between-dispose-and-exit-on-close-in-java
//     
//EXIT_ON_CLOSE will terminate the program.
//
//DISPOSE_ON_CLOSE will call dispose() on the frame, which will make it disappear and remove the resources it is using. 
//    You cannot bring it back, unlike hiding it.
//
//See aslo JFrame.dispose() vs System.exit()
//

    private void OkButtonActionPerformed(java.awt.event.ActionEvent evt) {
        //This is OK button
        //The goal here is to pass checkboxVals array to FitControlUI, so that it can be passed to the fitter.
        //FitControlUI fitControl = new FitControlUI();
        CloseWindowAction();
    }

    public void CloseWindowAction() {
        //First pass the checkboxVals array to FitControlUI, and then close then close the selector window.
        if(atLeastOneAngleBinClicked) 
            checkboxVals = checkboxValsClicked;
        fitControl.selectedAngleBins = checkboxVals;
        if (debug == true) {
            for (int i = 0; i < checkboxVals.length; i++) {
                System.out.println("fitControl.selectedAngleBins[" + i + "] = " + fitControl.selectedAngleBins[i]);
            }
        }
        //System.exit(0);   //This will kill the whole program
        this.dispose();   //This will only exit this window (and will dispose the used resources)
        //this.hide();
        //JFrame.dispose(); causes the JFrame window to be destroyed and cleaned up by the operating system. 
    }

    //@Override  
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public void createAndDisplayTheForm() {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FitControlBinSelectionUI(fitControl, fitter).setVisible(true);
                //createAndShowGUI();
            }
        });
    }

    //Entry point of program
    public static void main(String[] args) {
        //Create a frame and show it through SwingUtilities
        //SwingUtilities.invokeLater(() -> { new FitControlBinSelectionUI_FS(); });
        OrderOfAction OA = null;
        boolean isLinearFit = true;
        ArrayList<String> fileArray = null;

        DC_TimeToDistanceFitter fitter = new DC_TimeToDistanceFitter(OA, fileArray, isLinearFit);
        //FitControlUIold fitControlTmp = new FitControlUIold(fitter);
        FitControlUI fitControlTmp = new FitControlUI(fitter);
        FitControlBinSelectionUI binSelector = new FitControlBinSelectionUI(fitControlTmp, fitter);
        binSelector.createAndDisplayTheForm();
    }

}
