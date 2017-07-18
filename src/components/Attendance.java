/*
 * http://stackoverflow.com/questions/28169203/how-to-make-select-all-checkbox-work
 * kp: 4/29/17: Copied to study how I could make a select-all checkbox that would
 *    select or deselect all others (in a group).
 */
package components;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JCheckBox;

public class Attendance extends JFrame implements MouseListener {

    static JTable table;
    JLabel lblDate;
    JLabel lblNewLabel;
    JCheckBox chckbxSelectAll;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Attendance frame = new Attendance();
                frame.setVisible(true);
            }
        });
    }

    /**
     * Create the frame.
     */
    public Attendance() {
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        // Customer List
        JLabel lblAttendance = new JLabel("<html><u>Attendance</u></html>");
        lblAttendance.setBounds(231, 11, 143, 25);
        lblAttendance.setFont(new Font("Lucida Calligraphy", Font.BOLD, 20));
        getContentPane().add(lblAttendance);

        // ScrollPane
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(28, 59, 525, 240);
        getContentPane().add(scrollPane);

        // Table
        table = new JTable();
        scrollPane.setViewportView(table);

        // Button Update
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(255, 310, 89, 30);
        btnUpdate.setFont(new Font("Verdana", Font.PLAIN, 15));
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpdateData();

                //UpdateData(strUserID); // Update Data
                PopulateData(); // Reload Table

            }
        });
        getContentPane().add(btnUpdate);

        chckbxSelectAll = new JCheckBox("Select All");
        chckbxSelectAll.setBounds(459, 29, 97, 23);
        chckbxSelectAll.setBackground(Color.WHITE);
        chckbxSelectAll.setFont(new Font("Verdana", Font.PLAIN, 15));
        chckbxSelectAll.addMouseListener(this);
        getContentPane().add(chckbxSelectAll);

        lblDate = new JLabel("");
        lblDate.setFont(new Font("Verdana", Font.PLAIN, 15));
        lblDate.setBounds(28, 35, 136, 13);
        getContentPane().add(lblDate);

        clock();
        PopulateData();
    }

    private void clock() {
        Calendar cal = new GregorianCalendar();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.JANUARY);
        int year = cal.get(Calendar.YEAR);

        lblDate.setText("Date :" + year + "/" + month + "/" + day + "");

    }

    private static void PopulateData() {

        // Clear table
        table.setModel(new DefaultTableModel());

        // Model for Table
        DefaultTableModel model = new DefaultTableModel() {

            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    case 4:
                        return int.class;
                    case 5:
                        return Boolean.class;
                    default:
                        return String.class;
                }
            }
        };
        table.setModel(model);

        // Add Column
        // model.addColumn("Select");
        model.addColumn("UserId");
        model.addColumn("Name");
        model.addColumn("Date");
        model.addColumn("Attended");
        model.addColumn("Count");
        model.addColumn("Select");

        Connection con = null;
        Statement s = null;

        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            con = DriverManager.getConnection("jdbc:odbc:Project");

            s = con.createStatement();

            String sql = "SELECT * FROM  Attendance ";

            ResultSet rec = s.executeQuery(sql);
            int row = 0;
            while ((rec != null) && (rec.next())) {
                model.addRow(new Object[0]);
                model.setValueAt(false, row, 4); // Checkbox
                model.setValueAt(rec.getString("UserId"), row, 0);
                model.setValueAt(rec.getString("Name"), row, 1);
                model.setValueAt(rec.getString("Date"), row, 2);
                model.setValueAt(rec.getString("Attended"), row, 3);
                model.setValueAt(rec.getString("Count"), row, 4);

                row++;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }

    }

    // Update
    private void UpdateData() {

        Connection connect = null;
        Statement s = null;
        ResultSet rs = null;

        try {
            System.out.println("Entered");

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            connect = DriverManager.getConnection("jdbc:odbc:Project");
            System.out.println("Connected");
            s = connect.createStatement();

            String Query2 = "select * from Attendance ";

            rs = s.executeQuery(Query2);

            String id = "";

            while (rs.next()) {
                id = rs.getString(1);
            }
            System.out.println(id);
            //System.out.println(strUserID);
            String sql = "UPDATE Attendance SET Count=(Count+1) where UserId = '" + id + "'";
            s.executeUpdate(sql);
            System.out.println("Updated");

            String sql2 = "UPDATE Attendance SET Attended=('Present') where UserId = '" + id + "'";
            s.executeUpdate(sql2);
            System.out.println("Done");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            //JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }

    }

//    @Override
//    public void mouseClicked(MouseEvent e) {
//       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        if(e.getSource()==chckbxSelectAll)
//        {
//            //System.out.println("Select ALL");
//
//        }
//    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (chckbxSelectAll.isSelected()) {

            for (int i = 0; i < table.getRowCount(); i++) {
                table.getModel().setValueAt(true, i, 5);
            }

        } else {

            for (int i = 0; i < table.getRowCount(); i++) {
                table.getModel().setValueAt(false, i, 5);
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
