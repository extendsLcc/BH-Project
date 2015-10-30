package br.cadastro;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import br.cadastro.customfields.CustomComboBox;
import br.cadastro.customfields.CustomField;
import br.cadastro.customfields.CustomTable;
import br.cadastro.customfields.CustomTextArea;
import br.cadastro.customfields.CustomTextField;
import br.cadastro.customfields.DateChooser;
import br.main.Conexao;

public class FieldManager {
	
	private JPanel panel = new JPanel();
	private List< CustomTable > tableList = new ArrayList< CustomTable >();
	private List< FieldManager > subFields = new ArrayList< FieldManager >();
	private List< CustomField > fields = new ArrayList< CustomField >();
	private Validation validate;
	
	private String tableName;
	private String tablePk;
	private String tablefk;
	private int pk;
	
	public FieldManager( String forTable, String tablePk, String fk, int x, int y, int width, int height){
		
		tableName = forTable;
		this.tablePk = tablePk;
		tablefk = fk;
		panel.setBounds(x, y, width, height);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setLayout(null);
				
	}

	public JPanel getPanel(){
		
		return panel;
		
	}
	
	public void addValidation( Validation v ){
		
		validate = v;
		
	}
	
	public Validation getValidator(){
		
		return validate;
		
	}
	
	public void addComponent( JComponent comp ){
		
		panel.add(comp);
		
	}

	public void addTextLabel( String txt, int x, int y, int width, int height ){
		
		JLabel label = new JLabel( txt );
		label.setBounds(x, y, width, height);
		panel.add( label );
		
	}
	
	//   TxtField
	
	public void addInput( String id, int limit, int x, int y, int width, int height, boolean b ){
		
		CustomTextField txtField = new CustomTextField( id, limit );
		addInput(txtField, x, y, width, height, b);
		
	}
	
	public void addNumInput( String id, int limit, int x, int y, int width, int height, boolean b ){
		
		addNumInput(id, limit, 0, x, y, width, height, b);
		
	}
	
	public void addNumInput( String id, int limit, int decimal, int x, int y, int width, int height, boolean b ){
		
		CustomTextField txtField = new CustomTextField( id, limit, decimal );
		addInput(txtField, x, y, width, height, b);
		
	}
	
	public void addInput( String id, String filter, int x, int y, int width, int height, boolean b ){
		
		CustomTextField txtField = new CustomTextField( id, filter );
		addInput(txtField, x, y, width, height, b);
		
	}
	

	public void addInput( CustomTextField txtField, int x, int y, int width, int height, boolean b ){
		
		txtField.setBounds( x, y, width, height );
		if(!b) txtField.toggle();
		panel.add( txtField );
		fields.add( txtField );
		
	}

	
	//   TxtArea
	
	public void addTextArea( String id, int limit, int x, int y, int width, int height, boolean b ){
		
		CustomTextArea txtArea = new CustomTextArea( id, limit );
		txtArea.setWrapStyleWord(true);
		txtArea.setLineWrap(true);
		//txtArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		JScrollPane scroll = new JScrollPane( txtArea );
		scroll.setBounds( x, y, width, height );
		if(!b)txtArea.toggle();
		panel.add( scroll );
		fields.add( txtArea );
		
	}
	
	//	Date input
	
	public void addDateInput( String id ,int x, int y, int width, int height, boolean b ){

		DateChooser dc = new DateChooser( id );
		dc.setBounds(x, y, width, height);
		if(!b) dc.toggle();
		panel.add( dc );
		fields.add( dc );
		
	}
	
	//	ComboBox
	
	public void addComboBox( CustomComboBox cb, int x, int y, int width, int height, boolean b ){
		
		if(!b)cb.toggle();
		cb.setBounds(x, y, width, height);
		panel.add( cb );
		fields.add( cb );
		
	}
	
	public CustomField getField( String id ){
		
		for( CustomField cf: fields ){
			
			if( cf.getId().equals( id ) ){
				
				return cf;
				
			}
			
		}
		
		return null;
		
	}
	
	public CustomField getFieldEx( String id ){

		for( CustomField cf: fields ){
			
			if( cf.getId().equals( id ) ){
				
				return cf;
				
			}
			
		}
		
		for( FieldManager fm : subFields){
			
			return fm.getFieldEx( id );
			
		}
		
		return null;
		
	}
	
	// subTable
	
	public void addTableField( CustomTable ct ){
		
		tableList.add( ct );
		ct.addToPanel( panel );
		
	}
	
	//----------------------------------------
	
	public void addFieldManagerChild( FieldManager which ){
		
		subFields.add( which );
		panel.add( which.getPanel() );//which.addToPanel( panel );
		
	}
	
	public FieldManager getChild( int index ){
		
		return subFields.get( index );
		
	}
	
