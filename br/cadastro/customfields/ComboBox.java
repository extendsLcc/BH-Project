package br.cadastro.customfields;

import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import br.main.Conexao;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ComboBox extends JComboBox<Item>{

	private String query;
	
	public ComboBox( String whichQuery ){
		
		super();
		query = whichQuery;
		AutoCompleteDecorator.decorate( this );
		atualizeItens();
		
	}
	
	public void atualizeItens(){
		
		int index = getSelectedIndex();
		removeAllItems();
		ResultSet rs = Conexao.query( query );
		
		try {
			
			while( rs.next() ){
				
				addItem( new Item( rs.getInt(1), rs.getString(2) ) );
				
			}
			
		} catch (SQLException e) {}
		
		setSelectedIndex( Math.min( Math.max( index, 0), getItemCount() - 1 ) );
		
	}
	
	public void selectItemById( int id ){
		
		for( int i = 0; i < getItemCount(); i++ ){
			
			if( getItemAt( i ).getId() == id ){
				
				setSelectedIndex( i );
				break;
				
			}
			
		}
		
	}
	
	public int getSelectedItemId(){

		if( getSelectedIndex() < 0 ) return -1; // Caso o ComboBox for Editavel
		return ( (Item) getSelectedItem() ).getId();
		
	}
	
	public void setSQL( String newSql ){
		
		query = newSql;
		
	}
		
}
