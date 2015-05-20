package br.tst.customFields;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import br.tst.Conexao;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomComboBox extends JComboBox<Item>{

	private String id = "";
	private String query;
	
	//https://gist.github.com/anonymous/52faceb6bb0b7c8fb3bf
	
	public CustomComboBox( String s, String whichQuery, int x, int y, int width, int height){
		
		super();
		id = s;
		query = whichQuery;
		setBounds(x, y, width, height);
		AutoCompleteDecorator.decorate( this );
		atualizeItens();
		
	}
	
	public void atualizeItens(){
		
		removeAllItems();
		addItem( new Item( -1, "----" ) );	
		ResultSet rs = Conexao.query( query );
		
		try {
			
			while( rs.next() ){
				
				addItem( new Item( rs.getInt(1), rs.getString(2) ) );
				
			}
			
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog( null, "Erro na consulta de construção do ComboBox \n" + e.getMessage() );
			
		}
		
	}
	
	public void selectItemById( int id ){
		
		for( int i = 0; i < getItemCount(); i++ ){
			
			if( getItemAt( i ).getId() == id ){
				
				setSelectedIndex( i );
				
			}
			
		}
		
	}
	
	public int getSelectedItemId(){

		if( getSelectedIndex() < 0 ) return -1; // Caso o ComboBox for Editavel
		return ( (Item) getSelectedItem() ).getId();
		
	}
	
	public void setId( String s ){
		
		id = s;
		
	}
	
	public String getId(){
		
		return id;
		
	}
		
}
