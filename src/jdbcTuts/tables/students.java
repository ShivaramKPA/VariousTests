package jdbcTuts.tables;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author kpadhikari
 */
public class students {
    public static void getStudents(ResultSet rs) throws SQLException {
        while (rs.next()) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("Students ID " + rs.getInt("Id")+" ");
            buffer.append(rs.getString("first_name")+" ");
            buffer.append(rs.getString("last_name")+" ");
            buffer.append(rs.getDate("dob"));
            System.out.println("kp: buffer=" + buffer);
            System.out.println("kp: buffer.toString()=" + buffer.toString());
        }
    }
}
