//https://www.youtube.com/watch?v=joWldbcp1So
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varioustests;

import java.util.Scanner;

/**
 *
 * @author https://www.youtube.com/watch?v=joWldbcp1So
 */
public class debuggerTest_ArrayDemo {
    //kp: I defined the getValue function below before typing in the following comments
    //    when I typed /** and then hit the 'Enter' key, the following three stars and the 
    //    last/closing slash were autmatically generated. Also, '@param array' was also 
    //    generated right after the second-to-last star. Other texts, I added/typed them later.
    /**
     * read integers from the keyboard into the array
     * @param array array to be filled
     */
    public static void getValues(int[] array) {
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < array.length; i++) {
            System.out.println("Please enter a number: ");
            array[i] = scan.nextInt();
        }
    }
    
    /**
     * return the largest value in the array
     * @param array numbers to search
     * @return largest value
     */
    public static int findMax(int[] array) {
        int max = array[0];
        for (int i = 0; i < array.length; i++) {
            if(array[i] > max) {
                max = array[i];
            }            
        }
        return max;
    }
    
    public static void main(String[] args) {
        int[] myArray = new int[5];
        debuggerTest_ArrayDemo.getValues(myArray);
        System.out.println("The largest value is: " + findMax(myArray));
    }
    
}
