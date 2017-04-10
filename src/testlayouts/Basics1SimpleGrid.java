/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testlayouts;
//https://designgridlayout.java.net/usage.html
//Requires an external jar https://java.net/projects/designgridlayout/downloads/download/designgridlayout-1.11.jar
import javax.swing.*;
import net.java.dev.designgridlayout.DesignGridLayout;
import net.java.dev.designgridlayout.IRow;

public class Basics1SimpleGrid 
{
    public static void main(String[] args) 
    {
        JFrame frame = new JFrame("Example 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel top = new JPanel();              
        DesignGridLayout layout = new DesignGridLayout(top);

        // You can add components one line at a time (not advised)
        IRow row = layout.row().grid();
        row.add(button());
        row.add(button());

        // Or using method chaining
        layout.row().grid().add(button()).add(button());

        // Or, even better, using variable arguments
        layout.row().grid().add(button(), button());

        frame.add(top);
        frame.pack();
        frame.setVisible(true);
    }

    public static JButton button()
    {
        return new JButton("Button");
    }
}
