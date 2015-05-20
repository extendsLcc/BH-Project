package br.tst.customFields;

import javax.swing.JTextArea;

public class CustomTextArea extends JTextArea {

	private String id = "";
	
	public CustomTextArea( String s ){
		
		super();
		id = s;
		
	}
	
	public void setId( String s ){
		
		id = s;
		
	}
	
	public String getId(){
		
		return id;
		
	}
	
}