	public void atualizeComboBox(){
		
		for( CustomField cf: fields ){
			
			if( cf instanceof CustomComboBox ){
				
				((CustomComboBox) cf).atualizeItens();
				
			}
			
		}
		
	}
	
	public void fillValuesById( int whichId ){ // preencher valores dos campos com os dados do id da tabela		
		
		try{
			
			String query = "";
			
			for( int i = fields.size() - 1; i >= 0; i-- ){
				
				query += i == 0 ? fields.get( i ).getId() : fields.get( i ).getId() + ", " ;
				
			}
			
			for( FieldManager fm: subFields ){
				
				query += query.length() < 2 ? fm.tablefk : "," + fm.tablefk;
				
			}
			
			query = "SELECT " + query + " FROM "+ tableName + " WHERE "+ tablePk +"="+whichId;
			ResultSet data = Conexao.query( query );
			//System.out.println( query );
			try{ data.next(); }catch( Exception e ){ return; }
			pk = whichId;
			
			for( CustomField cf: fields ){
			
				cf.setValue( data.getObject( cf.getId() ) );
				
			}
			
			for( CustomTable ct : tableList ){
				
				ct.fillValuesById( whichId );
				
			}
			
			for( FieldManager fm: subFields ){

				fm.fillValuesById( data.getInt( fm.tablefk ) );
				
			}
			
		}catch( SQLException e ){
			
			JOptionPane.showMessageDialog(null, "Erro na consulta de informações da tabela "+ tableName + " pelo id "+ whichId +" \n Erro: "+ e.getMessage());
			
		}
		
	}
	
	public int insert(){
		
		int index;
		String sql = "INSERT INTO " + tableName + " ( ";
		ArrayList < Integer > childsIndex = new ArrayList< Integer >();

		for( CustomField cf : fields){
			
			sql +=  cf.getId() + ", ";//i == 0 ? fields.get( i ).getId() : fields.get( i ).getId() + ", ";
			
		}
		
		for( FieldManager child : subFields ){
			
			childsIndex.add( child.insert() );
			sql += child.tablefk + ", ";
			
		}
		
		sql += ") VALUES ( '";
		
		for( CustomField cf : fields){
			
			sql += cf.getValue() + "', '";
			
		}
		
		for( int i = 0; i < subFields.size(); i++ ){
			
			sql += childsIndex.get( i );
			sql += "', '";
			
		}
	
		sql += ")";
		sql = sql.replaceAll( "'[)]", ")");
		sql = sql.replaceAll( ",\\s[)]", " )");
		//System.out.println( sql );
		index = Conexao.insert( sql );
		
		for( CustomTable ct: tableList ){
			
			ct.save( index );
			
		}
		
		return index;
		
	}
	
	public void alter(){
		
		String sql = "UPDATE " + tableName + " SET ";
		
		for( CustomField cf: fields ){
			
			sql += cf.getId() + "='" + cf.getValue() + "',";
			
		}
		
		sql += "WHERE ";
		sql = sql.replaceAll( ",W" , " W");
		sql += tablePk + "=" + pk;
		//System.out.println( sql );
		
		try {
			
			Conexao.updateHandled( sql );
			
		} catch (SQLException e) {}
		
		for( FieldManager fm: subFields ){
			
			fm.alter();
			
		}
		
		for( CustomTable ct: tableList ){
			
			ct.save( pk );
			
		}
		
	}
	
	public void drop(){
		
		try {
			
			for( CustomTable ct: tableList ){
				
				ct.delete( pk );
				
			}
			
			Conexao.updateHandled( "DELETE FROM " + tableName + " WHERE " + tablePk + "=" + pk );
			clear();
			
			for( FieldManager fm : subFields ){
				
				fm.drop();
				fm.clear();
				
			}
			
		} catch ( SQLException e){
			
			for( CustomTable ct: tableList ){
				
				ct.save( pk );
				
			}
			
			JOptionPane.showMessageDialog( null, tableName + " não pode ser excluido, porque esta sendo usado por outro item! \n" + e.getMessage()  );
			
		}
		
	}

	public void clear() {

		for( CustomField cf: fields ){
			
			if( cf instanceof CustomComboBox ){
				
				( (CustomComboBox) cf).setSelectedIndex( 0 );;
				
			}else{
				
				cf.setValue( "" );

			}
			
		}
		
		for( CustomTable t: tableList ){
			
			t.clear();
			
		}
		
		for( FieldManager sF: subFields ){
			
			sF.clear();
			
		}
	}

	public void toggleEdit(boolean flag) {

		for( CustomField cf: fields ){
			
			((JComponent) cf).setEnabled( flag && cf.enabled() );
			
		}
		
		for( CustomTable t: tableList ){
			
			t.getTable().setEnabled( flag );
			
		}
		
		for( FieldManager fm: subFields ){
			
			fm.toggleEdit( flag );
			
		}
		
	}
	
}
