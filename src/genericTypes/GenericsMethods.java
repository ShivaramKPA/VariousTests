/*
https://www.journaldev.com/1663/java-generics-example-method-class-interface
Java Generic Method

Sometimes we don’t want whole class to be parameterized, in that case we can create 
java generics method. Since constructor is a special kind of method, we can use 
generics type in constructors too.

Here is a class showing example of java generic method.
 */
package genericTypes;

public class GenericsMethods {

	//Java Generic Method
	public static <T> boolean isEqual(GenericsType<T> g1, GenericsType<T> g2){
		return g1.get().equals(g2.get());
	}
	
	public static void main(String args[]){
		GenericsType<String> g1 = new GenericsType<>();
		g1.set("Pankaj");
		
		GenericsType<String> g2 = new GenericsType<>();
		g2.set("Pankaj");
		
		boolean isEqual = GenericsMethods.<String>isEqual(g1, g2);
		//above statement can be written simply as
		isEqual = GenericsMethods.isEqual(g1, g2);
		//This feature, known as type inference, allows you to invoke a generic method as an ordinary method, without specifying a type between angle brackets.
		//Compiler will infer the type that is needed
	}
}    
}
