package br.tst.customFields;

import javax.swing.JTextField;

public class CustomTextField extends JTextField {

	private String id = "";
	
	public CustomTextField( String s ){
		
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
