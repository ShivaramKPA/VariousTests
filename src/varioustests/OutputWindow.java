/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varioustests;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;


class OutputWindow implements ActionListener{
	JTextArea area;
	JFrame frame;
	boolean terminaL;
	OutputStream os;
	PrintStream ps;
	PrintStream Ps;
	JButton x,y;
	int k=0;
   
	OutputWindow(){	
	 javax.swing.SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			createAndShowGui();
		}
	 	}   );
	}
	
	void createAndShowGui() {
	//Creating a testarea.
		area=new JTextArea();
		area.setFont(new Font("Times New Roman",Font.PLAIN, 12));
		area.setEditable(false);
		JScrollPane pane=new JScrollPane(area);
		pane.setPreferredSize(new Dimension(550,391));
	   
		//Saving the old System.
		Ps=System.out;
		//Creating an output stream to the text area.
		os=new TextAreaOutputStream(area);
		ps=new PrintStream(os);
	   
		//Creating a frame.
		frame=new JFrame("Text Output");
		frame.setLayout(new GridBagLayout());
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	   
		//Creating two buttons.
		x=new JButton("Change Output");
		y=new JButton("Start");
		x.addActionListener(this);
		y.addActionListener(this);
	   
		//adding ecerything to the frame.
		GridBagConstraints c=new GridBagConstraints();
		c.weightx=1;
		c.weighty=1;
		c.fill=GridBagConstraints.BOTH;
		c.gridheight=6;
		c.gridwidth=2;
		c.gridx=1;
		c.gridy=1;
		frame.add(pane,c);
		c.gridheight=1;
		c.gridwidth=1;
		c.gridx=1;
		c.gridy=7;
		frame.add(x,c);
		c.gridx=2;
		frame.add(y,c);
		frame.pack();
		System.setOut(ps);
	}
   
	//Changed outbut back and forth inbetween output window and terminal.
	//Called by the Change Output button.
	void setOutput(){
		if(k%2==0)
			System.setOut(Ps);
		else
			System.setOut(ps);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//The Change Output button.
		if(e.getSource()==x){
			System.out.println("Output Changed");
			setOutput();
			k++;
			System.out.println("Output Changed");
		}
	   
		//The Start button.
		if(e.getSource()==y){
	   SwingWorker sw = new SwingWorker() {
		public Object doInBackground() {
			  try{
				  String [] cmd = {"java","OutputTesters"};
				  Process proc = new ProcessBuilder(cmd).start();
				InputStream out = proc.getInputStream();
				InputStreamReader isr = new InputStreamReader(out);
				BufferedReader br = new BufferedReader(isr,100);
				String line = null;
				System.out.println("\n<OUTPUT>");
				while ( (line = br.readLine()) != null){
					System.out.println(line);
				}
				System.out.println("</OUTPUT>\n");
			}catch(Exception e1){} 
		return null;}};
		sw.execute();
		}
	   
	}
	
	public static void main(String[] args){
		 new OutputWindow();
	}	
}
