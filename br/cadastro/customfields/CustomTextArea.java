package br.cadastro.customfields;

import javax.swing.JTextArea;

public class CustomTextArea extends JTextArea implements CustomField {

	private String id = "";
	public Boolean enabled = true;
	
	public CustomTextArea( String s, int limit ){
		
		super();
		id = s;
		setDocument( new LengthRestrictedDocument( limit ) );
		
	}
	
	public void setId( String s ){
		
		id = s;
		
	}
	
	public String getId(){
		
		return id;
		
	}
	
	@Override
	public Object getValue() {
		
		return getText();
		
	}

	@Override
	public void setValue(Object o) {

		setText( (String) o );
		
	}

	public void toggle() {

		enabled = !enabled;
		
	}
	
	public boolean enabled() {
		
		return enabled;
		
	}
	
}
