package br.tst;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import br.tst.customFields.Item;
import br.tst.customFields.NameList;

import java.awt.Color;

public class ElementList {
	
	private NameList list;
	private List<Item> itens = new ArrayList<Item>();
	private JPanel panel = new JPanel();
	private JTextField search;
	private FieldManager fields;
	
	public ElementList( JPanel parent, String name, int x, int y, int width, int height, String query ){
	
		list = new NameList( query );
        
		JScrollPane scroll =list.getScroll();
		scroll.setBounds(10, 36, width - 20, height - 47 );
		panel.add( scroll );
		
		panel.setBounds(x, y, width, height);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setLayout(null);
		parent.add( panel );
		
		JLabel title = new JLabel("Lista de " + name + ":");
		title.setBounds(10, 11, 150, 14);
		panel.add(title);
		
		search = new JTextField();
		search.setBounds(157, 8, 100, 20);
		search.setColumns(10);
		panel.add(search);
		
		atualize();
			
		list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {//seleção alterada
			
			@Override
			public void valueChanged( ListSelectionEvent e ){
				
				if( !e.getValueIsAdjusting() && list.getSelectedIndex() >= 0 ){
					
					fields.fillValuesById( list.getSelectedValue().getId() );
					
				}
				
			}
			
		});
		
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
	
	public void setFields( FieldManager which ){ //GAMBI
		
		fields = which;
		
	}
	
	public void atualize(){
		
		//! Executar consulta e pegar lista de nome a ser mostrado + id do objeto
		list.atualizeItens();
		
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

}
