/*  +__^_________,_________,_____,________^-.-------------------,
 *  | |||||||||   `--------'     |          |                   O
 *  `+-------------USMC----------^----------|___________________|
 *    `\_,---------,---------,--------------'
 *      / X MK X /'|       /'
 *     / X MK X /  `\    /'
 *    / X MK X /`-------'
 *   / X MK X /
 *  / X MK X /
 * (________(                @author m.c.kunkel, kpadhikari
 *  `------'
 */
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package org.jlab.dc_calibration.clients;
package swing.consoles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.swing.Action;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;
//import org.jlab.dc_calibration.domain.DialogForRec;
//import org.jlab.dc_calibration.domain.DialogForT0cor;
//import org.jlab.dc_calibration.domain.DialogFor_tvsxCCDBwriter;
//import org.jlab.dc_calibration.domain.EstimateT0correction;

//import org.jlab.dc_calibration.domain.OrderOfAction;
//import org.jlab.dc_calibration.domain.RunReconstructionCoatjava4;
//import org.jlab.dc_calibration.domain.DC_TimeToDistanceFitter;
public class DC_GUI extends WindowAdapter implements WindowListener, ActionListener, Runnable {

    private JFrame frame;
    private JTextArea textArea;

    protected Thread reader, reader2;
    private boolean quit;

    private final PipedInputStream pin = new PipedInputStream();
    private final PipedInputStream pin2 = new PipedInputStream();

    static String strLinearFit = "Linear Fit";
    static String strNonLinearFit = "Non-linear Fit";
    private boolean isLinearFit;

    private File file;
    Thread errorThrower; // just for testing (Throws an Exception at this
    // Console
    Thread mythread;
    // Banner
    private JLabel banner;
    // JPanels to be used
    private JPanel bannerPanel, panelForWelcomeAndOpenFile, panelForVariousControls, panelImg, centerPanel;
    private int gridSize = 1;
    private JPanel buttonPanel, radioPanel;
    // a file chooser to be used to open file to analyze
    JFileChooser fc;
    // file to be read and analyzed
    private String fileName;
    // buttons to be implemented
    JButton bT0Correction;
    JButton bFileChooser, bTestEvent, bReadRecDataIn, bReconstruction, bTimeToDistance, bCCDBwriter, buttonClear;
    Dimension frameSize;
    OrderOfAction OA = null;

    File[] fileList = null;
    ArrayList<String> fileArray = null;

    public DC_GUI() {
        createFrame();
        createFileChooser();
        createButtons();
        createPanels();
        initFrame();
        
        //The following listen() method takes care of creating & preparing two 
        //    threads 'reader' and 'reader2' that handles redirecting std-out and 
        //    std-err by using piped input and output streams.
        listen(); //3/6/18
    }

    private void createFrame() {
        // create all components and add them
        frame = new JFrame("DC Calibration Console");
        frame.setLayout(new BorderLayout());// kp

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frameSize = new Dimension((int) (screenSize.width / 1.25), (int) (screenSize.height / 1.5));
        int x = (int) (frameSize.width / 2);
        int y = (int) (frameSize.height / 2);
        frame.setBounds(x, y, frameSize.width, frameSize.height);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Not needed if WindowListener below is active
        frame.addWindowListener(this);//3/3/18 //Above line not needed because WindowClosing() method does the same.
        frame.pack();
        frame.setSize(frameSize.width, frameSize.height);
        frame.setLocationRelativeTo(null);
        //frame.setVisible(true); //do it from initFrame() after adding all listeners.
    }

    private void createBanner() {
        banner = new JLabel("Welcome to DC Calibration for CLAS12!", JLabel.CENTER);
        banner.setForeground(Color.yellow);
        banner.setBackground(Color.gray);
        banner.setOpaque(true);
        banner.setFont(new Font("SansSerif", Font.BOLD, 20));
        banner.setPreferredSize(new Dimension(1000, 30));
    }

    private void createFileChooser() {
        fc = new JFileChooser();
    }

