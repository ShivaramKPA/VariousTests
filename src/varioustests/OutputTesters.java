/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varioustests;

class OutputTesters {
	public static void main (String[] args){
		for(int j=1;j<5;j++){
			try{
				  Thread.currentThread();
				  Thread.sleep(1000);
				  System.out.println("Done.");
				}
			catch(InterruptedException ie){ }
		}
	}
}

