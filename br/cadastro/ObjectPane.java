package br.cadastro;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ObjectPane extends JPanel {

	private ElementList nameList;
	private FieldManager fields;
	private JButton insert;
	private JButton alter;
	private JButton delete;
	private JButton cancel;
	
	public ObjectPane( ElementList list, FieldManager forms ) {
		
		setList( list );
		setFields( forms );
		init();
		
	}
	
	public void setList( ElementList list ){
		
		nameList = list;
		
	}
	
	public ElementList getList(){
		
		return nameList;
		
	}
	
	public void setFields( FieldManager forms ){
		
		fields = forms;
		
	}
	
	public FieldManager getFields(){
		
		return fields;
		
	}
	
	public void init(){
		
		setLayout(null);
		add( nameList.getPanel() );
		add( fields.getPanel() );
		fields.toggleEdit( false );
		setPreferredSize( new Dimension( nameList.getPanel().getWidth() + 20 + fields.getPanel().getWidth(), nameList.getPanel().getHeight() + 50 ) );
		
		JPanel control = new JPanel( new FlowLayout(FlowLayout.CENTER, 5, 5) );
		control.setBounds( 0, nameList.getPanel().getHeight() + 10 , getPreferredSize().width, 40 );
		add( control );
		
		insert = new JButton( "Novo" );
		alter = new JButton( "Alterar" );
		delete = new JButton( "Excluir" );
		cancel = new JButton( "Cancelar" );
		
		Dimension d = new Dimension(100, 26);
		insert.setPreferredSize( d );
		alter.setPreferredSize( d );
		delete.setPreferredSize( d );
		cancel.setPreferredSize( d );
		
		control.add( insert );
		control.add( alter );
		control.add( delete );
		control.add( cancel );
		
		alter.setEnabled( false );
		delete.setEnabled( false );
		cancel.setEnabled( false );
		
		nameList.getList().getSelectionModel().addListSelectionListener(new ListSelectionListener() {//seleção alterada
			
			@Override
			public void valueChanged( ListSelectionEvent e ){
				
				if( !e.getValueIsAdjusting() ){
					
					if( nameList.getList().getSelectedIndex() >= 0 ){
						
						fields.fillValuesById( nameList.getList().getSelectedValue().getId() );
						alter.setEnabled( true );
						delete.setEnabled( true );
						
					}else{
						
						alter.setEnabled( false );
						delete.setEnabled( false );
						
					}
					
				}
				
			}
			
		});
		
		insert.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if( insert.getText().equalsIgnoreCase( "salvar" ) ) {

						if( fields.getValidator().isDuplicate() ) return;
						if( !fields.getValidator().Validate() ) return;
						int i = fields.insert(); 
						nameList.atualize();
						nameList.getList().setSelectedItemById( i );
						nameList.getList().ensureIndexIsVisible( nameList.getList().getSelectedIndex() );
						cancel.doClick();
						return;
					
					}
				
				editFields( (JButton) e.getSource() );
				nameList.getList().clearSelection();
				fields.clear();

			}
			
		} );
		
		alter.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if( alter.getText().equalsIgnoreCase( "salvar" ) ){

					if( !fields.getValidator().Validate() ) return;
					fields.alter();
					cancel.doClick();
					int i = nameList.getList().getSelectedIndex();
					nameList.atualize();
					nameList.getList().setSelectedIndex( i );
					return;
					
				}
				
				editFields( (JButton) e.getSource() );
				insert.setEnabled( false );

			}
			
		} );
		
		delete.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if( JOptionPane.showConfirmDialog( null, "Você deseja excluir " + nameList.getList().getSelectedValue().getName() , "", JOptionPane.YES_NO_OPTION  ) == 0 ){
				
					fields.drop();
					nameList.atualize();
				
				}

			}
			
		} );
		
		cancel.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				insert.setEnabled( true );
				cancel.setEnabled( false );
				insert.setText( "Novo" );
				alter.setText( "Alterar" );
				nameList.setEnabled( true );
				fields.toggleEdit( false );
				
				if( nameList.getList().getSelectedIndex() >= 0 ){
					
					int i = nameList.getList().getSelectedIndex();
					nameList.getList().clearSelection();
					nameList.getList().setSelectedIndex(i);
					
				}

			}
		});
		
	}
	
	public void editFields( JButton b ){
		
		b.setText( "Salvar" );
		cancel.setEnabled( true );
		delete.setEnabled( false );
		nameList.setEnabled( false );
		fields.toggleEdit( true );
		
	}

}
