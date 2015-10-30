package br.cadastro.customfields;

import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class CustomTextField extends JFormattedTextField implements CustomField{

	private String id = "";
	public Boolean enabled = true;
	
	public CustomTextField( String s, int limit ){
		
		super();
		id = s;
		setDocument( new LengthRestrictedDocument( limit ) );
		
	}
	
	public CustomTextField( String s, int max, int decimal ){
		
		super();
		id = s;
		setDocument( new JTextFieldNumbFilter( max, decimal ) );
		
	}
	
	public CustomTextField( String s, String filter ) {
		
		super();
		id = s;
		
		try {
			
			MaskFormatter mask = new MaskFormatter( filter );
			mask.setPlaceholderCharacter( '_' );
			setFormatter( mask );
			setHorizontalAlignment(JTextField.CENTER);
			
		} catch (ParseException e) {}
		
		setFocusLostBehavior( PERSIST );
		
	}
	
	public void setId( String s ){
		
		id = s;
		
	}
	
	public String getId(){
		
		return id;
		
	}
	
	@Override
	public Object getValue() {

		if( getFormatter() != null ) return getText().replaceAll( "[_)(-]", "" ).replaceAll( "\\s", "" ); // ! POG
		return getText();
		
	}

	@Override
	public void setValue(Object o) {
		
		try{
			
			setText( (String) o );
			
		}catch( Exception e){
			
			setText( o.toString() );
			
		}
		
	}

	public void toggle() {

		enabled = !enabled;
		
	}

	public boolean enabled() {
		
		return enabled;
		
	}
	
}
