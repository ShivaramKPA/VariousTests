/*
 * https://docs.oracle.com/javase/tutorial/java/generics/types.html
*  https://www.journaldev.com/1663/java-generics-example-method-class-interface
 * Notice that while using this class, we have to use type casting and it can produce 
*   ClassCastException at runtime.

 */
package genericTypes;

/**
 *
 * @author kpadhikari
 */
public class NonGenericBox {
    private Object object;

    public void set(Object object) { this.object = object; }
    public Object get() { return object; }

    public static void main(String[] args) {
        Integer other = new Integer(5);
        NonGenericBox box = new NonGenericBox();
        box.set(new Integer(3));
        System.out.println("box.get() = " + box.get());
        System.out.println("3*box.get() = " + other*(Integer)box.get()); //Gave error when multiplied by 3 or other

        NonGenericBox box1 = new NonGenericBox();
        box1.set(new Double(4.0));    
        System.out.println("box1.get() = " + box1.get());
    }
}
