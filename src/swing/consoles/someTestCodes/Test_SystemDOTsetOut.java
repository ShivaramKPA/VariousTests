/*
 * Code from: https://www.tutorialspoint.com/java/lang/system_setout.htm
*  The java.lang.System.setOut() method reassigns the "standard" output stream.
*  Declaration:      public static void setOut(PrintStream out)
*  Parameters:       out − This is the standard output stream.
*  Return Value:     This method does not return any value.
*  Exception:        SecurityException − if a security manager exists and its 
*                    checkPermission method doesn't allow reassigning of the 
*                    standard output stream.
 */
package swing.consoles.someTestCodes;

/**
 *
 * @author kpadhikari
 */

import java.lang.*;
import java.io.*;

public class Test_SystemDOTsetOut {

   public static void main(String[] args) throws Exception {
    
      // create file in the main directory of the project (not inside the package)
      FileOutputStream f = new FileOutputStream("trash.txt");

      //System.setOut(new PrintStream(f)); //works
      System.setOut(new PrintStream(f, true));//works

      // this text will get redirected to above file (not to the standard console)
      System.out.println("This text is printed by System.out.println(String)\n" 
//        + "method by redirecting the text to this file using\n"
        + "System.setOut(new PrintStream(f, true)) method");
   }    
}
