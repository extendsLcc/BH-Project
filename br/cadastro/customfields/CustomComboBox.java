package br.cadastro.customfields;

import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import br.main.Conexao;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomComboBox extends ComboBox implements CustomField{

	private String id = "";
	private Boolean enabled = true;
	
	//https://gist.github.com/anonymous/52faceb6bb0b7c8fb3bf
	
	public CustomComboBox( String s, String whichQuery){
		
		super( whichQuery );
		id = s;
		
	}
	
	public void setId( String s ){
		
		id = s;
		
	}
	
	public String getId(){
		
		return id;
		
	}
	
	@Override
	public Object getValue() {
		
		return getSelectedItemId();
		
	}

	@Override
	public void setValue(Object o) {

		selectItemById( (int) o );
		
	}

	public void toggle() {
		
		enabled = !enabled;
		
	}

	public boolean enabled() {
		
		return enabled;
		
	}
		
}
