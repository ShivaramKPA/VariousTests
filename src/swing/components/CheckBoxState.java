/*
 * http://www.java2s.com/Code/Java/Swing-JFC/GetorsettheselectionstateofJCheckBox.htm
 * kp: 4/29/17: Copied to study how I could make a select-all checkbox that would
 *    select or deselect all others (in a group).
 */
package swing.components;

import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JFrame;

public class CheckBoxState extends JFrame {

    JCheckBox checkBox1 = new JCheckBox("Check me!", true);
    //https://docs.oracle.com/javase/7/docs/api/javax/swing/JCheckBox.html
    //Creates a check box with text and specifies whether or not it is initially selected.
    JCheckBox checkBox2 = new JCheckBox("Check me!");
    //checkBox2.setSelected(true);
    JCheckBox checkBox3 = new JCheckBox("Check me!", false);
    JCheckBox checkBox4 = new JCheckBox("Check me!", true);
    JCheckBox jCheckBoxSelectAll = new JCheckBox("Select All", false);

    public CheckBoxState() {
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        checkBox2.setSelected(true);
        boolean selected = checkBox1.isSelected();
        if (selected) {
            System.out.println("Check box state is selected.");
        } else {
            System.out.println("Check box state is not selected.");
        }

        addListenersToAllCheckBoxes();

        //Now adding all the checkboxes to the content-pane of the JFrame
        //   remember this class is an extension of JFrame and we are calling the 
        //   method getContentPane() of the parent (JFrame) class.
        getContentPane().add(checkBox1);
        getContentPane().add(checkBox2);
        getContentPane().add(checkBox3);
        getContentPane().add(checkBox4);
        getContentPane().add(jCheckBoxSelectAll);
    }

    private void addListenersToAllCheckBoxes() {
        checkBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBox1ActionPerformed(evt);
            }
        });

        checkBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBox2ActionPerformed(evt);
            }
        });

        checkBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBox3ActionPerformed(evt);
            }
        });

        checkBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBox4ActionPerformed(evt);
            }
        });

        jCheckBoxSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxSelectAllActionPerformed(evt);
            }
        });
    }

    private void checkBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkBox1.isSelected()) {
            System.out.println("checkBox1 is selected.");
        } else {
            System.out.println("checkBox1 is deselected.");
        }
    }

    private void checkBox2ActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkBox2.isSelected()) {
            System.out.println("checkBox2 is selected.");
        } else {
            System.out.println("checkBox2 is deselected.");
        }
    }

    private void checkBox3ActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkBox3.isSelected()) {
            System.out.println("checkBox3 is selected.");
        } else {
            System.out.println("checkBox3 is deselected.");
        }
    }

    private void checkBox4ActionPerformed(java.awt.event.ActionEvent evt) {
        if (checkBox4.isSelected()) {
            System.out.println("checkBox4 is selected.");
        } else {
            System.out.println("checkBox4 is deselected.");
        }
    }

    private void jCheckBoxSelectAllActionPerformed(java.awt.event.ActionEvent evt) {
        if (jCheckBoxSelectAll.isSelected()) {
            checkBox1.setSelected(true);
            checkBox2.setSelected(true);
            checkBox3.setSelected(true);
            checkBox4.setSelected(true);
        } else {
            checkBox1.setSelected(false);
            checkBox2.setSelected(false);
            checkBox3.setSelected(false);
            checkBox4.setSelected(false);
        }
        
        //Whether jCheckBoxSelectAll slected or not, call the following methods 
        //   whenever there is action on this box
        checkBox1ActionPerformed(evt);
        checkBox2ActionPerformed(evt);
        checkBox3ActionPerformed(evt);
        checkBox4ActionPerformed(evt);
    }

    public static void main(String[] args) {
        new CheckBoxState().setVisible(true);
    }
}
