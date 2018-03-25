/*
 * https://docs.oracle.com/javase/tutorial/java/generics/types.html
 */
package genericTypes;

/**
 * Generic version of the NonGenericBox class.
 *
 * @param <T> the type of the value being boxed
 */
public class GenericBox<T> {

    // T stands for "Type"
    private T t;

    public void set(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

    public static void main(String[] args) {
        Integer other = new Integer(5);
        GenericBox box = new GenericBox();
        box.set(new Integer(3));
        System.out.println("box.get() = " + box.get());
        //System.out.println("3*box.get() = " + 3*box.get()); //Gave error when multiplied by 3 or other
    }
}
