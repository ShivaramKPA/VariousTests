/*
 * https://www.tutorialspoint.com/design_pattern/abstract_factory_pattern.htm
 */
package designPattern.Factory;

public abstract class AbstractFactory {
   abstract Color getColor(String color);
   abstract Shape getShape(String shape) ;
}
