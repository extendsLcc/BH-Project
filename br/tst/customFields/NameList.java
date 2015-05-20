package br.tst.customFields;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import br.tst.Conexao;

public class NameList extends JList<Item>{
	
	/**
	 * 		Mostrar lista de nomes da tabela, saber dizer quem é id da tabela que representa aquele nome
	 * 
	 * */
	
	private DefaultListModel<Item> listModel = new DefaultListModel<Item>();
	private JScrollPane scroll = new JScrollPane( this, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
	private String sqlQuery; //consulta
	
	public NameList( String table, String tablePk, String outputColumn ){
		
		this( "SELECT " + tablePk + ", "+ outputColumn + " FROM " + table );
		
	}

	public NameList( String sql ){

		super();
		setModel( listModel );
		sqlQuery = sql;
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		atualizeItens();
		
	}
	
	public NameList( String[] dados ){ // ORDEM: table, id, nome
		
		this( "SELECT "+ dados[1] + ", " + dados[2] + " FROM " + dados[0] );
		
	}
	
	public void atualizeItens(){

		listModel.removeAllElements();
		ResultSet resposta = Conexao.query( sqlQuery );
		
		try {
			
			while( resposta.next() ){
				
				listModel.addElement( new Item( resposta.getInt( 1 ), resposta.getString( 2 ) ) );
				
			}
			
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(null, "Ocorreu um erro na atualização de dados da lista \n Erro:" + e.getMessage());

		}
		
	}
	
	public void setSQL( String newsql ){
	
		sqlQuery = newsql;
	
	}
	
	public JScrollPane getScroll(){
		
		return scroll;
		
	}
	
	public DefaultListModel<Item> getModel(){
		
		return listModel;
		
	}
	
}