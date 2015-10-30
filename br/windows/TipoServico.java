package br.windows;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.cadastro.customfields.ComboBox;
import br.cadastro.customfields.LengthRestrictedDocument;
import br.main.Conexao;

public class TipoServico extends ListaTipos {
	
	private JPanel inputs;
	private ComboBox funcao;
	private JTextField nome;
	
	public TipoServico() {

		super( "Tipo serviço", new String[]{ "tiposervicos", "idtipoServicos", "nome", "Funcao_idfuncao" } );
		inputs = new JPanel();
		inputs.add( new JLabel( "Nome: " ) );
		nome = new JTextField();
		nome.setDocument( new LengthRestrictedDocument( 25 ));
		nome.setColumns( 10 );
		inputs.add( nome );
		inputs.add( new JLabel( "Função:" ) );
		funcao = new ComboBox( "SELECT idFuncao, nome FROM funcao" );
		inputs.add( funcao );
		
	}
	
	@Override
	public void insert(){
		
		if( JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, inputs, "Insira os dados do tipo de serviço.", JOptionPane.OK_CANCEL_OPTION) ){
			
			lastIndex = Conexao.insert( "INSERT INTO tiposervicos( nome,  Funcao_idfuncao) VALUES ( '"+ nome.getText() +"', '"+ funcao.getSelectedItemId() +"' )" );
			JOptionPane.showMessageDialog( this, "Tipo de seriviço \"" + nome.getText() + "\" foi Cadastrado com sucesso!" );
			list.atualizeItens();
			clear();
			
		}
		
	}

	@Override
	public void alter(){
		
		try{
			
			nome.setText( list.getSelectedValue().getName() );
			// pog BRUTA
			ResultSet rs = Conexao.query( "SELECT Funcao_idfuncao FROM  tiposervicos WHERE idtiposervicos=" + list.getSelectedValue().getId() );
			rs.next();
			funcao.selectItemById( rs.getInt( 1 ) );
			
		}catch(Exception e ){}
		
		if( JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, inputs, "Insira os dados do tipo de serviço.", JOptionPane.OK_CANCEL_OPTION) ){
			
			Conexao.update( "UPDATE tiposervicos SET nome='"+ nome.getText() +"', Funcao_idFuncao='"+ funcao.getSelectedItemId() + "' WHERE idtiposervicos=" 
					+ list.getSelectedValue().getId() );
			list.atualizeItens();
			clear();
			
		}
		
	}
	
	@Override
	public void delete(){
		
		if( JOptionPane.showConfirmDialog( this, "Você tem certeza que deseja excluir o tipo de servico: " 
				+ list.getSelectedValue().getName(), "", JOptionPane.YES_NO_OPTION  ) == 0  ){
			
			try{
				
				Conexao.updateHandled( "DELETE FROM tiposervicos WHERE idtiposervicos=" + list.getSelectedValue().getId()  );
				JOptionPane.showMessageDialog( this, "O tipo de serviço  \"" + nome + "\" foi excluido com sucesso!" );
				list.atualizeItens();
				
			}catch( SQLException e ){
				
				JOptionPane.showMessageDialog( this, "O tipo de serviço  \"" + list.getSelectedValue().getName() +
						"\" não pode ser deletado porque esta sendo usado por outro Item." );
				
			}
			
		}
		
	}
	
	private void clear(){
		
		nome.setText("");
		
	}

}
