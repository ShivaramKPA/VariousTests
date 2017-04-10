/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varioustests;

//http://stackoverflow.com/questions/4443878/redirecting-system-out-to-jtextpane
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class ConsoleOld implements Runnable
{
    JTextArea displayPane;
    BufferedReader reader;

    private ConsoleOld(JTextArea displayPane, PipedOutputStream pos)
    {
        this.displayPane = displayPane;

        try
        {
            PipedInputStream pis = new PipedInputStream( pos );
            reader = new BufferedReader( new InputStreamReader(pis) );
        }
        catch(IOException e) {}
    }

    public void run()
    {
        String line = null;

        try
        {
            while ((line = reader.readLine()) != null)
            {
//              displayPane.replaceSelection( line + "\n" );
                displayPane.append( line + "\n" );
                displayPane.setCaretPosition( displayPane.getDocument().getLength() );
            }

            System.err.println("im here");
        }
        catch (IOException ioe)
        {
            JOptionPane.showMessageDialog(null,
                "Error redirecting output : "+ioe.getMessage());
        }
    }

    public static void redirectOutput(JTextArea displayPane)
    {
        ConsoleOld.redirectOut(displayPane);
        ConsoleOld.redirectErr(displayPane);
    }

    public static void redirectOut(JTextArea displayPane)
    {
        PipedOutputStream pos = new PipedOutputStream();
        System.setOut( new PrintStream(pos, true) );

        ConsoleOld console = new ConsoleOld(displayPane, pos);
        new Thread(console).start();
    }

    public static void redirectErr(JTextArea displayPane)
    {
        PipedOutputStream pos = new PipedOutputStream();
        System.setErr( new PrintStream(pos, true) );

        ConsoleOld console = new ConsoleOld(displayPane, pos);
        new Thread(console).start();
    }

    public static void main(String[] args)
    {
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane( textArea );

        JFrame frame = new JFrame("Redirect Output");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.getContentPane().add( scrollPane );
        frame.setSize(200, 100);
        frame.setVisible(true);

        ConsoleOld.redirectOutput( textArea );
        final int i = 0;

        Timer timer = new Timer(1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println( new java.util.Date().toString() );
                System.err.println( System.currentTimeMillis() );
            }
        });
        timer.start();
    }
}