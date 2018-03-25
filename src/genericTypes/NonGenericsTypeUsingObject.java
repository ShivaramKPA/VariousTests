/*
*  https://www.journaldev.com/1663/java-generics-example-method-class-interface
*   Notice that while using this class, we have to use type casting and it can 
produce ClassCastException at runtime. Now we will use java generic class to 
rewrite the same class as shown below. (see GenericsTypeExample)
 */
package genericTypes;

public class NonGenericsTypeUsingObject {

	private Object t;

	public Object get() {
		return t;
	}

	public void set(Object t) {
		this.t = t;
	}

        public static void main(String args[]){
		NonGenericsTypeUsingObject type = new NonGenericsTypeUsingObject();
		type.set("Pankaj"); 
		String str = (String) type.get(); //type casting, error prone and can cause ClassCastException
                System.out.println(str);
                type.set(2);
                System.out.println(((Integer)2)*(Integer)type.get());
	}
}
