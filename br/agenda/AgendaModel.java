package br.agenda;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import br.cadastro.customfields.Item;

class AgendaModel extends AbstractTableModel {

	private List<Item> columns = new ArrayList<Item>();
	private List<RowData> rows = new ArrayList<RowData>();
	
	public static String PLACEHOLDER = "";
	
	public void addColumn( Item i ){
		
		columns.add( i );
		fireTableStructureChanged();
		
	}
	
	public boolean containsColumnId( int id ){
		
		return getColumnIndexById( id ) >= 0;
		
	}
	
	public int getColumnIndexById( int id ){
		
		for( int i = 0; i < columns.size(); i ++){
			
			if( columns.get( i ).getId() == id ){
				
				return i;
				
			}
			
		}
		
		return -1;
		
	}
	
	public Item getColumn( int column ){
		
		return columns.get(column );
		
	}
	
	public void removeAllColumns(){
		
		for( int i = getColumnCount() - 1; i >= 0; i-- ){
			
			removeColumn( i );
			
		}
		
	}
	
	public void removeColumn( int column ){
		
		for( int i = 0; i < getRowCount() - 1; i++ ){ 
			
			setValueAt( PLACEHOLDER, i, column);
			
		}
		
		columns.remove( column );
		fireTableStructureChanged();
		
	}
	
	public void removeAllRows(){
		
		for( int i = getRowCount() - 1; i >= 0; i-- ){
			
			removeRow( i );
			
		}
		
	}
	
	public void removeRow( int row ){
		
		rows.remove( row );
		fireTableRowsDeleted( row, row );
		
	}
	
	public void addRow( Object o[] ){

		rows.add( new RowData() );
		
		for( int i = 0; i < o.length; i ++ ){
			
			setValueAt( o[i], getRowCount() - 1, i );
			
		}
		
		fireTableDataChanged();
		
	}
	
	public void clearRows(){
		
		for( int i = getColumnCount() - 1; i >= 0; i -- ){
			
			for( int x = getRowCount() - 1; x >= 0; x-- ){
				
				setValueAt( PLACEHOLDER, x, i);
				
			}
			
		}
		
	}
	
	public String getColumnName(int num){
		
        return columns.get( num ).toString();
        
    }
	
	@Override
	public int getColumnCount() {

		return  columns.size();
		
	}

	@Override
	public int getRowCount() {

		return rows.size();
		
	}
	
	public boolean isCellEditable(int row, int col){ 
		
		return false; 
		
	}
	
	public void setValueAt(Object value, int row, int col) {

		rows.get( row ).setValueForCol( value, col );
	    fireTableCellUpdated(row, col);
	    
	}

	@Override
	public Object getValueAt(int row, int col) {
		
		return rows.get( row ).getValueForCol( col );
		
	}
	
	private class RowData {
		
		private Map<Integer, Object> values = new HashMap<Integer, Object>();
		
		public Object getValueForCol(int columnIndex) {
			if(values.containsKey(columnIndex)){
				return values.get(columnIndex);
			}
			return PLACEHOLDER;
		}
		
		public void setValueForCol(Object aValue, int columnIndex) {
			values.put(columnIndex, aValue);
		}
		
	}
	
}


class AgendacellRenderer extends DefaultTableCellRenderer {
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		
		try{
			
			CellAgenda cell = (CellAgenda) value;
			
			if( !isSelected )
				c.setBackground( cell.getBackground() );
			else
				c.setBackground( cell.getBackground().darker() );
			
		}catch( Exception e ){
			
			if( !isSelected )
				c.setBackground( table.getBackground() );
			
		}
		
		return c;
	}
	
}