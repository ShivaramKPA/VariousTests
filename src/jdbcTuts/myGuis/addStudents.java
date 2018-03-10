
package jdbcTuts.myGuis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

/**
 *
 * @author kpadhikari
 */
public class addStudents {
    
    //a constructor
    public addStudents() {
        
    }
    
    public void add(String id, String fname, String lname, 
            String dob, String Class, String email) {
        
        String sql = "INSERT INTO students (id, first_name, last_name, dob, class, email) VALUES (?, ?, ?, ?, ?, ?)";
        
        //try-catch with resources
        try ( Connection conn = DbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
                ){
            stmt.setString(1, id);//1 is the placeholder of id
            stmt.setString(2, fname);
            stmt.setString(3, lname);            
            stmt.setString(4, dob);
            stmt.setString(5, Class);
            stmt.setString(6, email);
            
            stmt.execute();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
