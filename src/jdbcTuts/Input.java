
package jdbcTuts;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author kpadhikari
 */
public class Input {
    public static String getInput(String value) {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(value);
        System.out.flush();
        
        try {
            return buffer.readLine();
        } catch (Exception e) {
            return "Error";
        }
    }
    
    public static int getInt(String value) throws NumberFormatException {
        String in = getInput(value);
        return Integer.parseInt(in);
    }
}
