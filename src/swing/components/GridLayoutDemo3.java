/*
 * here I have a main panel with Box layout to put several subpanels of equal width
 *   arranged (layed out) in a vertical stack
*  Then a (sub)panel (second one at the moment - 8/11/17) that uses GridLayout (4 rows * 7 columns) 
*    which uses 1 column for rows, three columns for buttons (like a calculator), 2 for
*    I observed that, once the # of rwos is defined, the # of columns didn't really matter 
 */
package swing.components;

/**
 *
 * @author kpadhikari &
 * http://www.mathcs.emory.edu/~cheung/Courses/377/Syllabus/8-JDBC/GUI/layout.html
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.synth.SynthLookAndFeel;
//import varioustests.lookandfeel.SynthApplication;

public class GridLayoutDemo3 {
   
   private final int nRows = 4;
   private final int nColumns = 5;//7; //This # didn't really matter (had here but used 7 columns of elements)
   private JTextField[] col4Fields = new JTextField[nRows];
   private JTextField[] col5Fields = new JTextField[nRows];
   private JPanel MyGridLayoutPanel;
   JTextArea jTextArea1;
   private JPanel topPanelWithComboBoxes, sliderPanel, panelWithTextArea;
    
    public GridLayoutDemo3() {
        initLookAndFeel();
        createAndShowGUI();
    }
    
    public void createAndShowGUI() {
        JFrame f = new JFrame("GridLayout");
        int width = 600, height = 600;
        f.setSize(width, height);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
        //We must get the ContentPane of the JFrame to add anything. 
        Container pane = f.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS)); 
        
        createAndAddComponentsToTopPanelWithComboBoxes();
        pane.add(topPanelWithComboBoxes);
        
        createAndAddComponentsToMyGridLayoutPanel();  
        pane.add(MyGridLayoutPanel);//, "Center"); // Paste MyGridLayoutPanel into JFrame   

        createAndAddComponentsToPanelWithSlider();
        pane.add(sliderPanel);
        
        createAndAddComponentsToPanelWithTextArea();
        pane.add(panelWithTextArea);
        

        f.pack(); //Needed if we have more than one elements (panes) added to the frame
        f.setVisible(true);
    }

    public void createAndAddComponentsToTopPanelWithComboBoxes() {
        topPanelWithComboBoxes = new JPanel();
        Dimension dim = new Dimension(600, 100);
        topPanelWithComboBoxes.setPreferredSize(dim);
    }   
    
    public void createAndAddComponentsToMyGridLayoutPanel() {
        MyGridLayoutPanel = new JPanel();
        
        //MyGridLayoutPanel.setBorder(BorderFactory.createTitledBorder("Set Parameters"));//works
        TitledBorder borderTitle = BorderFactory.createTitledBorder("Set Parameters");
        borderTitle.setTitleJustification(TitledBorder.CENTER);
        MyGridLayoutPanel.setBorder(borderTitle);
        
        TitledBorder titledBorder = (TitledBorder) MyGridLayoutPanel.getBorder();
        titledBorder.setTitleColor(Color.darkGray); //deep green);
        
        
        
        Dimension dim = new Dimension(600, 200);
        MyGridLayoutPanel.setPreferredSize(dim);//setSize(dim);
        MyGridLayoutPanel.setLayout(new GridLayout(nRows, nColumns));  // 4x3 Grid (rows * columns)

        String col0Str[] = { "Row1", "Row2", "Row3", "Row4" };
        JButton[] col1Buttons = new JButton[nRows];
        JButton[] col2Buttons = new JButton[nRows];
        JButton[] col3Buttons = new JButton[nRows];
        String col1Str[] = { "7", "4", "1", "0" };
        String col2Str[] = { "8", "5", "2", "." };
        String col3Str[] = { "9", "6", "3", "CE" };
        JCheckBox[] col6CheckBoxes = new JCheckBox[nRows];
        JLabel[] col0Labels = new JLabel[nRows];
        

//
//kp: The GridLayout works such that the elements are added in the same order as
//    we write on paper or type on computer pages. We go from left to right on the
//    top line and then we go to the next line and do the same repeatedly. So, in 
//   the following example, elements 1, 2, 3, 4, 5 sit on the first row, then
//   elements (6,..,10) sit on the second row and so on.
//
        for (int i = 0; i < nRows; i++) {
            //Defining components to add to the panel (container)
            col0Labels[i] = new JLabel(col0Str[i],JLabel.RIGHT);
            col1Buttons[i] = new JButton(col1Str[i]);
            col2Buttons[i] = new JButton(col2Str[i]);
            col3Buttons[i] = new JButton(col3Str[i]);
            col4Fields[i] = new JTextField(8);
            col5Fields[i] = new JTextField(8);
            col6CheckBoxes[i] = new JCheckBox("Fix me");
            //col7Labels[i] = new JLabel("Fix me");
            
            //Now add all the components to the panel
            MyGridLayoutPanel.add(col0Labels[i]);
            MyGridLayoutPanel.add(col1Buttons[i]);
            MyGridLayoutPanel.add(col2Buttons[i]);
            MyGridLayoutPanel.add(col3Buttons[i]);
            MyGridLayoutPanel.add(col4Fields[i]);
            MyGridLayoutPanel.add(col5Fields[i]);
            MyGridLayoutPanel.add(col6CheckBoxes[i]);
            //MyGridLayoutPanel.add(col7Labels[i]);
        }
    }
    
    public void createAndAddComponentsToPanelWithSlider() {
        sliderPanel = new JPanel(new BorderLayout());
        Dimension dim = new Dimension(600, 100);
        sliderPanel.setPreferredSize(dim);        
    }
    
    public void createAndAddComponentsToPanelWithTextArea() {
        panelWithTextArea = new JPanel(new BorderLayout());
        Dimension dim = new Dimension(600, 300);
        panelWithTextArea.setPreferredSize(dim);//.setSize(dim);
        panelWithTextArea.add(new JLabel("<html><font color='black'> <b>Fit Results</b></font></html>"),BorderLayout.NORTH);
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
        panelWithTextArea.add(jScrollPane, BorderLayout.CENTER);
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
            new GridLayoutDemo3();
        });
    }
}
