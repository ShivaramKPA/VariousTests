/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.components;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author kpadhikari
 */
public class TryGridBagConstraints {
//public class GridBagButtons {
  private static final Insets insets = new Insets(0, 0, 0, 0);//kp: minimum empty space between grids or components
  //   Just like the spaces between pads in TCanvas that I used to control by giving very small numbers
  //  for the last two arguments in Divide(nCol or nx, nRow or ny, xMargin, yMargin);
  //  we can also use Insets(..) to control the margins/spaces between the enclosing window and the containers
  //  that occupy each of the grids.

  public static void main(final String args[]) {
    final JFrame frame = new JFrame("GridBagConstraints");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new GridBagLayout());
    JButton button;
    // Row One - Three Buttons
    button = new JButton("One");
    addComponent(frame, button, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    button = new JButton("Two");
    addComponent(frame, button, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    button = new JButton("Three");
    addComponent(frame, button, 2, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    // Row Two - Two Buttons
    button = new JButton("Four");
    addComponent(frame, button, 0, 1, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    button = new JButton("Five");
    addComponent(frame, button, 2, 1, 1, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    // Row Three - Two Buttons
    button = new JButton("Six");
    addComponent(frame, button, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    button = new JButton("Seven");
    addComponent(frame, button, 1, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    frame.setSize(500, 200);
    frame.setVisible(true);
  }

  private static void addComponent(Container container, Component component, int gridx, int gridy,
      int gridwidth, int gridheight, int anchor, int fill) {
    GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
        anchor, fill, insets, 0, 0);
    container.add(component, gbc);
  }
}

/*
http://stackoverflow.com/questions/12796195/how-to-set-gaps-for-all-components-in-the-gridbaglayout
Q: ===========


When you are using group layout you set all the gaps with:

setAutoCreateGaps(true);
setAutoCreateContainerGaps(true);

Is there a same function for GridBagLayout?


A:================

In GridBagLayout, using GridBagConstraints you can set gaps with the below properties;

    GridBagConstraints.ipadx,GridBagConstraints.ipady: Specifies the component's internal padding within the layout.

    GridBagConstraints.insets: Specifies the component's external padding.

    GridBagConstraints.weightx,GridBagConstraints.weighty: Used to determine how to distribute space.

For Example:

pane.setLayout(new GridBagLayout());
GridBagConstraints c = new GridBagConstraints();
c.fill = GridBagConstraints.HORIZONTAL;
c.ipady = 40;      //make this component tall
c.ipadx = 10;      //make this component wide
c.anchor = GridBagConstraints.PAGE_END; //bottom of space
c.insets = new Insets(10,0,0,0);  //top padding
c.gridx = 1;       //first column
c.gridy = 2;       //third row
c.gridwidth = 2;   //2 columns wide
c.weightx = 0.5;   //increase horizontal space
c.weighty = 1.0;   //increase vertical space


*/