/*
 * https://www.tutorialspoint.com/design_pattern/abstract_factory_pattern.htm
Abstract Factory patterns work around a super-factory which creates other factories. 
This factory is also called as factory of factories. This type of design pattern 
comes under creational pattern as this pattern provides one of the best ways to 
create an object.

In Abstract Factory pattern an interface is responsible for creating a factory of 
related objects without explicitly specifying their classes. Each generated factory 
can give the objects as per the Factory pattern.
Implementation

We are going to create a Shape and Color interfaces and concrete classes implementing 
these interfaces. We create an abstract factory class AbstractFactory as next step. 
Factory classes ShapeFactory and ColorFactory are defined where each factory extends 
AbstractFactory. A factory creator/generator class FactoryProducer is created.

AbstractFactoryPatternDemo, our demo class uses FactoryProducer to get a AbstractFactory 
object. It will pass information (CIRCLE / RECTANGLE / SQUARE for Shape) to AbstractFactory 
to get the type of object it needs. It also passes information (RED / GREEN / BLUE for 
Color) to AbstractFactory to get the type of object it needs.

//kp: Upto step 1 and 2 are common to the example for simple factory method demo i.e.,
//    FactoryPatternDemo.java

* Step 1: Create an interface for Shapes (see Shape.java)
* Step 2: Create concrete classes implementing the same interface
*         See Rectangle.java, Square.java, Circle.java
* Step 3: Create an interface for Colors (see Colors.java)
* Step 4: Create concrete classes implementing the same interface Colors.
*         See Red.java, Green.java, Blue.java
* Step 5: Create an Abstract class to get factories for Color and Shape Objects.
*         see AbstractFactory.java
* Step 6: Create Factory classes extending AbstractFactory to generate object of 
*         concrete class based on given information.
*         See ShapeFactory.java and ColorFactory.java
* Step 7: Create a Factory generator/producer class to get factories by passing an 
*         information such as Shape or Color
*         See  FactoryProducer.java
* Step 8: Use the FactoryProducer to get AbstractFactory in order to get factories 
*         of concrete classes by passing an information such as type.
*         See AbstractFactoryPatternDemo.java
* Step 9: Verify the output running the program.
 */
package designPattern.Factory;

/**
 *
 * @author kpadhikari
 */
public class ReadmeAbstractFactory {
    
}
