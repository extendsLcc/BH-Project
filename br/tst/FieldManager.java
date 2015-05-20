package br.tst;

import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import br.tst.customFields.CustomComboBox;
import br.tst.customFields.CustomTable;
import br.tst.customFields.CustomTextArea;
import br.tst.customFields.CustomTextField;

public class FieldManager {
	
	private JPanel panel = new JPanel();
	List< CustomTextField > inputList = new ArrayList< CustomTextField >();
	List< CustomTextArea > txtAreaList = new ArrayList< CustomTextArea >();
	List< CustomComboBox > comboBoxList = new ArrayList< CustomComboBox >();
	List< CustomTable > tableList = new ArrayList< CustomTable >();
	List< FieldManager > subFields = new ArrayList< FieldManager >();
	private String tableName;
	private String tablePk;
	private String tablefk;
	
	public FieldManager( String forTable, String tablePk, String fk, int x, int y, int width, int height){
		
		tableName = forTable;
		this.tablePk = tablePk;
		tablefk = fk;
		panel.setBounds(x, y, width, height);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setLayout(null);
				
	}
	
	public void addComponent( Component comp ){
		
		panel.add(comp);
		
	}
	
	public void addToPanel( JPanel parent ){
		
		parent.add( panel );
		
	}

	public void addTextLabel( String txt, int x, int y, int width, int height ){
		
		JLabel label = new JLabel( txt );
		label.setBounds(x, y, width, height);
		panel.add( label );
		
	}
	
	//   TxtField
	
	public void addInput( String id ,int x, int y, int width, int height ){
		
		CustomTextField txtField = new CustomTextField( id );
		txtField.setBounds( x, y, width, height );
		panel.add( txtField );
		inputList.add( txtField );
		
	}
	
	public CustomTextField getInputById( String whichId ){
		
		for( CustomTextField ctf : inputList ){
			
			if( ctf.getId().equals( whichId ) ){
				
				return ctf;
				
			}
			
		}
		
		return null; //lista de input vazia ou id invalido
		
	}
	
	//   TxtArea
	
	public void addTextArea( String id ,int x, int y, int width, int height ){
		
		CustomTextArea txtArea = new CustomTextArea( id );
		txtArea.setWrapStyleWord(true);
		txtArea.setLineWrap(true);
		//txtArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		JScrollPane scroll = new JScrollPane( txtArea );
		scroll.setBounds( x, y, width, height );
		panel.add( scroll );
		txtAreaList.add( txtArea );
		
	}
	
	public CustomTextArea getTextAreaById( String whichId ){
		
		for( CustomTextArea cta: txtAreaList ){
			
			if( cta.getId().equals( whichId ) ){
				
				return cta;
				
			}
			
		}
		
		return null; //lista de textArea vazia ou id invalido
		
	}
	
	// ComboBox
	
	public void addComboBox( CustomComboBox ccb ){
		
		panel.add( ccb );
		comboBoxList.add( ccb );
		
	}

	public CustomComboBox getComboById( String whichId ){
		
		for( CustomComboBox ccb: comboBoxList ){
			
			if( ccb.getId().equals( whichId ) ){
				
				return ccb;
				
			}
			
		}
		
		return null; //lista de comboBox vazia ou id invalido
		
	}
	
	// subTable
	
	public void addTableField( CustomTable ct ){
		
		tableList.add( ct );
		ct.addToPanel( panel );
		
	}
	
	//----------------------------------------
	
	public void addFieldManagerChild( FieldManager which ){
		
		subFields.add( which );
		which.addToPanel( panel );
		
	}
	
	public FieldManager getChild( int index ){
		
		return subFields.get( index );
		
	}
	
	public void fillValuesById( /*String forTable,*/ int whichId/*, String idName*/ ){ // preencher valores dos campos com os dados do id da tabela		
		
		try{
			
			String query = "SELECT ";
			
			for( int i = inputList.size() - 1; i >= 0; i-- ){
				
				query += i == 0 ? inputList.get( i ).getId() :inputList.get( i ).getId() + ", ";
				
			}
			
			for( int i = comboBoxList.size() - 1; i >= 0; i-- ){
				
				query += comboBoxList.get( i ).getId().isEmpty() ? "" :", " + comboBoxList.get( i ).getId();
				
			}
			
			for( int i = txtAreaList.size() - 1; i >= 0; i-- ){
				
				query +=  ", " + txtAreaList.get( i ).getId();
				
			}
			
			query += " FROM "+ tableName + " WHERE "+ tablePk +"="+whichId;
			ResultSet data = Conexao.query( query );
			//System.out.println( query );
			if( !data.next() ) return;
			
			for( CustomTextField ctf : inputList ){

				ctf.setText( data.getString( ctf.getId() ) );
				
			}
			
			for( CustomTextArea cta : txtAreaList ){

				cta.setText( data.getString( cta.getId() ) );
				
			}
			
			for( CustomComboBox ccb : comboBoxList ){

				ccb.selectItemById( data.getInt( ccb.getId() ) );
				
			}
			
			for( CustomTable ct : tableList ){
				
				ct.fillValuesById( whichId );
				
			}
			
			for( FieldManager fm: subFields ){

				ResultSet rs = Conexao.query( "SELECT "+ fm.tablefk +" FROM "+ tableName +" WHERE "+ tablePk +"="+ whichId );
				rs.next();
				fm.fillValuesById( rs.getInt( fm.tablefk ) );
				
			}
			
		}catch( SQLException e ){
			
			JOptionPane.showMessageDialog(null, "Erro na consulta de informações da tabela "+ tableName + " pelo id "+ whichId +" \n Erro: "+ e.getMessage());
			
		}
		
	}
	
}
