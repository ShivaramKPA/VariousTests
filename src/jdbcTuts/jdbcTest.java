package jdbcTuts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class jdbcTest {

    //private static final String SQL = "SELECT Id firstname class FROM students WHERE class <= ?";
    public static void main(String[] args) throws SQLException {

        //Following line will let you import the driver file into the memory
        //  for old jdks below 1.8 (or was it below 1.6?) see at about 14 min point of video)
//        //Class.forName(com.mysql.jdbc.Driver);
//        Connection con = null;
//
//        //Needed for reading and modifying data in the database
//        Statement stmt = null;
//        ResultSet rs = null;
//
//        //Below try-catch important because every connection we open,
//        //  we need to close that as well. It helps in portability
//        try {
//            //con = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
//            con = DbConnection.getConnection(); //DbConnection defined in DbConnection.java.
//            System.out.println("Connected");
//            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            rs = stmt.executeQuery("SELECT * FROM students");
//            rs.last(); //Move the cursor to the last of the results-set
//            System.out.println("We have: " + rs.getRow() + " rows in the table.");
//        } catch (SQLException ex) {
//            //Logger.getLogger(jdbcTest.class.getName()).log(Level.SEVERE, null, ex);
//            System.err.println(ex);
//        } finally {
//            //Closing the following jdbc resources is a good practice
//            // But, close them in proper order. First opened will be closed last & so on.
//            if (rs != null) {
//                rs.close();
//            }
//            if (stmt != null) {
//                stmt.close();
//            }
//            if (con != null) {
//                con.close();
//            }
//        }

        //Following block is the try-with-resources alternative to above block.
        //   This makes it unnecessary to use 'finally' block.
        //Ref: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        //     and around 35:00 min of the video https://www.youtube.com/watch?v=379qkZTibZA
        // 
        // The try-with-resources statement is a try statement that declares one or 
        // more resources. A resource is an object that must be closed after the 
        // program is finished with it. The try-with-resources statement ensures that 
        //  each resource is closed at the end of the statement. Any object that 
        //  implements java.lang.AutoCloseable, which includes all objects which implement 
        //  java.io.Closeable, can be used as a resource.
        //
        //Below try-catch important because every connection we open,
        //  we need to close that as well. It helps in portability
        try (
                Connection con = DbConnection.getConnection(); //DbConnection defined in DbConnection.java.
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery("SELECT * FROM students");
                //ResultSet rs = stmt.executeQuery();
                ) 
        {
//            System.out.println("Connected");
//            rs.last(); //Move the cursor to the last of the results-set
//            System.out.println("We have: " + rs.getRow() + " rows in the table.");
            students.getStudents(rs);
                        
            rs.last();//Reading the last row
            System.out.println("Last Student is: " + rs.getInt("Id") + " " 
                    + rs.getString("first_name") + " " + rs.getString("last_name"));
                        
            rs.first();//Reading the last row
            System.out.println("First Student is: " + rs.getInt("Id") + " " 
                    + rs.getString("first_name") + " " + rs.getString("last_name"));            
            //See http://localhost/phpMyAdmin/sql.php?db=school&table=students&pos=0
                        
            rs.absolute(3);//Reading the Nth student
            System.out.println("3rd Student is: " + rs.getInt("Id") + " " 
                    + rs.getString("first_name") + " " + rs.getString("last_name"));
        } catch (SQLException ex) {
            //Logger.getLogger(jdbcTest.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
        }
    }
}
