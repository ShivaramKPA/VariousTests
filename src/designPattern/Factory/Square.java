/*
 * https://www.tutorialspoint.com/design_pattern/factory_pattern.htm
 */
package designPattern.Factory;

public class Square implements Shape {

   @Override
   public void draw() {
      System.out.println("Inside Square::draw() method.");
   }
}
