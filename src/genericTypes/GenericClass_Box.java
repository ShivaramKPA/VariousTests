/*
 https://www.tutorialspoint.com/java/java_generics.htm
Generic Classes

A generic class declaration looks like a non-generic class declaration, except 
that the class name is followed by a type parameter section.

As with generic methods, the type parameter section of a generic class can have 
one or more type parameters separated by commas. These classes are known as 
parameterized classes or parameterized types because they accept one or more parameters.

Example

Following example illustrates how we can define a generic class −
 */
package genericTypes;

public class GenericClass_Box<T> {
   private T t;

   public void add(T t) { //kp: Setter
      this.t = t;
   }

   public T get() {  //kp: Getter
      return t;
   }

   public static void main(String[] args) {
      GenericClass_Box<Integer> integerBox = new GenericClass_Box<Integer>();
      GenericClass_Box<String> stringBox = new GenericClass_Box<String>();
    
      integerBox.add(new Integer(10));
      stringBox.add(new String("Hello World"));

      System.out.printf("Integer Value :%d\n\n", integerBox.get());
      System.out.printf("String Value :%s\n", stringBox.get());
   }
}
