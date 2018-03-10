/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.lambdaexp;

/**
 *
 * @author kpadhikari
 *  Copied from https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html#lambda-expressions-in-gui-applications
 *  https://en.wikipedia.org/wiki/Lambda_expression
 *  Lambda expression in computer programming, also called anonymous function, 
 *   a function (or a subroutine) defined, and possibly called, without being 
 *   bound to an identifier
 */

public class LambdaExpDemoCalculator {
//public class Calculator {
  
    interface IntegerMath {
        int operation(int a, int b);   
    }
  
    public int operateBinary(int a, int b, IntegerMath op) {
        return op.operation(a, b);
    }
 
    public static void main(String... args) {
    
        LambdaExpDemoCalculator myApp = new LambdaExpDemoCalculator();
        
        //In the following addition & subtraction are objects of two different
        //   classes (not named, therefore, anonymous) which implement the 
        //   common interface 'IntegerMath', and
        // the following two lines simultaneously does two jobs - 
        //      (1) defines or implements the corresponding anonymous classes
        //      (2) instantiates those classes to create the objects - addition 
        //           and subtraction.
        IntegerMath addition = (a, b) -> a + b;
        IntegerMath subtraction = (a, b) -> a - b;
        System.out.println("40 + 2 = " +
            myApp.operateBinary(40, 2, addition));
        System.out.println("20 - 10 = " +
            myApp.operateBinary(20, 10, subtraction));    
    }
}

