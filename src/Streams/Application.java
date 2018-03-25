/*
 * Prepared while going through https://www.youtube.com/watch?v=L6U6JBC74sA
* which is in the World of Zero channel

Java 8 brought a number of new features to Java, one of those features was the 
Streams API. This API brought an easy way to create functional style list manipulation 
elements using a simple API. This is extremely powerful and allows you to create 
extremely complex interactions with relatively little code. 

For those familiar with C#, 
the Java Streams API offers a core feature set very similar to what you can do with 
LINQ. Certain syntactic limitations exist as well as a lack of extension methods to 
easily expand the system. Java solves these differences in other ways. 

The java Stream javadocs are here: https://docs.oracle.com/javase/8/docs... Curious 
about the Reactive Extensions and how they're different? Check out the video on 
building Fizz Buzz using JavaRX: https://youtu.be/ZTqIg-Fk2n4 
Discord: https://discord.gg/hU5Kq2u
 */

package Streams;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author kpadhikari
 */
public class Application {
    //public static void main(String[] args) {
    public static void main(String... args) { //kp: Use of ellipsis for varargs
        Stream.of(1,2,3,4,5,6,7,8,9,10).filter(value -> value >= 3).forEach(value -> System.out.println(value));
        //Stream.of(10...30).filter(value -> value >= 3).forEach(value -> System.out.println(value));
        System.out.println("Next, using map()");
        Stream.of(1,2,3,4,5).filter(value -> value >= 3).map(value -> value*value).forEach(value -> System.out.println(value));
        System.out.println("Now, using the int-array for the input to the Arrays.stream() (can't do so with Stream()):");
        int[] numbers = new int[] {1, 2, 3,4, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 0};
        Arrays.stream(numbers).filter(value -> value >=30).map(value -> value*value).forEach(value -> System.out.println(value));
        
        int sum = Arrays.stream(numbers).filter(value -> value >=30).map(value -> value*value).sum();
        System.out.println("Sum = " + sum);
        Arrays.stream(numbers).filter(number -> number % 2 == 0).sorted().forEach(value -> System.out.println(value));
        
        System.out.println("Now initializing an array with some non-zero values (by default, the elements have zero values):");
        int [] negativeInts = new int[10]; //by default, initialized to zero values
        
        //generate(() -> -1) creates an infinite list of negative ones.
        // limit(10) is doing the pagination - choosing/taking 10 from the front of the infinite list of '-1's.
        Stream.generate(() -> -1).limit(10).toArray();//Creating an infinite list of 
    }
}
