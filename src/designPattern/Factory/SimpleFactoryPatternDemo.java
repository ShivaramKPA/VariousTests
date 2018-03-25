/*
 * https://www.tutorialspoint.com/design_pattern/factory_pattern.htm
 */
package designPattern.Factory;



public class SimpleFactoryPatternDemo {

   public static void main(String[] args) {
      ShapeFactory4SimpleExample shapeFactory = new ShapeFactory4SimpleExample();

      //get an object of Circle and call its draw method.
      Shape shape1 = shapeFactory.getShape("CIRCLE");

      //call draw method of Circle
      shape1.draw();

      //get an object of Rectangle and call its draw method.
      Shape shape2 = shapeFactory.getShape("RECTANGLE");

      //call draw method of Rectangle
      shape2.draw();

      //get an object of Square and call its draw method.
      Shape shape3 = shapeFactory.getShape("SQUARE");

      //call draw method of circle
      shape3.draw();
   }
}
