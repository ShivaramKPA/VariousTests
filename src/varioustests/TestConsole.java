/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varioustests;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 *
 * @author kpadhikari
 */
public class TestConsole {
    public static void main(String[] args) {
        try {
            new Console();
        } catch (IOException e) {
        }
        System.out.println("Hello !!!");
    } 
    
}
