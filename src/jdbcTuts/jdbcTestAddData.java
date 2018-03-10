
package jdbcTuts;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author kpadhikari
 */
public class jdbcTestAddData {
    
 public static void main(String[] args) throws SQLException {
        try (
                Connection con = DbConnection.getConnection(); //DbConnection defined in DbConnection.java.
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery("SELECT * FROM students");
                //ResultSet rs = stmt.executeQuery();
                ) 
        {
            while (rs.next()) {
                System.out.println(rs.getString("Id") + " " + rs.getString("first_name")
                     + " " + rs.getString("last_name") +  " " + rs.getString("dob"));
            }
            
        } catch (SQLException ex) {
            //Logger.getLogger(jdbcTest.class.getName()).log(Level.SEVERE, null, ex);
            //System.err.println(ex);
            ex.printStackTrace();
        }
        
        
        Scanner input = new Scanner(System.in);
        
        
        System.out.println("\n Do you want to add Student");
        String ans = input.nextLine();
        
        if(ans.equalsIgnoreCase("yes")) {
            System.out.println("Enter Id: ");
            String Id = input.nextLine();
            System.out.println("Enter First Name: ");
            String FName = input.nextLine();   
            System.out.println("Enter Last Name: ");
            String LName = input.nextLine();   
            System.out.println("Enter DOB: ");
            String Dob = input.nextLine();
            System.out.println("Enter class: ");
            String CLass = input.nextLine();             
            System.out.println("Enter email: ");
            String Email = input.nextLine();  
            
            addStudents create = new addStudents();
            create.add(Id, FName, LName, Dob, CLass, Email);
        } else {
           System.exit(1);
        }
    }    
}
