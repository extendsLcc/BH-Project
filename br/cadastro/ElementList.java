package br.cadastro;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import br.cadastro.customfields.Item;
import br.cadastro.customfields.NameList;


public class ElementList {
	
	private NameList list;
	private List<Item> itens = new ArrayList<Item>();
	private JPanel panel = new JPanel();
	private JTextField search;
	
	public ElementList( String name, int x, int y, int width, int height, String query ){
	
		list = new NameList( query );
        
		JScrollPane scroll = list.getScroll();
		scroll.setBounds(10, 36, width - 20, height - 47 );
		panel.add( scroll );
		
		panel.setBounds(x, y, width, height);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setLayout(null);
		
		JLabel title = new JLabel("Lista de " + name + ":");
		title.setBounds(10, 11, 150, 14);
		panel.add(title);
		
		search = new JTextField();
		search.setBounds(157, 8, 100, 20);
		search.setColumns(10);
		panel.add(search);
		
		atualize();
		
		search.getDocument().addDocumentListener( new DocumentListener(){ // input event

			@Override
			public void changedUpdate(DocumentEvent e) {

				search();
				
			}

			@Override
			public void insertUpdate(DocumentEvent e) {

				search();
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {

				search();
				
			}
			
		});
		
	}

	public void atualize(){
		
		//! Executar consulta e pegar lista de nome a ser mostrado + id do objeto
		list.atualizeItens();
		itens.clear();
		
		for( int i = 0; i < list.getModel().getSize(); i++ ){
			
			itens.add( list.getModel().get( i ) );
			
		}
		
	}
	
	public void search(){
		
		list.getModel().removeAllElements();
		
		for( Item i : itens ){
			
			if( i.getName().toLowerCase().contains( search.getText().toLowerCase() ) ){

				list.getModel().addElement( i );
				
			}
			
		}
		
	}
	
	public JPanel getPanel(){
		
		return panel;
		
	}
	
	public NameList getList(){
		
		return list;
		
	}

	public void setEnabled(boolean b) {

		list.setEnabled( b );
		search.setEnabled( b );

	}

}
