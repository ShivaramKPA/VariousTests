/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.consoles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author kpadhikari
   Ref: https://www.youtube.com/watch?v=A1jfhH_5GLM
        Java Tutorial - ConsoleNoOtherFiles Like Windows Command Prompt (Part 1)
        Also used the part 2 and a little (upto about 7:30 min) of 
      part 3 (https://www.youtube.com/watch?v=7pYw1WxDgzg)
 */
public class ConsoleNoOtherFiles {
    public static void main(String[] args) {
        new ConsoleNoOtherFiles();
    }
    
    public JFrame frame;
    public JTextPane console;
    public JTextField input;
    public JScrollPane scrollpane;
    
    public StyledDocument document;
    
    boolean trace = false;
    
    //Following is needed only for keeping history of past commands &
    //   make them show up with the up and down arrow keys.
    ArrayList<String> recent_used = new ArrayList<String>();
    int recent_used_id = 0;
    int recent_used_maximum = 10;
    
    //kp: Custom Constructor (to be used to initialize the GUI)
    public ConsoleNoOtherFiles() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex) {};
        
        frame = new JFrame("Console");//can do frame.setTitle("Cosole"); too.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        console = new JTextPane();
        console.setEditable(false);
        console.setFont(new Font("Courier New", Font.PLAIN, 12));
        console.setOpaque(false);
        
        document = console.getStyledDocument();
        
        input = new JTextField("Type your commands here.");
        input.setEditable(true);
        input.setBackground(Color.black);
        input.setForeground(Color.white);
        input.setCaretColor(Color.black);
        input.setOpaque(false);
    
        
        //kp: My lines to remind that the prompt is here. (May be I can have a blinking cursor/caret)
        //input.setText("Type your commands here."); 
        input.selectAll(); //Highlighting the text, so that one doesn't have to delete one by one to type new command
        
        //Adding two listener's to 'input' - Action & Key Listeners
        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = input.getText();
                if (text.length() > 1) {
                    //print(text + "\n", false, new Color(255, 255, 255)); //kp: This is defined below (not a std func)
                    print(text + "\n", true, new Color(255, 255, 255));//tracing
                    
                    recent_used.add(text);//Used to get prev. commands with up/down keys   
                    recent_used_id = 0;
                    
                    doCommand(text);
                    scrollBottom();
                    input.selectAll();//Highlighting the text when we press the Enter.
                }
            }
        });
        
        input.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (recent_used_id < (recent_used_maximum - 1) 
                            && recent_used_id < (recent_used.size() -1)) {
                        recent_used_id++;
                    }
                    input.setText(recent_used.get(recent_used.size() 
                            - 1 - recent_used_id));
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (recent_used_id > 0) {
                        recent_used_id--;
                    }
                    input.setText(recent_used.get(recent_used.size() 
                            - 1 - recent_used_id));
                    
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}            
            @Override
            public void keyTyped(KeyEvent e) {}            
        });
        
        
        scrollpane = new JScrollPane(console);
        //scrollpane.setBorder(null);
        scrollpane.setOpaque(false);
        scrollpane.getViewport().setOpaque(false);
        
        frame.add(input, BorderLayout.SOUTH);
        frame.add(scrollpane, BorderLayout.CENTER);
        frame.getContentPane().setBackground(new Color(50,50,50));
        
        frame.setSize(660,360);
        frame.setLocationRelativeTo(null);
        
        frame.setResizable(false);
        frame.setVisible(true);
        
    }
    
    public void doCommand(String s) {
        final String[] commands = s.split(" ");
        
        try {
            if (commands[0].equalsIgnoreCase("clear")) {
                clear();
            }
            else if (commands[0].equalsIgnoreCase("popup")) {
                String message = "";
                for (int i = 1; i < commands.length; i++) {
                    message += commands[i];
                    if(i != commands.length - 1) {
                        message += " ";
                    }
                }
                JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                  println(s, trace, new Color(255,255,255));            
            }
            
        } catch (Exception e) {
            println("Error -> " + e.getMessage(), trace, new Color(255,155,155));            
        }
     
    }
    
    public void scrollTop() {
        console.setCaretPosition(0);
    }
    
    public void scrollBottom() {
        console.setCaretPosition(console.getDocument().getLength());
    }   
    
    public void print(String s, boolean trace) {
        print(s, trace, new Color(255, 255, 255));
    }
    
    public void print(String s, boolean trace, Color c) { //Color here is that of the text
        Style style = console.addStyle("Style", null);
        StyleConstants.setForeground(style, c);
        
        if (trace) {
            Throwable t = new Throwable();
            StackTraceElement[] elements = t.getStackTrace();
            String caller = elements[0].getClassName();
            
            s = caller + " -> " + s;
        }
        
        try {
            document.insertString(document.getLength(), s, style);
        }
        catch (Exception ex) {}
    }
    
    public void println(String s, boolean trace) {
        println(s, trace, new Color(255,255,255));
    }
    
    public void println(String s, boolean trace, Color c) {
        print(s + "\n", trace, c);
    }    
    
    public void clear() {
        try {
            document.remove(0,document.getLength());
        }
        catch (Exception ex) {}
    }
}
