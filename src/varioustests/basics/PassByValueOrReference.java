/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varioustests.basics;

/**
 *
 * @author https://www.youtube.com/watch?v=hNR6fsksEu8
 *      8.6: Pass by Value vs. Pass by Reference - Processing Tutorial
 */
public class PassByValueOrReference {
    
    public static void main(String[] args) {
        int x = 10;
        Integer y = 10;
        Integer z = new Integer(10);
        System.out.println("x="+x+", y="+y + ", z=" + z);
        PassByValueOrReference obj = new PassByValueOrReference();
        obj.change(x);
        obj.change(y);
        obj.change(z);
        System.out.println("int x=10; After obj.change(x), x = " + x);
        System.out.println("int y=10; After obj.change(y), y = " + y);
        System.out.println("int z=10; After obj.change(z), z = " + z);
        
        change(x);
        change(y);
        change(z);
        System.out.println("int x=10; After change(x), x = " + x);
        System.out.println("int y=10; After change(y), y = " + y);
        System.out.println("int z=10; After change(z), z = " + z);        
    }
    
    public static void change(int number) { //Arg is a primitive data type 'int'
        number = number + 20;
        System.out.println("Inside change(int): result=" + number);
    }
    
    //I was expecting the following function to work as 'pass-by-reference' example
    //  so the value of the variables y and z changed to 30, but it seems I slipped somewhere.
    public static void change(Integer number) { //Arg is an object data type 'Integer'
        Integer addant = new Integer(20);
        number = number + addant;
        System.out.println("Inside change(Integer): result=" + number); 
    }
    
}
