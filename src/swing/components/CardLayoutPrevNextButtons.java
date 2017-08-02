/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.components;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//kp: https://docs.oracle.com/javase/tutorial/uiswing/layout/card.html
// CardLayout is another way to accomplish the same task as the tabbed pane but without
//    making the tabs visible, so all the JPanels (Panes) are stacked like cards
//In this example, it will have a JFrame 'f' containing two main JPanels - cards at
// the top taking of most of the space and control at the bottom.
// The cards will contain all the 9 subpanels (still JPanels) stacked together like the playing cards.
//         Each of these 9 subpanels will have a title of "Panel #", and a randomly chosen background color.
// The control panel at the bottom wil have the two buttons for "Prev" and "Next" actions



/** @see http://stackoverflow.com/questions/5654926 */
public class CardLayoutPrevNextButtons extends JPanel {
    private static int kpCounter = 0;// 
    private static final Random random = new Random();
    private static final JPanel cards = new JPanel(new CardLayout());
    private final String name;

    public CardLayoutPrevNextButtons(String name) {
        this.name = name;
        this.setPreferredSize(new Dimension(320, 240));
        this.setBackground(new Color(random.nextInt()));//kp: Background gets a random color
        this.add(new JLabel(name));
    }

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                create();
            }
        });
    }

    private static void create() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int i = 1; i < 9; i++) {
            CardLayoutPrevNextButtons p = new CardLayoutPrevNextButtons("Panel " + String.valueOf(i));
            //String.valueOf(i) returns the string representation. For example, if i were a boolean,
            // then it would show up as 'true' or 'false' even if we gave 1 or 0 for i.
            // For more info, see https://www.tutorialspoint.com/java/java_string_valueof.htm
            cards.add(p, p.toString());
        }
        JPanel control = new JPanel();
        JLabel jLabel = new JLabel("kpCounter = " + String.valueOf(kpCounter));
        
        //kp: defining a button, implementing the actionListener and adding to the panel all at the same time.
        control.add(new JButton(new AbstractAction("\u22b2Prev") {

            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.previous(cards);
                kpCounter--;
                jLabel.setText("kpCounter = " + String.valueOf(kpCounter));
            }
        }));
        control.add(new JButton(new AbstractAction("Next\u22b3") {

            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.next(cards);
                kpCounter++;
                jLabel.setText("kpCounter = " + String.valueOf(kpCounter));
            }
        }));
        
        control.add(jLabel);
        
        f.add(cards, BorderLayout.CENTER);//This is like an overayed tabbed panes (like a stack of cards)
        f.add(control, BorderLayout.SOUTH);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}