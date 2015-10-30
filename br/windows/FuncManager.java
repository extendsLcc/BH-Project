package br.windows;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.cadastro.customfields.CustomComboBox;
import br.cadastro.customfields.Item;
import br.cadastro.customfields.NameList;
import br.main.Conexao;
import br.main.Util;

public class FuncManager extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private NameList listaFunction;
	private NameList funcaoFuncionario;
	private CustomComboBox comboBox;
	private ListaTipos funcao;
	
	public FuncManager() {
		
		setBounds(100, 100, 419, 385);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 346, 464, 1);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		listaFunction = new NameList( new String[]{ "funcao", "idfuncao", "nome" } );
		listaFunction.getScroll().setBounds(10, 74, 154, 217);
		getContentPane().add( listaFunction.getScroll() );
		
		funcaoFuncionario = new NameList( "SELECT idfuncao FROM funcao WHERE idfuncao=-1" );
		funcaoFuncionario.getScroll().setBounds(237, 74, 154, 217);
		getContentPane().add( funcaoFuncionario.getScroll() );
		
		comboBox = new CustomComboBox( "", "SELECT idPessoa, nome FROM pessoa "
				+ "RIGHT JOIN funcionario ON idpessoa=Pessoa_idPessoa" );
		comboBox.setBounds( 133, 13, 173, 25 );
		comboBox.setSelectedIndex(0);
		comboBox.addActionListener( new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				CustomComboBox cb = (CustomComboBox) e.getSource();
				if( cb.getSelectedItem() == null ) return;
				int id = ((Item) cb.getSelectedItem()).getId();
				funcaoFuncionario.setSQL( "SELECT idfuncao, nome FROM funcao "
						+ "LEFT JOIN funcao_has_funcionario ON funcao_idfuncao=idfuncao "
						+ "WHERE Funcionario_Pessoa_idPessoa=" + id );
				funcaoFuncionario.atualizeItens();
				
				listaFunction.setSQL( "SELECT idfuncao, nome FROM FUNCAO WHERE idfuncao NOT IN "
						+ "( SELECT idfuncao  FROM funcao LEFT JOIN funcao_has_funcionario "
						+ "ON funcao_idfuncao=idfuncao WHERE Funcionario_Pessoa_idPessoa=" + id + " )" );
				listaFunction.atualizeItens();
				
			}
			
		});
		getContentPane().add(comboBox);
		
		JButton button = new JButton(">");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int selection = listaFunction.getSelectedIndex();
				
				if( comboBox.getSelectedIndex() >= 0 && selection > -1  ){
					
					Conexao.update( "INSERT INTO funcao_has_funcionario ( funcao_idfuncao, Funcionario_Pessoa_idPessoa) "
							+ "VALUES ('"+ listaFunction.getSelectedValue().getId() +"', '"+ comboBox.getSelectedItemId() +"')" );
					
					listaFunction.getModel().remove( listaFunction.getSelectedIndex() );
					funcaoFuncionario.atualizeItens();
					listaFunction.setSelectedIndex( Math.min( selection, listaFunction.getModel().getSize() - 1 ) );
					
				}
				
			}
		});
		button.setBounds(174, 157, 53, 23);
		getContentPane().add(button);
		
		button = new JButton("<");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int selection = funcaoFuncionario.getSelectedIndex();
				
				if( comboBox.getSelectedIndex() >= 0 && selection > -1  ){
					
					Conexao.update( "DELETE FROM funcao_has_funcionario WHERE funcao_idfuncao="+ 
					funcaoFuncionario.getSelectedValue().getId()  +" and Funcionario_Pessoa_idPessoa="+ 
					comboBox.getSelectedItemId() );
					
					funcaoFuncionario.getModel().remove( funcaoFuncionario.getSelectedIndex() );
					listaFunction.atualizeItens();
					funcaoFuncionario.setSelectedIndex( Math.min( selection, funcaoFuncionario.getModel().getSize() - 1 ) );
					
				}
				
			}
		});
		button.setBounds(174, 191, 53, 23);
		getContentPane().add(button);
		
		JLabel label = new JLabel("Funcionario:");
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(43, 16, 95, 14);
		getContentPane().add(label);
		
		label = new JLabel("Fun\u00E7\u00F5es:");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		label.setBounds(57, 49, 66, 14);
		getContentPane().add(label);
		
		label = new JLabel("Fun\u00E7\u00F5es do funcionario:");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		label.setBounds(240, 50, 154, 14);
		getContentPane().add(label);
		
		funcao = new ListaTipos( "Função", new String[]{ "funcao", "idfuncao", "nome" }  );
		
		button = new JButton("Cadastrar fun\u00E7\u00E3o");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Util.showDialog( FuncManager.this, funcao );
				listaFunction.atualizeItens();
				funcaoFuncionario.atualizeItens();
				
			}
		});
		button.setBounds(118, 302, 154, 23);
		getContentPane().add(button);

	}
	
	@Override
	public void setVisible( boolean b ){
		
		comboBox.atualizeItens();
		super.setVisible( b );
		
	}
	
}