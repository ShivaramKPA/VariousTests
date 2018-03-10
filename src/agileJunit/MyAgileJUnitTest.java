package agileJunit;

/**
 *
 * @author kpadhikari
 */

/*
*  Ref: https://www.youtube.com/watch?v=Q0ue-T0Z6Zs (by Frank Coyle, calls himself Dr. C?)
*        Junit is a uni-testing tool
*        Supports Test-Driven-Development - also known as Agile Programming
*        See: www.junit.org
*  Netbeans has built-in capability for running JUnit, that we're gonna 
*      find very attractive 
*
*  Go to Tools menu and select 'Create/Update Tests' or something similar if
*     you have different version of NetBeans (I am using 8.2 at the moment)
*  That will create a new file with another 'Test' added to the name of this file
*     i.e. MyAgileJUnitTestTest.java in a new package of the same name 'agileJunit'
*     but inside the folder 'Test Packages'.
*/
public class MyAgileJUnitTest {
    public int add(String s1, String s2) {
        int int1 = Integer.parseInt(s1);//Initially it was s2 as well (for testAdd2)
        int int2 = Integer.parseInt(s2);
        return int1 + int2;
    }
}
