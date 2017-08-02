/*
 * http://code.runnable.com/Up1SgHW053gvAAFi/how-to-use-dictionary-in-java-for-collections
 */
/*
https://docs.oracle.com/javase/7/docs/api/java/util/Dictionary.html
public abstract class Dictionary<K,V>
extends Object

The Dictionary class is the abstract parent of any class, such as Hashtable, which maps 
keys to values. Every key and every value is an object. In any one Dictionary object, 
every key is associated with at most one value. Given a Dictionary and a key, the 
associated element can be looked up. Any non-null object can be used as a key and as a value.

As a rule, the equals method should be used by implementations of this class to decide 
if two keys are the same.

NOTE: This class is obsolete. New implementations should implement the Map interface, 
rather than extending this class.

https://www.tutorialspoint.com/java/java_dictionary_class.htm
Dictionary is an abstract class that represents a key/value storage repository and 
operates much like Map.

Given a key and value, you can store the value in a Dictionary object. Once the value 
is stored, you can retrieve it by using its key. Thus, like a map, a dictionary can be 
thought of as a list of key/value pairs.

*/

package swing.components;

/*
Dictionary implements a key value pair kind of collection
where the key is unique
*/
import java.util.*;
public class DictionaryDemo {
  static String newLine = System.getProperty("line.separator");
  public static void main(String[] args) {
  
    System.out.println(newLine + "Dictionary in Java" + newLine);
    System.out.println("-----------------------" + newLine);
    System.out.println("Adding items to the Dictionary" + newLine);
    //Creating dictionary object
    //dictionary can be created using HashTable object
    //as dictionary is an abstract class
    Dictionary dict = new Hashtable();
    
    //you add elements to dictionary using put method
    //put(key, value)
    dict.put(1, "Java");
    dict.put(2, ".NET");
    dict.put(3, "Javascript");
    dict.put(4, "HTML");
    
    System.out.println(newLine + "Items in the dictionary..." + dict + newLine);
    System.out.println("-----------------------" + newLine);
    
    //elements can be retrieved using their key
    System.out.println("Retrieve element from dictionary with key 1 : " + 
    dict.get(1) + newLine);
    System.out.println("-----------------------" + newLine);
    
    //elements can be removed using their key
    System.out.println("Removing element from dictionary with key 2 : " + 
    dict.remove(2) + newLine);
    
    System.out.println("Items in the dictionary after removing..." + dict + newLine);
    System.out.println("-----------------------" + newLine);
  }
}