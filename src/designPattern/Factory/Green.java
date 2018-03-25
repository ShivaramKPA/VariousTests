/*
 * https://www.tutorialspoint.com/design_pattern/abstract_factory_pattern.htm
 */
package designPattern.Factory;

public class Green implements Color {

   @Override
   public void fill() {
      System.out.println("Inside Green::fill() method.");
   }
}
