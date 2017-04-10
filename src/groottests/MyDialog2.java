/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groottests;

//http://www2.hawaii.edu/~takebaya/ics111/jdialog/jdialog.html
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Frame;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

public class MyDialog2 extends JDialog implements ActionListener {

    private String[] data;
    private JTextField descBox;
    private JComboBox colorList;
    private JButton btnOk;
    private JButton btnCancel;

    File[] fileList = null;
    ArrayList<String> fileArray = null;

    public MyDialog2(Frame parent) {
        super(parent, "Enter data", true);
        Point loc = parent.getLocation();
        setLocation(loc.x + 80, loc.y + 80);
        data = new String[2]; // set to amount of data items
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);


        JLabel colorLabel = new JLabel("Choose Input File");
        //gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(colorLabel, gbc);

        JFileChooser inputFC = new JFileChooser();
        JButton bFC = new JButton("Choose Input File", createImageIcon("/images/Open16.gif"));
        bFC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Input File Chooser button has been hit..");
                chooseInputFiles(inputFC, e);
            }
        });
        bFC.setVisible(true);
        bFC.setBounds(10, 10, 180, 60);
        //panel.add(bFC);  

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(bFC, gbc);

        /*
      JLabel colorLabel = new JLabel("Choose color:");
      gbc.gridwidth = 1;
      gbc.gridx = 0;
      gbc.gridy = 1;
      panel.add(colorLabel,gbc);
      String[] colorStrings = {"red","yellow","orange","green","blue"};
      colorList = new JComboBox(colorStrings);
      gbc.gridwidth = 1;
      gbc.gridx = 1;
      gbc.gridy = 1;
      panel.add(colorList,gbc);
         */
        
        JLabel descLabel = new JLabel("Enter Iteration #:");
        gbc.gridwidth = 1;//gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(descLabel, gbc);
        descBox = new JTextField(30);
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(descBox, gbc);


        //This is to add the space between the Color/File Chooser row & the one with 'Ok' & 'Cancel'
        JLabel spacer = new JLabel(" ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(spacer, gbc);
        btnOk = new JButton("Ok");
        btnOk.addActionListener(this);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(btnOk, gbc);
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(btnCancel, gbc);
        getContentPane().add(panel);
        pack();
    }

    private void chooseInputFiles(JFileChooser iFC, ActionEvent evt) {
        iFC.setMultiSelectionEnabled(true);
        iFC.showOpenDialog(null);
        fileList = iFC.getSelectedFiles();
        fileArray = new ArrayList<String>();
        for (File file : fileList) {
            System.out.println("Ready to read file " + file);
            fileArray.add(file.toString());
        }
    }

    protected static ImageIcon createImageIcon(String path) {
        // java.net.URL imgURL = FileChooserDemo.class.getResource(path);
        ImageIcon myImageIcon;
        java.net.URL imgURL = MyDialog2.class.getResource(path);
        if (imgURL != null) {
            myImageIcon = new ImageIcon(imgURL);
            return myImageIcon;
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source == btnOk) {
            //data[0] = descBox.getText();
            //data[1] = (String)colorList.getSelectedItem();
            data[0] = fileArray.get(0); //Assuming that only one (hipo) file is selected
            data[1] = descBox.getText();
        } else {
            data[0] = null;
        }
        dispose();
    }

    public String[] run() {
        this.setVisible(true);
        return data;
    }
}
