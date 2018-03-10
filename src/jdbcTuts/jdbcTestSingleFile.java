package jdbcTuts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author kpadhikari
 */

/*
 *  This is a standalone program that was created first. Later it was
 * broken up into multiple files such as jdbcTest.java, DbConnection.java etc
 *       - by defining, for example, a separate class DbConnection 
 *         (in DbConnection.java) for database connection and so on.
 *
 * 
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
public class jdbcTestSingleFile {

    //We need 'username', 'password' and the 'link' to the database
    // to be able to connect & access the databases.
    
    private static final String USERNAME = "adhikarikp2";
    private static final String PASSWORD = "adhikarikp2";
    private static final String CONN = "jdbc:mysql://localhost/school";
    
    public static void main(String[] args) throws SQLException {
        
        //Following line will let you import the driver file into the memory
        //  for old jdks below 1.8 (or was it below 1.6?) see at about 14 min point of video)
        //Class.forName(com.mysql.jdbc.Driver);
        
        Connection con = null;
        
        //Needed for reading and modifying data in the database
        Statement stmt = null;
        ResultSet rs = null;
        
        //Below try-catch important because every connection we open,
        //  we need to close that as well. It helps in portability
        try {
            con = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
            System.out.println("Connected");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("SELECT * FROM students");
            rs.last(); //Move the cursor to the last of the results-set
            System.out.println("We have: " + rs.getRow() + " rows in the table.");
        } catch (SQLException ex) {
            //Logger.getLogger(jdbcTestSingleFile.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
        }
        finally {
            //Closing the following jdbc resources is a good practice
            // But, close them in proper order. First opened will be closed last & so on.
            if(rs != null) {
                rs.close();
            }
            if( stmt != null) {
                stmt.close();
            }
            if( con != null) {
                con.close();
            }            
        }
    }
}
