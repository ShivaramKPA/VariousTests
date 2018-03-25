/*
 * https://www.tutorialspoint.com/design_pattern/factory_pattern.htm
 */
package designPattern.Factory;

public class ShapeFactory4SimpleExample {
	
   //use getShape method to get object of type shape 
   //@Override
   public Shape getShape(String shapeType){
      if(shapeType == null){
         return null;
      }		
      if(shapeType.equalsIgnoreCase("CIRCLE")){
         return new Circle();
         
      } else if(shapeType.equalsIgnoreCase("RECTANGLE")){
         return new Rectangle();
         
      } else if(shapeType.equalsIgnoreCase("SQUARE")){
         return new Square();
      }
      
      return null;
   }
}
