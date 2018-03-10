package jdbcTuts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jdbcTuts.tables.students;

/**
 *
 * @author kpadhikari
 */

/*
 * https://www.youtube.com/watch?v=379qkZTibZA
 * We need to download mysql-connector-java-5.1.45.zip from 
 *   https://dev.mysql.com/downloads/file/?id=474258, unpack it
 * and then add the jar file mysql-connector-java-5.1.45-bin.jar 
 * to the Libraries of the project (in NetBeans)
 *
 * We can check java documentations, we can go to
 *   https://docs.oracle.com/javase/7/docs/api/, https://docs.oracle.com/javase/8/docs/api/
 *   or https://docs.oracle.com/javase/9/docs/api/ for Java 7/8/9 documentations.
 */
public class jdbcTest2 {

    //private static final String SQL = "SELECT Id firstname class FROM students WHERE class <= ?";
    private static final String SQL = "SELECT * FROM students WHERE class <= ?";
    public static void main(String[] args) throws SQLException {

        double maxColVal;
        
        try {
            maxColVal = Input.getInt("Enter a maximum value of class:");
            
        } catch (Exception e) {
            System.err.println("Error: Invalid number");
            return;
        }
        
        ResultSet rs = null;
        try {
            Connection conn = DbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL, 
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setDouble(1, maxColVal);
           
            rs = stmt.executeQuery();
            students.getStudents(rs);
        } catch (SQLException e) {
            System.err.println(e);
        }finally {
            if(rs != null) {
                rs.close();
            }
        }
    }
}
