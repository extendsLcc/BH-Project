package br.windows;

import java.awt.Component;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import br.cadastro.customfields.Item;
import br.main.Conexao;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Lista extends JPanel {
	
	private JTable table;
	private DefaultTableModel model;
	private String sql;
	
	public Lista( Object[] columnsName, String sql ){
		
		setLayout(null);
		setPreferredSize( new Dimension( 915, 540 ) );
		this.sql = sql;
		model = new DefaultTableModel( columnsName, 0 ){

			public boolean isCellEditable(int row, int col){ 
				
				return false; 
				
			}
			
		};
		table = new JTable( model );
		JScrollPane scroll = new JScrollPane( table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		scroll.setBounds( 10, 11, 895, 485);
		add( scroll );
		
		tooltipRenderer tool = new tooltipRenderer();
		tool.setHorizontalAlignment( SwingConstants.CENTER );
		table.setDefaultRenderer( Object.class, tool );
		atualize();
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				atualize();
				
			}
		});
		btnAtualizar.setBounds(411, 507, 89, 23);
		add(btnAtualizar);
	}
	
	public void atualize(){
		
		for( int i = model.getRowCount() - 1; i >= 0; i -- ){
			
			model.removeRow( i );
			
		}
		
		try {
			
			ResultSet rs = Conexao.query( sql );
			ResultSetMetaData rsmd = rs.getMetaData();
			
			while( rs.next() ){
				
				ArrayList<Object> objects = new ArrayList<Object>();
				objects.add( new Item( rs.getInt( 1 ), rs.getString( 2 ) ) );
				
				for( int i = 3; i <= rsmd.getColumnCount(); i ++){
					
					objects.add( rs.getObject( i ) );
					
				}
				
				model.addRow( objects.toArray() );
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
	}
}


class tooltipRenderer extends DefaultTableCellRenderer {
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus,	row, column);
		c.setToolTipText( c.getText() );
		return c;
	}
	
}