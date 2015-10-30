package br.cadastro;

import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import br.main.Conexao;
import br.main.Util;

public interface Validation {
	
	public boolean Validate();
	
	public boolean isDuplicate();

	public default String isEmpty( FieldManager fm, String fieldId, String error ){

		String s = "";
		
		if( ((JTextComponent) fm.getFieldEx( fieldId )).getText().length() <= 0 ){
			
			s += error + "\n";
			
		}
		
		return s;
		
	}

	public default String isEmptyFilter( FieldManager fm, String fieldId, String error, int size ){
		
		String s = "";
		
		if( ((JTextComponent) fm.getFieldEx( fieldId )).getText().replaceAll( "[_)(-]", "" ).replaceAll( "\\s", "" ).length() <= size ){
			
			s += error + "\n";
			
		}
		
		return s;
		
	}
	
	public default boolean checkDuplicate( FieldManager fm, String id, String table ){
		
		boolean b = false;
		String name = fm.getFieldEx( id ).getValue().toString();
		
		try {
			
			b = Conexao.query( "SELECT " + id + " FROM " + table + " WHERE "+ id +"='"+name+"'" ).next();
			
		} catch (SQLException e) {}
	
		if( b )
			JOptionPane.showMessageDialog(null , "Nome duplicado! \nEscolha outro nome!");
		
		return b;
		
	}

}
