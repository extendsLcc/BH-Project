package br.cadastro.customfields;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import br.main.Conexao;

public class CustomTable {
	
	private JScrollPane scroll;
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel();
	private String querySql;
	private String[] tableData;
	
	public CustomTable( Object[] columns, String whichquery, String[] tableData, int x, int y, int width, int height){
		
		querySql = whichquery;
		this.tableData = tableData;
		table = new JTable( model );
		scroll = new JScrollPane( table );
		scroll.setBounds( x, y, width, height );
		scroll.getViewport().setBackground(Color.WHITE);
		table.getSelectionModel().setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		DefaultTableCellRenderer  centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
		table.setDefaultRenderer( Object.class , centerRenderer );
		table.setRowHeight( 30 );
		
		for( Object o : columns ){

			model.addColumn( o );
			
		}
		
		JTextField txt = new JTextField();
		txt.setDocument( new JTextFieldNumbFilter(10, 2) );
		table.setDefaultEditor( Object.class, new DefaultCellEditor( txt ));
		TableColumn col = table.getColumnModel().getColumn(0);
	    col.setCellEditor( new CellEditor());
		
	}
	
	public void addToPanel( JPanel parent ){
		
		parent.add( scroll );
		JButton button = new JButton("+");
		button.setBounds( scroll.getX() + scroll.getWidth() + 5, scroll.getY() + scroll.getHeight() / 2 - 15 , 24, 23);
		button.setMargin( new Insets( 0, 0, 0, 0) );
		button.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if( table.isEnabled() ){
					
					addRow( new Object[]{ 1 });
					
				}
				
			}
			
		} );
		parent.add( button );
		
		button = new JButton("-");
		button.setBounds( scroll.getX() + scroll.getWidth() + 5, scroll.getY() + scroll.getHeight() / 2 + 11 , 24, 23);
		button.setMargin( new Insets( 0, 0, 0, 0) );		
		button.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if( table.getSelectedRow() >= 0 && table.isEnabled() ){
					
					model.removeRow( table.getSelectedRow() );
					
				}
				
			}
			
		} );
		parent.add( button );
		
	}
	
	public void fillValuesById( int whichId ){
		
		clear();
		
		try {
			
			ResultSet rs = Conexao.query(querySql+ whichId);

			while( rs.next() ){
				
				List <Object> data = new ArrayList<Object>();
				
				for( int i = 1; i <= rs.getMetaData().getColumnCount(); i++ ){
					
					data.add( rs.getObject( i ) );
					
				}
				
				addRow( data.toArray() );
				
			}
			
			
			
		} catch (SQLException e) {
			
		}
		
	}
	
	public void addRow( Object[] o ){
		
		model.addRow( o );
		table.editCellAt( model.getRowCount() - 1, 0 );
		table.getCellEditor().stopCellEditing();
		
	}
	
	public void stopEdit(){
		
		if( table.isEditing() ){
			
			table.getCellEditor().stopCellEditing();
			
		}
		
	}
	
	public void save( int id ) {

		delete( id );
		stopEdit();
		
		for( int i = model.getRowCount() - 1; i >= 0; i-- ){
			
			String sql = "INSERT INTO " + tableData[0] + " ( ";
			String tmp = "";
			
			for( int x = 1; x < tableData.length; x ++){
				
				tmp += tableData[x] + ", ";
				
			}
			
			tmp += ")";
			sql += tmp.replaceAll( ",\\s[)]", " )") + " VALUES ( '" + id + "', '" ;
			try{
				sql += ( (Item) model.getValueAt( i, 0 ) ).getId();
			}catch( Exception e){
				sql += model.getValueAt( i, 0 );
			}
			sql += "'";
			
			for( int x = 1; x < model.getColumnCount(); x++ ){
				
				sql += ", '" + ( model.getValueAt( i, x ) + "" ).replaceAll( "null", "0" ).replaceAll(",", ".") + "' ";
				
			}
			
			sql+= " )";

			try {
				
				Conexao.updateHandled( sql );
				
			} catch (SQLException e) {

			}
			
		}
		
	}
	
	public void delete( int id ){
		
		try {
			
			Conexao.updateHandled( "DELETE FROM " + tableData[0] + " WHERE " + tableData[1] + "=" + id );
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void clear(){
		
		stopEdit();

		for( int i = model.getRowCount() - 1; i >= 0 ; i-- ){
			
			model.removeRow( i );
			
		}
		
	}
	
	public JTable getTable(){
		
		return table;
		
	}

	
}

class CellEditor extends AbstractCellEditor implements TableCellEditor {

	private CustomComboBox combo = new CustomComboBox( "null", "SELECT idproduto, nome FROM produto" );
	
	@Override
	public Object getCellEditorValue() {

		return combo.getSelectedItem();
		
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		
		combo.atualizeItens();
		
	    for( int i = 0; i < combo.getItemCount(); i++ ){

	    	try{
	    		
		    	if( combo.getItemAt( i ).getId() == Integer.parseInt( value.toString() ) ){
		    		
		    		combo.setSelectedIndex( i );
		    		
		    	}
	    	
	    	}catch( Exception e ){
	    		
	    		if( combo.getItemAt( i ).getId() == ((Item) value).getId() ){
	    			
	    			combo.setSelectedIndex( i );
	    			
	    		}
	    		
	    	}
	    	
	    }
	    
		return combo;
		
	}
	
}