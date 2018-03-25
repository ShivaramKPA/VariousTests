/*
 * https://www.tutorialspoint.com/design_pattern/abstract_factory_pattern.htm.
 */
package designPattern.Factory;

public class FactoryProducer {
   public static AbstractFactory getFactory(String choice){
   
      if(choice.equalsIgnoreCase("SHAPE")){
         return new ShapeFactory();
         
      }else if(choice.equalsIgnoreCase("COLOR")){
         return new ColorFactory();
      }
      
      return null;
   }
}
