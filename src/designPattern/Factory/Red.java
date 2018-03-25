/*
 * https://www.tutorialspoint.com/design_pattern/abstract_factory_pattern.htm
 */
package designPattern.Factory;

public class Red implements Color {

   @Override
   public void fill() {
      System.out.println("Inside Red::fill() method.");
   }
}