    private void createButtons() {
        bFileChooser = new JButton("Choose File", createImageIcon("/images/Open16.gif"));
        bT0Correction = new JButton();
        bTestEvent = new JButton();
        bReadRecDataIn = new JButton();
        bReconstruction = new JButton();
        bTimeToDistance = new JButton();
        bCCDBwriter = new JButton();
        buttonClear = new JButton("Clear");

        bTestEvent.setText("<html>" + "&emsp; &emsp; TestButton " + "<br>" + " Needs to be removed" + "</html>");
        bT0Correction.setText("<html>" + "Estimate T0s");
        bReadRecDataIn.setText("<html>" + "Run Decoder" + "</html>");
        bReconstruction.setText("<html>" + "Run Reconstruction" + "</html>");
        bTimeToDistance.setText("<html>" + "Run Time vs. Distance Fitter" + "</html>");
        bCCDBwriter.setText("<html>" + "Load xvst pars to CCDB" + "</html>");

        //bReconstruction.setPreferredSize(new Dimension(frameSize.width / 3, frameSize.height / 3));
        bTimeToDistance.setPreferredSize(new Dimension(frameSize.width / 3, frameSize.height / 3));

    }

    private void createPanels() {
        bannerPanel = new JPanel(new BorderLayout());
        addToBanner();

        panelForVariousControls = new JPanel(new BorderLayout());
        panelForVariousControls.setBorder(BorderFactory.createEtchedBorder());
        //addToOpenFilePanel(); //Moved below
        radioPanel = new JPanel(new GridLayout(0, 1));
        addToT0CorButton();
        addToRecoButton();
        addToRadioPanel();
        addToOpenFilePanel();//add File-chooser, radio panel etc to the control panel
        addToCCDBwriterButton(); //CCDB writer for tvsx parameters

        panelForWelcomeAndOpenFile = new JPanel(new BorderLayout());
        addToWelcomePanel();

        panelImg = new JPanel(new BorderLayout());
        addToPanelImage();

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(gridSize, gridSize, 1, 1));

        addToButtonPanel();

