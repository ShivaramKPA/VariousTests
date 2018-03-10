package jdbcTuts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kpadhikari
 */
public class DbConnection {
    //We need 'username', 'password' and the 'link' to the database
    // to be able to connect & access the databases.
    
    private static final String USERNAME = "adhikarikp2";
    private static final String PASSWORD = "adhikarikp2";
    private static final String CONN = "jdbc:mysql://localhost/school";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONN, USERNAME, PASSWORD);
    }
}
