/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varioustests;

import java.io.*;
import javax.swing.JTextArea;
 
	class TextAreaOutputStream extends OutputStream {

	private final JTextArea textArea;
	private final StringBuilder sb = new StringBuilder();

	TextAreaOutputStream(final JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void flush(){
		System.out.println("FLUSHED!");
	}
   
	@Override
	public void close(){ }

	@Override
	public void write(int b) throws IOException {

		
	
	updateTextArea(String.valueOf((char)b));
		
	   
		
	}

private void updateTextArea(final String text) {  
   javax.swing.SwingUtilities.invokeLater(new Runnable() {  
	 public void run() {  
	   textArea.append(text);  
	 }  
   });  
}
}