        centerPanel = new JPanel(new BorderLayout());
        addToCenterPanel();
    }

    private void addToBanner() {
        createBanner();
        bannerPanel.add(banner, BorderLayout.CENTER);
    }

    private void addToOpenFilePanel() {

        //Pack bRec & radioPanel into subpanel1 //& add to the main panel @ start
        JPanel subControlPanel1 = new JPanel(new BorderLayout());
        subControlPanel1.add(bReconstruction, BorderLayout.LINE_START);
        subControlPanel1.add(radioPanel, BorderLayout.CENTER);
        //panelForVariousControls.add(subControlPanel1, BorderLayout.LINE_START);

        //Pack bT0Correction & subControlPanel1 into subpanel0 & add to the main panel @ start
        JPanel subControlPanel0 = new JPanel(new BorderLayout());
        subControlPanel0.add(bT0Correction, BorderLayout.LINE_START);
        subControlPanel0.add(subControlPanel1, BorderLayout.CENTER);
        panelForVariousControls.add(subControlPanel0, BorderLayout.LINE_START);

        //Pack bFileChooser to another subpanel & add it to center of main Panel
        JPanel subControlPanel2 = new JPanel(new BorderLayout());
        subControlPanel2.add(bFileChooser, BorderLayout.LINE_START);
        panelForVariousControls.add(subControlPanel2, BorderLayout.CENTER);

        panelForVariousControls.add(bCCDBwriter, BorderLayout.LINE_END);
    }

    private void addToWelcomePanel() {
        panelForWelcomeAndOpenFile.add(bannerPanel, BorderLayout.NORTH);
        panelForWelcomeAndOpenFile.add(panelForVariousControls, BorderLayout.SOUTH);
    }

    private void addToPanelImage() {
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(this.getClass().getResource("/images/CLAS12.jpg")).getImage()
                .getScaledInstance(320, 320, java.awt.Image.SCALE_SMOOTH));
        // ImageIcon(this.getClass().getResource("images/timeVsTrkDoca_and_Profiles.png"));
        JLabel imgLabel = new JLabel(imageIcon);
        panelImg.add(imgLabel, BorderLayout.CENTER);
    }

    private void addToPanelImage(String whoMadeMeWake) {
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(this.getClass().getResource("/images/CLAS12.jpg")).getImage()
                .getScaledInstance(320, 320, java.awt.Image.SCALE_SMOOTH));
        // ImageIcon(this.getClass().getResource("images/timeVsTrkDoca_and_Profiles.png"));
        JLabel imgLabel = new JLabel(imageIcon);
        panelImg.add(imgLabel, BorderLayout.CENTER);
    }

    private void addToT0CorButton() {
        bT0Correction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                String choice = ae.getActionCommand();
                if (choice.equals("Quit")) {
                    System.exit(0);
                } else {
                    createDialogForT0Correction();
                }
            }
        });
    }

    private void addToCCDBwriterButton() {
        bCCDBwriter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                String choice = ae.getActionCommand();
                if (choice.equals("Quit")) {
                    System.exit(0);
                } else {

                    createDialogForTvsX_CCDBwriter();
                }
            }
        });
    }

    private void addToRecoButton() {
        bReconstruction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Reconstruction Button has been hit..");
                //createDialogForRecControls();
                //RunReconstructionCoatjava4 rec = new RunReconstructionCoatjava4();
                String choice = ae.getActionCommand();
                if (choice.equals("Quit")) {
                    System.exit(0);
                } //else if (choice.equals("Enter data")) {
                else {
                    createDialogForRecControls();
                }
            }
        });
    }

    private void createDialogForTvsX_CCDBwriter() {
        System.out.println("The button for CCDB upload has been clicked.");
//        DialogFor_tvsxCCDBwriter dlg = new DialogFor_tvsxCCDBwriter(frame);
//        String[] results = dlg.run();
//        ArrayList<String> fileArray = dlg.getFileArray();
//        if (results[0] != null) {
//            
//            String s = null;
//            String command = null;
//
//            try {
//                System.out.println("File to be uploaded: " + results[0]
//                        + "\nComments to be added: '" + results[1] + "'");
//                //Process p = Runtime.getRuntime().exec("pwd");
//                
//                command = String.format("./src/files/loadFitParsToCCDB.csh %s '%s'", results[0],results[1]);
//                System.out.println("The following command is being executed: \n " + command);
//                command = "./src/files/justEchoHello.sh";
//                Process p = Runtime.getRuntime().exec(command);
//                BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
//                BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//
//                // read the output from the command
//                //System.out.println("Here is the standard output of the command:\n");
//                while ((s = stdInput.readLine()) != null) {
//                    System.out.println(s);
//                }
//
//                // read any errors from the attempted command
//                //System.out.println("Here is the standard error of the command (if any):\n");
//                while ((s = stdError.readLine()) != null) {
//                    System.out.println(s);
//                }
//
//                System.exit(0);
//            } catch (IOException e) {
//                System.out.println("exception happened - here's what I know: ");
//                e.printStackTrace();
//                System.exit(-1);
//            }
//        }
    }

    private void createDialogForT0Correction() {
        System.out.println("The button for T0 Estimation has been clicked.");
//        //DialogForRec dlg = new DialogForRec(frame);
//        DialogForT0cor dlg = new DialogForT0cor(frame);
//        String[] results = dlg.run();
//        ArrayList<String> fileArray = dlg.getFileArray();
//        if (results[0] != null) {
//            JOptionPane.showMessageDialog(frame,
//                    "Input file: " + results[0] + "\nOutput file: " + results[1]); 
//            
//                    System.out.println("Debug 0");
//            EstimateT0correction t0c = new EstimateT0correction(results, fileArray);
//            t0c.DrawPlots(); 
//            t0c.DrawPlotsForAllCables();
//            t0c.DrawPlotsForTMaxAllCables();
//            System.out.println("Finished drawing the T0 plots ..");
//        }
    }

    private void chooseInputFiles(JFileChooser iFC, ActionEvent evt) {
        iFC.setMultiSelectionEnabled(true);
        iFC.showOpenDialog(null);
        fileList = iFC.getSelectedFiles();
        fileArray = new ArrayList<String>();
        for (File file : fileList) {
            System.out.println("Ready to read file " + file);
            fileArray.add(file.toString());
        }
    }

    private void createDialogForRecControls() {
//        DialogForRec dlg = new DialogForRec(frame);
//        String[] results = dlg.run();
//        if (results[0] != null) {
//            JOptionPane.showMessageDialog(frame,
//                    "Input file: " + results[0] + "\nOutput file: " + results[1]); 
//            
//            //Now make RunReconstructionCoatjava4() take results as input arg & control IP & OP files
//            RunReconstructionCoatjava4 rec = new RunReconstructionCoatjava4(results);
//        }
    }

    private void addToButtonPanel() {
        // buttonPanel.add(bTestEvent);
        // buttonPanel.add(bReadRecDataIn);
        //buttonPanel.add(bReconstruction);
        buttonPanel.add(bTimeToDistance);
        //buttonPanel.add(bCCDBwriter);
        OrderOfAction(2); // this int in OrderOfAction is the number of buttons activated in this method
    }

    private void OrderOfAction(int NButtons) {
        OA = new OrderOfAction(NButtons);
        // OA.setbuttonorder(bReconstruction, 1);
        OA.setbuttonorder(bFileChooser, 1);

        OA.setbuttonorder(bTimeToDistance, 2);
    }

    private void addToCenterPanel() {
        int width = (int) (frameSize.width);
        int height = (int) (frameSize.height);
        addToTextArea();
        JScrollPane images = new JScrollPane(panelImg);
        images.setPreferredSize(new Dimension((int) (width / 3.5), (int) (height / 3.5)));
        centerPanel.add(images, BorderLayout.WEST);

        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        centerPanel.add(Box.createVerticalGlue(), BorderLayout.SOUTH);

        JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension((int) (width / 2), (int) (height / 2)));
        //scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        centerPanel.add(scroll, BorderLayout.EAST);
    }

    private void addToTextArea() {
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(false);//(true);//this makes horizontal scroll bar to show up as well.
        textArea.setWrapStyleWord(true);
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    /**
     * This method is to get edit menu when right-clicked on the textfield
     */
    private void AddEditOptionsMenu() {

        JPopupMenu menu = new JPopupMenu();
        Action cut = new DefaultEditorKit.CutAction();
        cut.putValue(Action.NAME, "Cut");
        cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        menu.add(cut);

        Action copy = new DefaultEditorKit.CopyAction();
        copy.putValue(Action.NAME, "Copy");
        copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        menu.add(copy);

        Action paste = new DefaultEditorKit.PasteAction();
        paste.putValue(Action.NAME, "Paste");
        paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        menu.add(paste);

        //Action selectAll = new DefaultEditorKit.selectAllAction(); //kp: doesn't work
        Action selectAll = new SelectAll(); //kp: See this local clas defined below.       
        menu.add(selectAll);

        textArea.setComponentPopupMenu(menu);
    }

    static class SelectAll extends TextAction {

        public SelectAll() {
            super("Select All");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));//kp:("control S"));
        }

        public void actionPerformed(ActionEvent e) {
            JTextComponent component = getFocusedComponent();
            component.selectAll();
            component.requestFocusInWindow();
        }
    }

    private void addToRadioPanel() {
        //super(new BorderLayout());

        //Create the radio buttons.    
        //JRadioButton pigButton = new JRadioButton(pigString);
        //pigButton.setMnemonic(KeyEvent.VK_P);
        //pigButton.setActionCommand(pigString);
        JRadioButton linearFitRadioButton = new JRadioButton(strLinearFit);
        //linearFitRadioButton.setMnemonic(KeyEvent.VK_L);
        linearFitRadioButton.setActionCommand(strLinearFit);
        JRadioButton nonLinearFitButton = new JRadioButton(strNonLinearFit);
        //nonLinearFitButton.setMnemonic(KeyEvent.VK_N);
        nonLinearFitButton.setActionCommand(strNonLinearFit);

        //kp: If not grouped, more than one (even all buttons can be selected simulataneously)
        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(linearFitRadioButton);
        group.add(nonLinearFitButton);

        //frame.addWindowListener(this); //kp: 3/3/18
        //Register a listener for the radio buttons.        
        //linearFitRadioButton.addActionListener(this);
        linearFitRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isLinearFit = true;
                //listen();
                addListeners();
            }
        }
        );

        /*
        Following Lambda exp. was created automatically by clicking on the little
        yellow 'suggestion' bulb that asked whether I wanted to use the lambda 
        expression instead of my original code which was similar to above */
        nonLinearFitButton.addActionListener((ActionEvent e) -> {
            isLinearFit = false;
            //listen();
            addListeners();
        });

        //addListeners(); //3/3/18: adding actionListener to bTimeToDistance
        //Put the radio buttons in a column in a panel.
        radioPanel.add(linearFitRadioButton);
        radioPanel.add(nonLinearFitButton);
    }

    public void popoutInfoAboutOrderOfAction() {
        JFrame frame = new JFrame("Message explaining the color coding");
        String pt1 = "<html><body width='";
        String pt2 = "'><h1>Please select a radio button & <br> then check button color coding:</h1>"
                + "<p> <font color='red'>Red:</font> Button is not active - do NOT select<br><br> "
                + "<p> <font color='blue'>Blue:</font> Button is active - please select to continue<br><br>"
                + "<p> <font color='green'>Green:</font> Button was active - and action has been performed<br><br> " + "";
        String pall = pt1 + pt2;
        JOptionPane.showMessageDialog(frame, pall);
    }

    private void initFrame() {

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panelForWelcomeAndOpenFile, BorderLayout.NORTH);
        frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
        frame.getContentPane().add(buttonClear, BorderLayout.SOUTH);
        //frame.setVisible(true); // this line is active also in createFrame(), called earlier

        frame.setVisible(true); // this line is active also in createFrame(), called earlier
        popoutInfoAboutOrderOfAction(); //Call this after main window pops-up, so both are visible.
    }

    private void addListeners() {
        //listen(); //kp: Moving this to the constructor of this class, so that all std-out will be redirected

        System.out.println("isLinearFit = " + isLinearFit);
        if (isLinearFit) {
            System.out.println("You selected Linear Fit.");
        } else {
            System.out.println("You selected Non-Linear Fit.");
        }
        bFileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OA.buttonstatus(e);
                if (OA.isorderOk()) {
                    chooseFiles(e);
                    if (fileArray.size() == 0) {
                        System.err.println("There are no files selected ");
                        System.exit(1);
                    }

                    DC_TimeToDistanceFitter e3 = new DC_TimeToDistanceFitter(OA, fileArray, isLinearFit);
                    //TimeToDistanceFitter e3 = new DC_TimeToDistanceFitter(fileArray, isLinearFit);
                    //ReadRecDataForMinuitNewFileOldWay e3 = new ReadRecDataForMinuitNewFileOldWay(OA, fileArray, isLinearFit);
                    bTimeToDistance.addActionListener(ee -> {
                        new Thread(e3).start();
                    });
                }
            }
        });
        //listen();
    }

    private void listen() {
        //frame.addWindowListener(this);
        try {
            PipedOutputStream pout = new PipedOutputStream(this.pin);
            /*
            https://www.tutorialspoint.com/java/lang/system_setout.htm
            The java.lang.System.setOut() method reassigns the "standard" output stream.
             */
            System.setOut(new PrintStream(pout, true));
            //PrintStream(OutputStream out, boolean autoFlush) - Creates a new print stream.
            // https://docs.oracle.com/javase/7/docs/api/java/io/PrintStream.html
        } catch (java.io.IOException io) {
            textArea.append("Couldn't redirect STDOUT to this console\n" + io.getMessage());
        } catch (SecurityException se) {
            textArea.append("Couldn't redirect STDOUT to this console\n" + se.getMessage());
        }
        try {
            PipedOutputStream pout2 = new PipedOutputStream(this.pin2);
            System.setErr(new PrintStream(pout2, true));
        } catch (java.io.IOException io) {
            textArea.append("Couldn't redirect STDERR to this console\n" + io.getMessage());
        } catch (SecurityException se) {
            textArea.append("Couldn't redirect STDERR to this console\n" + se.getMessage());
        }

        quit = false; // signals the Threads that they should exit
        reader = new Thread(this);
        reader.setDaemon(true); // kp: make this thread a process running in the
        // background (no interactive access)
        reader.start(); // kp: start this thread
        //
        reader2 = new Thread(this);
        reader2.setDaemon(true);
        reader2.start();
    }

    private void chooseFiles(ActionEvent evt) {
        fc.setMultiSelectionEnabled(true);
        fc.showOpenDialog(null);
        fileList = fc.getSelectedFiles();
        fileArray = new ArrayList<String>();
        for (File file : fileList) {
            System.out.println("Readying file " + file);
            fileArray.add(file.toString());
        }
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected static ImageIcon createImageIcon(String path) {
        // java.net.URL imgURL = FileChooserDemo.class.getResource(path);
        ImageIcon myImageIcon;
        java.net.URL imgURL = DC_GUI.class.getResource(path);
        if (imgURL != null) {
            myImageIcon = new ImageIcon(imgURL);
            return myImageIcon;
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    //
    // kp: 3/3/18:
    //The windowClosed(..), windowClosing(..) etc are some of the methods of
    // the WindowListener interface. We don't have to implement all the methods of this
    // interface here because this class also extends WindowAdapter which is an abstract
    //  adapter class for receiving window events. The methods in this abstract class  
    //   are empty and the class exists as convenience for creating listener objects. 
    //  (If you implement the WindowListener interface, you have to define all of the 
    //     methods in it. This abstract class defines null methods for them all, so you can 
    //     only have to define methods for events you care about.) 
    // see https://docs.oracle.com/javase/7/docs/api/java/awt/event/WindowListener.html 
    // and https://docs.oracle.com/javase/7/docs/api/java/awt/event/WindowAdapter.html 
    //  The latter webpage says the following about WindowAdapter abstract class:
    //      Create a listener object using the extended class and then register it with a 
    //      Window (frame in our case) using the window's addWindowListener method (for 
    //      example see the line 'frame.addWindowListener(this)' above. When the window's status 
    //      changes by virtue of being opened, closed, activated or deactivated, iconified 
    ///     or deiconified, the relevant method in the listener object ('this' object in 
    //      this/our case) is invoked, and the WindowEvent is passed to it.
    //
    public synchronized void windowClosed(WindowEvent evt) {
        quit = true;
        // https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html
        // notify() :   Wakes up a single thread that is waiting on this object's monitor.
        // notifyAll() : Wakes up all threads that are waiting on this object's monitor.
        this.notifyAll(); // stop all threads (kp: notify All threads?)
/*        
        // kp:
        // https://docs.oracle.com/javase/tutorial/essential/concurrency/join.html

        The join method allows one thread to wait for the completion of another. If t is 
        a Thread object whose thread is currently executing,
        
        t.join();

        causes the current thread to pause execution until t's thread terminates. 
        Overloads of join allow the programmer to specify a waiting period. However,
        as with sleep, join is dependent on the OS for timing, so you should not assume 
        that join will wait exactly as long as you specify.

        Like sleep, join responds to an interrupt by exiting with an InterruptedException.
        
        https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html
        join() : Waits for this thread to die.
        join(long millis) : Waits at most millis milliseconds for this thread to die.
        join(long millis, int nanos)  : Waits at most millis milliseconds plus nanos 
        nanoseconds for this thread to die.
         */       
        
/*
* https://www.youtube.com/watch?v=vLjucKGR654 JOIN() METHOD : JAVA MULTITHREADING TUTORIALS
*   3/8/18:
*  Join() method ensures termination of thread which calls its join method 
*   before any other thread such as (main thread) terminates.
*   This method can be used to decide sequence of thread's execution.
*        
*  Whenever a thread calls the join() method from another thread, the 'hosting
*    thread' (from which the call is made) has to wait until that thread (whose 
*    join method is called) completes it's execution.
*
         */       
        try {            
            reader.join(1000);
            pin.close();
        } catch (Exception e) {
        }
        try {
            reader2.join(1000);
            pin2.close();
        } catch (Exception e) {
        }
        System.exit(0);
    }

    public synchronized void windowClosing(WindowEvent evt) {
        frame.setVisible(false); // default behaviour of JFrame
        frame.dispose();
    }


    //kp: 3/3/18: This is required because this class is implementing ActionListener interface
    //      of which this is the only method (that needs to be implemented).
    // see https://docs.oracle.com/javase/7/docs/api/java/awt/event/ActionListener.html
    // The 'this' object of this class is passed to addActionListener(this) method of the Frame
    //   object so that we can capture the actions on the main frame.
    public synchronized void actionPerformed(ActionEvent evt) {
        // Handle open button action.
        // if (evt.getSource() == bFileChooser) {
        // int returnVal = fc.showOpenDialog(fc);
        // if (returnVal == JFileChooser.APPROVE_OPTION) {
        // file = fc.getSelectedFile();
        // try {
        // this.fileName = file.getCanonicalPath();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // System.out.println("Opening: " + fileName + "\n");
        // } else {
        // // log.append("Open command cancelled by user." + newline);
        // System.out.println("Open command cancelled by user.\n");
        // }
        // } else if (evt.getSource() == buttonClear) {
        if (evt.getSource() == buttonClear) {
            textArea.setText("");
        }
        OA.buttonstatus(evt);

        if (OA.isorderOk()) {
            System.out.println("I am green and now I should do something here...");
        } else {
            System.out.println("I am red and it is not my turn now ;( ");
        }

    }

    //kp: 3/3/18: This is required because this class is implementing Runnable interface
    //      of which this is the only method (that needs to be implemented).
    //    Remember that this method will be executed when two threads 'reader' and 
    //       'reader2' are start()ed in listen() method above, that's because we're
    //        passing 'this' into those threads during the creation of the treads by 
    //        lines such as 'reader = new Thread(this);'. Note that 'this' is the 
    //        reference to the object of this class that we're working on here, which 
    //        means, we're passing the reference of the same instance (object) of this
    //        class into the two different threads (we don't have two copies of the object)
    //      
    //    As this doc https://docs.oracle.com/javase/tutorial/java/javaOO/thiskey.html says
    //        Within an instance method or a constructor, this is a reference to the current 
    //        object — the object whose method or constructor is being called. You can refer 
    //        to any member of the current object from within an instance method or a 
    //        constructor by using this.
    //
    // see https://docs.oracle.com/javase/7/docs/api/java/lang/Runnable.html
    // When an object implementing interface Runnable is used to create a thread, starting 
    // the thread causes the object's run method to be called in that separately executing thread.
    // The general contract of the method run() is that it may take any action whatsoever.
    public synchronized void run() {
        try {
            while (Thread.currentThread() == reader) {
                try {
                    this.wait(100);
                } catch (InterruptedException ie) {
                }
                if (pin.available() != 0) {
                    String input = this.readLine(pin);
                    textArea.append(input);
                }
                if (quit) {
                    return;
                }
            }

            while (Thread.currentThread() == reader2) {
                try {
                    this.wait(100);
                } catch (InterruptedException ie) {
                }
                if (pin2.available() != 0) {
                    String input = this.readLine(pin2);
                    textArea.append(input);
                }
                if (quit) {
                    return;
                }
            }
        } catch (Exception e) {
            textArea.append("\nConsole reports an Internal error.");
            textArea.append("The error is: " + e);
        }

        /*
		 * // just for testing (Throw a Nullpointer after 1 second) if (Thread.currentThread()==errorThrower) { try { this.wait(1000); }catch(InterruptedException
		 * ie){} throw new NullPointerException( "Application test: throwing an NullPointerException It should arrive at the console" ); }
         */
    }

    public synchronized String readLine(PipedInputStream in) throws IOException {
        String input = "";
        do {
            /**
             * kp: PipedInputStream inherits from InputStream and available() is
             * one of its methods.
             * https://docs.oracle.com/javase/7/docs/api/java/io/InputStream.
             * html available(): Returns an estimate of the number of bytes that
             * can be read (or skipped over) from this input stream without
             * blocking by the next invocation of a method for this input
             * stream.
             *
             * read(byte[] b): Reads some number of bytes from the input stream
             * and stores them into the buffer array b.
             */
            int available = in.available();
            if (available == 0) {
                break;
            }
            byte b[] = new byte[available]; // kp: creating a 'byte' array of
            // size 'available'
            in.read(b);
            input = input + new String(b, 0, b.length);
        } while (!input.endsWith("\n") && !input.endsWith("\r\n") && !quit);
        return input;
    }

    // private void initMenu() {
    // JMenuBar bar = new JMenuBar();
    // JMenu fileMenu = new JMenu("File");
    // JMenuItem processItem = new JMenuItem("Process File");
    // processItem.addActionListener(this);
    // fileMenu.add(processItem);
    // bar.add(fileMenu);
    //
    // JMenu pluginMenu = new JMenu("Plugins");
    // JMenuItem loadPlugin = new JMenuItem("Tree Viewer");
    // loadPlugin.addActionListener(this);
    // pluginMenu.add(loadPlugin);
    //
    // bar.add(pluginMenu);
    //
    // this.setJMenuBar(bar);
    // }
}
