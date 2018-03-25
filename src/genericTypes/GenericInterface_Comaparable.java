/*
https://www.journaldev.com/1663/java-generics-example-method-class-interface
Java Generic Interface

Comparable interface is a great example of Generics in interfaces and it’s written as:

package java.lang;
import java.util.*;

public interface Comparable<T> {
    public int compareTo(T o);
}

In similar way, we can create generic interfaces in java. We can also have multiple 
type parameters as in Map interface. Again we can provide parameterized value to a 
parameterized type also, for example new HashMap<String, List<String>>(); is valid.
 */
package genericTypes;

/**
 *
 * @author kpadhikari
 */
public class GenericInterface_Comaparable {
    
}
