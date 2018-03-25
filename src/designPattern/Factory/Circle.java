/*
 *https://www.tutorialspoint.com/design_pattern/factory_pattern.htm
 */
package designPattern.Factory;

public class Circle implements Shape {

   @Override
   public void draw() {
      System.out.println("Inside Circle::draw() method.");
   }
}
