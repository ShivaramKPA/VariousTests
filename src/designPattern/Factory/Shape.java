package designPattern.Factory;

/*
 * https://www.tutorialspoint.com/design_pattern/factory_pattern.htm

Factory pattern is one of the most used design patterns in Java. This type of design 
pattern comes under creational pattern as this pattern provides one of the best ways 
to create an object.

In Factory pattern, we create object without exposing the creation logic to the client 
and refer to newly created object using a common interface.

Implementation

We're going to create a Shape interface and concrete classes implementing the Shape 
interface. A factory class ShapeFactory is defined as a next step.

FactoryPatternDemo, our demo class will use ShapeFactory to get a Shape object. It 
will pass information (CIRCLE / RECTANGLE / SQUARE) to ShapeFactory to get the type 
of object it needs.

* Step 1: Create an interface (see Shape.java)
* Step 2: Create concrete classes implementing the same interface
*         See Rectangle.java, Square.java, Circle.java
* Step 3: Create a Factory to generate object of concrete class based on given information.
*         See ShapeFactory.java
* Step 4: Use the Factory to get object of concrete class by passing an information such as type.
*         See FactoryPatternDemo.java
* Step 5: Verify the output running the program.
 */

/*
 *
 * kp: Any interface that has a single method in it is also known as 'functional' interface
 *     because, we can use it in Lambda expressions (which belongs to 'functional' programming)
 */
public interface Shape {
   void draw();
}
