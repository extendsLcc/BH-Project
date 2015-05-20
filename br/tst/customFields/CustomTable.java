package br.tst.customFields;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import br.tst.Conexao;

public class CustomTable {
	
	private JScrollPane scroll;
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel();
	private String querySql;
	
	public CustomTable( Object[] columns, String whichquery, int x, int y, int width, int height){
		
		querySql = whichquery;
		table = new JTable( model );
		scroll = new JScrollPane( table );
		scroll.setBounds( x, y, width, height );
		scroll.getViewport().setBackground(Color.WHITE);
		table.getSelectionModel().setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		
		for( Object o : columns ){

			model.addColumn( o );
			
		}
		
	}
	
	public void addToPanel( JPanel parent ){
		
		parent.add( scroll );
		
	}
	
	public void fillValuesById( int whichId ){
		
		for( int i = model.getRowCount() - 1; i >= 0 ; i-- ){
			
			model.removeRow( i );
			
		}
		
		try {
			
			ResultSet rs = Conexao.query(querySql+ whichId);

			while( rs.next() ){
				
				List <Object> data = new ArrayList<Object>();
				
				for( int i = 1; i <= rs.getMetaData().getColumnCount(); i++ ){
					
					data.add( rs.getObject( i ) );
					
				}
				
				model.addRow( data.toArray() );
				
			}
			
			
			
		} catch (SQLException e) {
			
		}
		
	}

}
