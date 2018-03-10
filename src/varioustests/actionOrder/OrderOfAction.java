/*
* @author m.c.kunkel, kpadhikari
*
* kp: 2/23/18: refactored method names to CamelCase ones
 */
//package org.jlab.dc_calibration.domain;
package varioustests.actionOrder;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class OrderOfAction {

    // *****************************************************
    // Define basic stuff:
        // kp: Declaring the data meme
    private int NButtons;      //kp: # of buttons to be put in this ordered group
    private JButton[] buttons; //kp: As many as NButtons
    private int[] isbuttonOn;  //kp: Initialized to 0s, but changed to 1 when clicked
    //kp: Initially all buttons are unclicked, so they should have 
    //    zeros as values. (We could also have used boolean instead)
    
    private int[] isbuttonPending; //kp: 0,1: 1 for the button to be clicked, 0 for others
    //
    
    private int NeffectiveButtons = 0;
    private int currentindex = 0;
    
    
    // *****************************************************
    /**
     * 
     * @param NButtons Number of buttons to be put in this ordered group
     */
    public OrderOfAction(int NButtons) {
        this.NButtons = NButtons;
        buttons = new JButton[NButtons];
        isbuttonOn = new int[NButtons];
        isbuttonPending = new int[NButtons];
        JFrame frame = new JFrame("Message explaining the color coding");
        String pt1 = "<html><body width='";
        String pt2 = "'><h1>Please select a radio button & <br> then check button color coding:</h1>"
                + "<p> <font color='red'>Red:</font> Button is not active - do NOT select<br><br> "
                + "<p> <font color='blue'>Blue:</font> Button is active - please select to continue<br><br>"
                + "<p> <font color='green'>Green:</font> Button was active - and action has been performed<br><br> " + "";
        String pall = pt1 + pt2;
        JOptionPane.showMessageDialog(frame, pall);

    }

    // ***********************************************************************
    // Read in existing buttons, define their initial colour and the order,
    // they need to be pushed:
    // kp:  This is called only once by a button - during the initial UI setup.
    //      That's why we require that the first button (order=1) be blue and 
    //      others red initially.
    
    /**
     * 
     * @param AButton
     * @param order 
     */
    public void setButtonOrder(JButton AButton, int order) {
        if (order <= NButtons && NeffectiveButtons <= NButtons) {
            buttons[order - 1] = AButton;

            // Set visual appearance of the buttons:
            buttons[order - 1].setOpaque(true);
            buttons[order - 1].setContentAreaFilled(true);

            if (order != 1) {
                buttons[order - 1].setBackground(Color.red);
                buttons[order - 1].setForeground(Color.red);
                isbuttonPending[order - 1] = 0;
            } else {
                buttons[order - 1].setBackground(Color.blue);
                buttons[order - 1].setForeground(Color.blue);
                isbuttonPending[order - 1] = 1;
            }

            // Set initial button status. 
            //kp: Initially all buttons are unclicked, so they should have 
            //    zeros as values. (We could also have used boolean instead)
            isbuttonOn[order - 1] = 0;
            NeffectiveButtons++; // Count, how many buttons have actually been set
        }
    }

    // ***********************************************************************
    // ***********************************************************************
    // Set status of each button pushed:
    // kp: refactored name.
    public void setButtonStatus(ActionEvent evt) {
        JButton thisbutton = (JButton) evt.getSource();

        for (int i = 0; i < NeffectiveButtons; i++) {
            if (thisbutton == buttons[i]) {
                isbuttonOn[i] = 1;
                currentindex = i;
                buttons[i].setBackground(Color.green); // and change color to green
                buttons[i].setForeground(Color.green);
            }
        }
    }
    // ***********************************************************************

    // ***********************************************************************
    // Get button status (only for debugging)
    // No relevant function
    public int getButtonStatus(ActionEvent evt) {
        JButton thisbutton = (JButton) evt.getSource();
        int var = 0;
        for (int i = 0; i < NeffectiveButtons; i++) {
            if (thisbutton == buttons[i]) {
                var = i;
            }
        }

        return isbuttonOn[var];
    }
    // ***********************************************************************

    // ***********************************************************************
    // Check if buttons were used in the defined order:
    public boolean isOrderOk() {
        boolean outval = false;
        int countActive = 0; // count active buttons according to their order
        int countInActive = 0; // count non-active buttons according to their order

        // Check if order of selecting the buttons has been respected
        // =============================================================
        for (int k = 0; k < currentindex + 1; k++) {
            if (isbuttonOn[k] == 1) {
                countActive++;
            } else {
                countInActive++;
            }

            if (countInActive > 0) {
                isbuttonOn[k] = 0; // Disable buttons if order has been violated
                if (isbuttonPending[k] == 0) {
                    buttons[k].setBackground(Color.red);
                    buttons[k].setForeground(Color.red);
                }
            }
        }
        // =============================================================

        // Set next button to be pushed (in order) blue
        // =============================================================
        if (countInActive == 0 && countActive < NeffectiveButtons) {
            buttons[currentindex + 1].setBackground(Color.blue);
            buttons[currentindex + 1].setForeground(Color.blue);
            isbuttonPending[currentindex + 1] = 1;
        }
        // =============================================================

        // Reset everything, if all buttons have been pressed (in the right order)
        // =============================================================
        if (countActive == NeffectiveButtons) {

            for (int k = 0; k < NeffectiveButtons; k++) {
                isbuttonOn[k] = 0;
                if (k > 0) {
                    isbuttonPending[k] = 0;
                    buttons[k].setBackground(Color.red);
                    buttons[k].setForeground(Color.red);
                } else {
                    buttons[k].setBackground(Color.blue);
                    buttons[k].setForeground(Color.blue);
                }
            }
        }
        // =============================================================

        if (countActive == currentindex + 1) {
            outval = true;
        } else {
            outval = false;
        }

        return outval;
    }
    // ***********************************************************************

    public int getCurrentIndex() {
        return this.currentindex;
    }
}
