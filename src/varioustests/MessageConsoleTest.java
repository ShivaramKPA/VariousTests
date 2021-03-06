/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varioustests;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class MessageConsoleTest
{
    public static int counter;

    public static void main(String[] args)
        throws Exception
    {
        JTextComponent textComponent = new JTextPane();
        JScrollPane scrollPane = new JScrollPane( textComponent );

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Message Console");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.getContentPane().add( scrollPane );
        frame.setSize(400, 120);
        frame.setVisible(true);

        MessageConsole console = new MessageConsole(textComponent);
        console.redirectOut();
        console.redirectErr(Color.RED, null);

        Timer timer = new Timer(1000, new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                System.out.println( new java.util.Date().toString() );
            }
        });
        timer.start();

        Thread.sleep(750);

        Timer timer2 = new Timer(1000, new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                System.err.println( "Error Message: " + ++counter);
            }
        });
        timer2.start();
    }
}