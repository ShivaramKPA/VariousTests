/*
https://www.journaldev.com/1663/java-generics-example-method-class-interface
Notice that while using the class NonGnericsTypeUsingObject or NonGenericBox, 
we have to use type casting and it can 
produce ClassCastException at runtime. Now we will use java generic class to 
rewrite the same class as shown below.


Notice the use of GenericsType_Class class in the main method. We don’t need to do 
type-casting and we can remove ClassCastException at runtime. If we don’t provide 
the type at the time of creation, compiler will produce a warning that “GenericsType_Class 
is a raw type. References to generic type GenericsType_Class<T> should be parameterized”. 
When we don’t provide type, the type becomes Object and hence it’s allowing both 
String and Integer objects but we should always try to avoid this because we will 
have to use type casting while working on raw type that can produce runtime errors.

Tip: We can use @SuppressWarnings("rawtypes") annotation to suppress the compiler warning

Also notice that it supports java autoboxing.
 */
package genericTypes;

public class GenericsType_Class<T> {

	private T t;
	
	public T get(){
		return this.t;
	}
	
	public void set(T t1){
		this.t=t1;
	}
	
	public static void main(String args[]){
		GenericsType_Class<String> type = new GenericsType_Class<>();
		type.set("Pankaj"); //valid
		
		GenericsType_Class type1 = new GenericsType_Class(); //raw type
		type1.set("Pankaj"); //valid
		type1.set(10); //valid and autoboxing support
	}
}
