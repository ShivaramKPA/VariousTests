/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//http://alvinalexander.com/java/edu/pj/pj010016
package varioustests;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaRunCommand {

    public static void main(String args[]) {

        //String s = null;

        try {
            
	    // run the Unix "ps -ef" command
            // using the Runtime exec method:
            Process p1 = Runtime.getRuntime().exec("ps -ef");
            System.out.println("kp: ========================= Now ls command =====");
            Process p2 = Runtime.getRuntime().exec("ls");
            System.out.println("kp: ========================= Now pwd command =====");
            Process p3 = Runtime.getRuntime().exec("pwd");
            Process p4 = Runtime.getRuntime().exec("./src/files/justEchoHello.sh");
            
            PrintOutput(p1);
            PrintOutput(p2);
            PrintOutput(p3);
            PrintOutput(p4);
            
            System.exit(0);
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }  
    }
    
    private static void PrintOutput(Process p) {
        String s = null;
        try {
            BufferedReader stdInput = new BufferedReader(new
                         InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new
                         InputStreamReader(p.getErrorStream()));
            
            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException ex) {
            Logger.getLogger(JavaRunCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
