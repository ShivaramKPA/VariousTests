/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.components;

/**
 *
 * @author
 * http://www.mathcs.emory.edu/~cheung/Courses/377/Syllabus/8-JDBC/GUI/layout.html
 */
import java.awt.*;
import javax.swing.*;

public class GridLayoutDemo2 {
    
    public static void main(String[] args) {
        JFrame f = new JFrame("GridLayout");

        JPanel MyPanel = new JPanel();

        MyPanel.setLayout(new GridLayout(4, 3));  // 4x3 Grid

        JButton x1 = new JButton("7");
        JButton x2 = new JButton("8");
        JButton x3 = new JButton("9");
        JButton x4 = new JButton("4");
        JButton x5 = new JButton("5");
        JButton x6 = new JButton("6");
        JButton x7 = new JButton("1");
        JButton x8 = new JButton("2");
        JButton x9 = new JButton("3");
        JButton x10 = new JButton("0");
        JButton x11 = new JButton(".");
        JButton x12 = new JButton("CE");

        MyPanel.add(x1);
        MyPanel.add(x2);
        MyPanel.add(x3);
        MyPanel.add(x4);
        MyPanel.add(x5);
        MyPanel.add(x6);
        MyPanel.add(x7);
        MyPanel.add(x8);
        MyPanel.add(x9);
        MyPanel.add(x10);
        MyPanel.add(x11);
        MyPanel.add(x12);

        f.getContentPane().add(MyPanel, "Center"); // Paste MyPanel into JFrame    

        f.setSize(300, 300);
        f.setVisible(true);
    }
}
