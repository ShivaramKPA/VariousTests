/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coatjava.groottests;

/**
 *
 * @author kpadhikari
 */
public class PrintVarCLAS12DIR {
	public static void main(String[] arg) {
		String COATJAVA = System.getenv("CLAS12DIR");
		System.out.println("JAVA_HOME = " + System.getenv("JAVA_HOME"));
		System.out.println("CLAS12DIR = " + System.getenv("CLAS12DIR"));
	}    
}
