/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testlayouts;
// Copied from http://www.java2s.com/Tutorial/Java/0240__Swing/UsingaBoxLayoutManager.htm
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class BoxLayoutTest {

  public static void main(String[] args) {
    JFrame.setDefaultLookAndFeelDecorated(true);
    JFrame frame = new JFrame("BoxLayout Test");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS); // top to bottom
    //BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS); // left to right
    BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.LINE_AXIS); // left to right
    //BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS); // top to bottom
    frame.setLayout(boxLayout);
    frame.add(new JButton("Button 1"));
    frame.add(new JButton("Button 2"));
    frame.add(new JButton("Button 3"));
    frame.pack();

    frame.setVisible(true);
  }
}


/*
Arranges components either in a row or in a column.

    Box class offers a container that uses BoxLayout as its default layout manager.
    BoxLayout works to honor each component's x and y alignment properties as well as its maximum size.

The BoxLayout class has only one constructor:

public BoxLayout(java.awt.Container target, int axis)

    'target' argument specifies the container that needs to be laid out.
    'axis' specifies the axis to lay out components along.

The value of axis can be one of the following:

    BoxLayout.X_AXIS
    BoxLayout.Y_AXIS
    BoxLayout.LINE_AXIS
    BoxLayout.PAGE_AXIS
*/