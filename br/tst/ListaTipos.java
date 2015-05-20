package br.tst;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import br.tst.customFields.Item;
import br.tst.customFields.NameList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListaTipos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private String[] data;
	private String title;
	private NameList list;
	private JButton alter;
	private JButton insert;
	private JButton delete;
	
	public ListaTipos( String title, String[] tableIdName ) {	
		
		this.title = title;
		data = tableIdName;
		
		setTitle( title );
		setBounds(100, 100, 306, 212);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		insert = new JButton("Inserir");
		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				insert();
				
			}
		});
		insert.setBounds(4, 6, 89, 23);
		contentPanel.add(insert);
		
		alter = new JButton("Alterar");
		alter.setEnabled(false);
		alter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				alter();
				
			}
		});
		alter.setBounds(4, 31, 89, 23);
		contentPanel.add(alter);
		
		delete = new JButton("Remover");
		delete.setEnabled(false);
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				delete();
				
			}
		});
		delete.setBounds(4, 55, 89, 23);
		contentPanel.add(delete);
		
		list = new NameList( data );
		list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				altNDel();
			}
			
		});
		
		JScrollPane scroll = list.getScroll();
		scroll.setBounds( 99, 6, 187, 157 );
		contentPanel.add( scroll );
		
		list.atualizeItens();
		
	}
	
	public void insert(){

		String nome = JOptionPane.showInputDialog(this, "Insira o nome de " + title + " a ser cadastrado");
		if( nome == null ) return;
		if( nome.length() > 25 ) JOptionPane.showMessageDialog( this, "O nome deve ter no máximo 25 caracteres." );
		
		if( Conexao.update( "INSERT INTO " + data[0] + "(" + data[2] + ") VALUES ('" + nome + "')" ) ){

			JOptionPane.showMessageDialog( this, title + " \"" + nome + "\" foi Cadastrado com sucesso!" );
			list.atualizeItens();
			
		}
		
	}
	
	public void alter(){
		
		if( list.getSelectedValue() != null ){

			String s = JOptionPane.showInputDialog(this, "Insira o novo nome de " + title + " de " + list.getSelectedValue().getName(), list.getSelectedValue().getName() );
			if( s == null ) return;
			if( s.length() > 25 ) JOptionPane.showMessageDialog( this, "O nome deve ter no máximo 25 caracteres." );
			Conexao.update( "UPDATE " + data[0] + " SET " + data[2] + "='" + s  + "' WHERE " + data[1] + "=" + list.getSelectedValue().getId() );
			list.atualizeItens();
			
		}
		
	}
	
	public void delete(){	

		if( JOptionPane.showConfirmDialog( this, "Você tem certeza que deseja excluir " + title + ": " 
		+ list.getSelectedValue().getName(), "", JOptionPane.YES_NO_OPTION  ) == 0 ){
		
			String nome = list.getSelectedValue().toString();
			
			if( Conexao.update( "DELETE FROM " + data[0] + " WHERE " + data[1] + "=" +  list.getSelectedValue().getId() ) ){
	
				JOptionPane.showMessageDialog( this, title + " \"" + nome + "\" foi excluido com sucesso!" );
				list.atualizeItens();
				
			}else{

				JOptionPane.showMessageDialog( this, title + " \"" + nome + "\" não pode ser deletado porque esta sendo usado por outro Item." );
				
			}
			
		}
		
	}
	
	private void altNDel(){

		if( list.getSelectedIndex() == -1 ){
			
			alter.setEnabled( false );
			delete.setEnabled( false );
			
		}else {
			
			alter.setEnabled( true );
			delete.setEnabled( true );
			
		}
		
	}
	
}