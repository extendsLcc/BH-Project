package br.main;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import br.agenda.Agenda;
import br.cadastro.ElementList;
import br.cadastro.FieldManager;
import br.cadastro.ObjectPane;
import br.cadastro.Validation;
import br.cadastro.customfields.CustomComboBox;
import br.cadastro.customfields.CustomTable;
import br.cadastro.customfields.DateChooser;
import br.windows.FuncManager;
import br.windows.Lista;
import br.windows.ListaTipos;
import br.windows.TipoServico;
import br.windows.Conta;

public class Windows {
	
	public static TabbedPane tabbedPane = new TabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT );

	public static Tab Cliente;
	public static Tab Funcionario;
	public static Tab Fornecedor;
	public static Tab Produto;
	public static Tab Servico;
	public static Tab Compra;
	public static Tab Agenda;
	public static Tab Conta;
	public static Tab ListCli;
	public static Tab ListFun;
	public static Tab ListFor;
	public static Tab ListFunc;
	public static Tab ListPro;
	public static Tab ListSev;
	public static Tab ListConta;
	
	public static FuncManager funcoes = new FuncManager();
	
	public static void createTabs(){
	
		{	
			
			//  Cliente
			
			FieldManager camposClientes =  new FieldManager( "Cliente", "Pessoa_idPessoa", "", 287, 0, 460, 373 );
			camposClientes.addFieldManagerChild( pessoaFieldsBuilder() );
			ElementList clienteList = new ElementList( "Clientes", 0, 0, 267, 373, 
					"SELECT Pessoa_idPessoa, nome FROM Pessoa RIGHT JOIN Cliente ON idPessoa=Pessoa_idPessoa" );
			camposClientes.getChild( 0 ).addTextLabel( "Data nasc.:", 281, 67, 76, 14);
			camposClientes.addValidation( new Validation(){
				
				@Override
				public boolean Validate(){
					
					String s = "";
					s += isEmpty(camposClientes, "nome", "Digite um nome!"  );
					
					CustomComboBox city = (CustomComboBox) camposClientes.getFieldEx( "Cidade_idCidade" );
					
					if( city.getSelectedIndex() < 0 ){
						
						if( JOptionPane.showConfirmDialog( null, "A cidade " + city.getSelectedItem() + 
								" não esta cadastrada no sistema!\n Deseja Cadastra-la?", "", JOptionPane.YES_NO_OPTION) == 0 ){
							
							insertCity( camposClientes.getChild(0).getChild(0), city );
							
						}else
							s += "Escolha uma cidade registrada!";
						
					}
					
					if( s.length() > 0 ){
						
						JOptionPane.showMessageDialog( null, s);
						return false;
						
					}
					
					return true;
				}
				
				public boolean isDuplicate(){

					return checkDuplicate( camposClientes, "nome", "Pessoa" );
					
				}
				
			} );
			
			Cliente = new Tab( new ObjectPane(clienteList, camposClientes), "Clientes", null, "Janela de controle de Clientes" );
			
		}
		
		{
			
			//  Funcionario
			
			FieldManager camposFuncionarios = new FieldManager( "Funcionario", "Pessoa_idPessoa", "", 287, 0, 460, 373 );
			camposFuncionarios.addFieldManagerChild( pessoaFieldsBuilder() );
			ElementList funcionariosList = new ElementList( "Funcionários", 0, 0, 267, 373, 
					"SELECT Pessoa_idPessoa, nome FROM Pessoa RIGHT JOIN Funcionario ON idPessoa=Pessoa_idPessoa" );
			camposFuncionarios.getChild( 0 ).addTextLabel( "Data nasc.:", 281, 67, 76, 14);
			camposFuncionarios.addValidation( new Validation(){
				
				@Override
				public boolean Validate(){

					String s = "";
					s += isEmpty(camposFuncionarios , "nome", "Digite um nome!"  );
					s += isEmptyFilter( camposFuncionarios , "telefone_cel", "Digite um numero de celular!", 8 );
					
					CustomComboBox city = (CustomComboBox) camposFuncionarios.getFieldEx( "Cidade_idCidade" );
					
					if( city.getSelectedIndex() < 0 ){
						
						if( JOptionPane.showConfirmDialog( null, "A cidade " + city.getSelectedItem() + 
								" não esta cadastrada no sistema!\n Deseja Cadastra-la?", "", JOptionPane.YES_NO_OPTION) == 0 ){
							
							insertCity( camposFuncionarios.getChild(0).getChild(0), city);
							
						}else
							s += "Escolha uma cidade registrada!";
						
					}
					
					if( s.length() > 0 ){
						
						JOptionPane.showMessageDialog( null, s);
						return false;
					
					}
					
					return true;
					
				}
				
				public boolean isDuplicate(){

					return checkDuplicate( camposFuncionarios, "nome", "Pessoa" );
					
				}
				
			} );
			
			Funcionario = new Tab( new ObjectPane(funcionariosList, camposFuncionarios), "Funcionários", null, "Janela de controle de Funcionarios");
			
		}
		
		{
			
			//  Fornecedor
			
			FieldManager camposFornecedores = new FieldManager( "Fornecedor", "Pessoa_idPessoa", "", 287, 0, 460, 373 );
			camposFornecedores.addFieldManagerChild( pessoaFieldsBuilder() );
			camposFornecedores.addTextLabel( "Data Cadastro:", 277, 67, 100, 14);
			camposFornecedores.getChild( 0 ).addTextLabel( "cadastro:", 281, 67, 76, 14);
			camposFornecedores.getChild( 0 ).getField( "aniversario" ).toggle();
			camposFornecedores.addValidation( new Validation(){
				
				@Override
				public boolean Validate(){
					
					String s = "";
					s += isEmpty( camposFornecedores, "nome", "Digite um nome!");
					s += isEmptyFilter( camposFornecedores, "telefone_cel", "Digite um Celular", 8 );
					s += isEmptyFilter( camposFornecedores , "telefone_fixo", "Digite um numero de telefone", 8 );
					s += isEmpty(camposFornecedores, "endereco", "Digite um endereço" );
					s += isEmpty( camposFornecedores, "bairro", "Digite o Bairro ");

					CustomComboBox city = (CustomComboBox) camposFornecedores.getFieldEx( "Cidade_idCidade" );
					
					if( city.getSelectedIndex() < 0 ){
						
						if( JOptionPane.showConfirmDialog( null, "A cidade " + city.getSelectedItem() + 
								" não esta cadastrada no sistema!\n Deseja Cadastra-la?", "", JOptionPane.YES_NO_OPTION) == 0 ){
							
							insertCity( camposFornecedores.getChild(0).getChild(0), city );
							
						}else
							s += "Escolha uma cidade registrada!";
						
					}
					
					if( s.length() > 0 ){
						
						JOptionPane.showMessageDialog( null, s);
						return false;
						
					}
					
					return true;
					
				}
				
				public boolean isDuplicate(){

					return checkDuplicate( camposFornecedores, "nome", "Pessoa" );
					
				}
				
			});
			ElementList fornecedoresList = new ElementList( "Fornecedores", 0, 0, 267, 373,
					"SELECT Pessoa_idPessoa, nome FROM Pessoa RIGHT JOIN Fornecedor ON idPessoa=Pessoa_idPessoa" );
			
			Fornecedor = new Tab( new ObjectPane(fornecedoresList, camposFornecedores), "Fornecedores", null, "Janela de controle de Fornecedores");
			
		}
		
		{
			
			//  Produto
			
			FieldManager camposProdutos = new FieldManager("produto", "idProduto", "",287, 0, 380, 205 );
			camposProdutos.addTextLabel( "Nome: ", 10, 14, 100, 14 );
			camposProdutos.addInput( "nome", 45, 120, 11, 248, 20, true );
			camposProdutos.addTextLabel( "Tipo de produto: ", 10, 39, 100, 14 );
			camposProdutos.addComboBox( new CustomComboBox( "tipoProduto_idtipoProduto", "SELECT idtipoProduto, nome FROM tipoProduto" ), 120, 36, 192, 20, true );
			camposProdutos.addTextLabel( "Custo: ", 10, 64, 46, 14 );
			camposProdutos.addNumInput( "custo", 7, 2, 120, 61, 138, 20, true );		
			camposProdutos.addTextLabel( "R$ ", 268, 64, 46, 14 );
			camposProdutos.addTextLabel( "Observa\u00E7\u00F5es :", 10, 138, 100, 14 );
			camposProdutos.addTextArea( "obs", 255, 122, 111, 248, 83, true );
			camposProdutos.addTextLabel( "Qtd em Estoque :", 10, 89, 100, 14 );
			camposProdutos.addNumInput( "qtdEstoque", 11, 120, 86, 86, 20, false );	
			ListaTipos tipoProduto = new ListaTipos( "Tipo de Produto", new String[]{ "tipoproduto", "idtipoProduto", "nome" } );
			JButton tmp = new JButton("+");
			tmp.setBounds(322, 35, 46, 22);
			tmp.addActionListener( new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					Util.showDialog( camposProdutos.getPanel(), tipoProduto );
					CustomComboBox cb = ((CustomComboBox) camposProdutos.getField( "tipoProduto_idtipoProduto" ));
					
					if( tipoProduto.lastIndex != -1 ){
						
						cb.selectItemById( tipoProduto.getLastIndex() );
						
					}
					
					cb.atualizeItens();
					
				}
				
			});
			camposProdutos.addComponent( tmp );
			camposProdutos.addValidation( new Validation() {
				
				@Override
				public boolean Validate() {
					
					String s = "";
					s += isEmpty( camposProdutos, "nome", "Digite um nome!");
					
					if( camposProdutos.getFieldEx( "qtdEstoque" ).getValue().toString().length() <= 0 )
						camposProdutos.getFieldEx( "qtdEstoque" ).setValue( "0" );
					
					if( s.length() > 0 ){
						
						JOptionPane.showMessageDialog( null, s);
						return false;
						
					}
					
					return true;
				}

				public boolean isDuplicate(){

					return checkDuplicate( camposProdutos, "nome", "produto" );
					
				}
				
			});
			ElementList produtosList = new ElementList( "Produtos", 0, 0, 267, 205, "SELECT idProduto, nome FROM Produto" );
			
			Produto = new Tab( new ObjectPane( produtosList, camposProdutos ), "Produtos", null, "Janela de controle de Produtos");
			
		}
		
		{
			
			//  Serviço
			
			FieldManager camposServico = new FieldManager( "servicos", "idServicos", "", 287, 0, 460, 373);
			camposServico.addTextLabel( "Nome: ", 10, 14, 100, 14 );
			camposServico.addInput("nome", 45, 147, 11, 299, 20, true);
			camposServico.addTextLabel( "Tipo de Serviço: ", 10, 39, 100, 14 );
			camposServico.addComboBox( new CustomComboBox( "tipoServicos_idtipoServicos", "SELECT idtipoServicos, nome FROM tiposervicos" ), 146, 36, 244, 20, true );
			TipoServico servicos =  new TipoServico();
			JButton tmp = new JButton("+");
			tmp.setBounds( 400, 35, 46, 22);
			tmp.addActionListener( new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					Util.showDialog( camposServico.getPanel(), servicos );
					CustomComboBox cb = ((CustomComboBox) camposServico.getField( "tipoServicos_idtipoServicos" ));
					
					if( servicos.lastIndex != -1 ){
						
						cb.selectItemById( servicos.getLastIndex() );
						
					}
					
					cb.atualizeItens();
					
				}
				
			});
			camposServico.addComponent( tmp );
			camposServico.addTextLabel( "Preço de Venda: ", 10, 64, 100, 14 );
			camposServico.addNumInput("precoVenda", 7, 2, 146, 64, 84, 20, true);
			camposServico.addTextLabel( "R$", 240, 67, 46, 14 );
			camposServico.addTextLabel( "Comissão: ", 10, 89, 78, 14 );
			camposServico.addNumInput("comissao", 5, 146, 89, 84, 20, true);
			camposServico.addTextLabel( "%", 240, 92, 46, 14 );
			camposServico.addTextLabel( "Duração Aproximada: ", 10, 117, 136, 14 );
			camposServico.addNumInput("duracaoAproximada", 4, 146, 117, 84, 20, true);
			camposServico.addTextLabel( "minutos", 240, 120, 58, 14 );
			camposServico.addTextLabel( "Produtos usados: ", 10, 190, 112, 14 );
			camposServico.addTextLabel( "Observações: ", 10, 313, 100, 14 );
			camposServico.addTextArea("observacoes", 255, 147, 283, 299, 79, true );
			CustomTable ct = new CustomTable( new Object[]{ "Produto", "qtd Usada" }, 
					"SELECT Produto_idProduto, qtdUsada FROM produto_has_servicos WHERE Servicos_idServicos=",
					new String[] { "produto_has_servicos", "Servicos_idServicos", "Produto_idProduto", "qtdUsada" },
					147, 148, 268, 122);
			camposServico.addTableField( ct );
			Util.setJTableColumnsWidth( ct.getTable(), 273, 75, 25 );
			camposServico.addValidation( new Validation() {
				
				@Override
				public boolean Validate() {

					String s = "";
					s += isEmpty( camposServico, "nome", "Digite um nome!");
					s += isEmpty( camposServico, "precoVenda", "Digite o valor de venda do serviço!");
					
					if( s.length() > 0 ){
						
						JOptionPane.showMessageDialog( null, s);
						return false;
						
					}
					
					return true;
					
				}

				public boolean isDuplicate(){

					return checkDuplicate( camposServico, "nome", "servicos" );
					
				}
			});
			ElementList servicosList = new ElementList( "Serviços", 0, 0, 267, 373, "SELECT idServicos, nome FROM Servicos" );

			Servico = new Tab( new ObjectPane( servicosList, camposServico ), "Serviços", null, "Janela de controle de Serviços");
			
		}
		
		{
			
			//  Compra
			//SELECT CONCAT( SUBSTRING( descrip , 1, 10 ) , '... ' , dataVencimento, ' ', pessoa.nome ) as nome FROM compra LEFT JOIN fornecedor ON Fornecedor_Pessoa_idPessoa=Pessoa_idPessoa
			//LEFT JOIN pessoa ON Pessoa_idPessoa=idPessoa
			
			
			FieldManager camposCompra = new FieldManager( "compra", "idCompra", "", 287, 0, 476, 345);// 446
			camposCompra.addTextLabel( "Descrição: ", 10, 16, 100, 14 );
			camposCompra.addTextArea("descrip", 85, 136, 11, 330, 55, true );
			camposCompra.addTextLabel( "Data do Vencimento: ", 10, 77, 127, 14 );
			camposCompra.addTextLabel( "Data da Quitação: ", 10, 105, 127, 14 );
			camposCompra.addDateInput("dataVencimento", 136, 74, 127, 20, true);
			camposCompra.addTextLabel( "Fornecedor: ", 10, 133, 100, 14 );
			camposCompra.addComboBox( new CustomComboBox( "Fornecedor_Pessoa_idPessoa", 
					"SELECT idPessoa, nome FROM pessoa RIGHT JOIN fornecedor ON Pessoa_idPessoa=idPessoa" ), 136, 130, 192, 20, true );
			camposCompra.addTextLabel( "Produtos Comprados: ", 10, 186, 127, 14 );
			camposCompra.addDateInput("dataQuitacao", 136, 102, 127, 20, false);
			camposCompra.addTextLabel( "Valor Total: ", 10, 317, 94, 14 );
			camposCompra.addTextLabel( "R$ ", 273, 317, 36, 14 );
			camposCompra.addNumInput("valorTotal", 7, 2, 136, 314, 127, 20, false);
			CustomTable ct = new CustomTable( new Object[]{ "Produto", "Custo", "Qtd Comprada" }, 
					"SELECT Produto_idProduto, custoPorProduto, qtdComprada FROM produto_has_compra WHERE Compra_idCompra=",
					new String[]{ "produto_has_compra", "Compra_idCompra", "Produto_idProduto", "custoPorProduto", "qtdComprada" },
					136, 158, 300, 150); // 56
			Util.setJTableColumnsWidth( ct.getTable(), 272, 47, 13, 30 );
			camposCompra.addTableField( ct );
			camposCompra.addValidation( new Validation() {
				
				@Override
				public boolean Validate() {
					
					String s = "";
					s += isEmpty( camposCompra, "descrip", "Escreva uma descrição! ");
					if( ( (DateChooser) camposCompra.getFieldEx( "dataVencimento" )).getDate() == null )
						s += "Escolha a data de vencimento da conta!";
					//s += isEmpty( camposCompra, "", "Escolha a data de vencimento da conta!");

					if( s.length() > 0 ){
						
						JOptionPane.showMessageDialog( null, s);
						return false;
						
					}
					
					return true;
					
				}
				
				public boolean isDuplicate(){

					return false;
					
				}
			});
			ElementList comprasList = new ElementList( "Compras", 0, 0, 267, 345, "SELECT idCompra, descrip FROM Compra" );
			
			Compra = new Tab( new ObjectPane( comprasList, camposCompra ), "Compras", null, "Janela de controle de Compras");
			
		}
		
		{	// Agenda
			
			Agenda = new Tab( new Agenda(), "Agenda", null, "Janela da Agenda" );
			
		}
		
		{	// Conta
			
			Conta = new Tab( new Conta(), "Contas", null, "Janela de Contas dos Clientes" );
			
		}
		
		{	// List Cliente
			
			ListCli = new Tab( new Lista( new Object[]{ "Nome", "Celular", "Telefone", "E-mail", "Data Nasc.", "Endereco", "Observações" }, 
					"SELECT idPessoa, pessoa.nome, telefone_cel, telefone_fixo, email, aniversario, "
					+ "concat( endereco, ' ' , numero, ' ', bairro, ' ' , bairro, ' ' , CEP, ' ', Name, UF ) AS endereco, obs "
					+ "FROM estado RIGHT JOIN cidade ON Estado_idEstado=idEstado RIGHT JOIN endereco ON Cidade_idCidade=idCidade"
					+ " RIGHT JOIN pessoa ON Endereco_idEndereco=idEndereco RIGHT JOIN cliente ON idPessoa=Pessoa_idPessoa")
			, "Lista de Clientes", null, "Lista de Clientes" );
			
		}
		
		{	// List Funcionario
			
			ListFun = new Tab( new Lista( new Object[]{ "Nome", "Celular", "Telefone", "E-mail", "Data Nasc.", "Endereco", "Observações" }, 
					"SELECT idPessoa, pessoa.nome, telefone_cel, telefone_fixo, email, aniversario, "
					+ "concat( endereco, ' ' , numero, ' ', bairro, ' ' , bairro, ' ' , CEP, ' ', Name, UF ) AS endereco, obs "
					+ "FROM estado RIGHT JOIN cidade ON Estado_idEstado=idEstado RIGHT JOIN endereco ON Cidade_idCidade=idCidade"
					+ " RIGHT JOIN pessoa ON Endereco_idEndereco=idEndereco RIGHT JOIN funcionario ON idPessoa=Pessoa_idPessoa")
			, "Lista de Funcionarios", null, "Lista de Funcionarios" );
			
		}
		
		{	// List Fornecedor
			
			ListFor = new Tab( new Lista( new Object[]{ "Nome", "Celular", "Telefone", "E-mail", "Data Nasc.", "Endereco", "Observações" }, 
					"SELECT idPessoa, pessoa.nome, telefone_cel, telefone_fixo, email, aniversario, "
					+ "concat( endereco, ' ' , numero, ' ', bairro, ' ' , bairro, ' ' , CEP, ' ', Name, UF ) AS endereco, obs "
					+ "FROM estado RIGHT JOIN cidade ON Estado_idEstado=idEstado RIGHT JOIN endereco ON Cidade_idCidade=idCidade"
					+ " RIGHT JOIN pessoa ON Endereco_idEndereco=idEndereco RIGHT JOIN fornecedor ON idPessoa=Pessoa_idPessoa")
			, "Lista de Fornecedor", null, "Lista de Fornecedor" );
			
		}
		
		{	// List Serviços
			
			ListSev = new Tab( new Lista( new Object[]{ "Nome", "Tipo do Serviço", "Preço de Venda", "Comissão",
					"Duração Aproximada", "Observações" },
					"SELECT idServicos, servicos.nome, tipoServicos.nome, precoVenda, comissao, duracaoAproximada, "
					+ "observacoes FROM servicos LEFT JOIN tiposervicos ON tipoServicos_idtipoServicos=IdtipoServicos")
					, "Lista de Serviços" , null, "Lista de Serviços" );
			
			
		}
		
		{	//Produtos
			
			ListPro = new Tab( new Lista( new Object[]{ "Nome", "Tipo do Produto", "Custo", "Qtd em Estoque", "Observações" },
					"SELECT idProduto, produto.nome, tipoproduto.nome as tipoproduto, custo, qtdEstoque, obs FROM produto"
					+ " LEFT JOIN tipoproduto ON tipoProduto_idtipoProduto=idtipoProduto"), "Lista de Produtos", null, "Lista de Produtos" );
			
		}
		
		{	// Contas
			
			ListConta =  new Tab( new Lista( new Object[]{ "nome", "valor", "dataVencimento", "dataQuitacao"}, 
					"SELECT idConta, pessoa.nome, valor, dataVencimento, dataQuitacao  FROM conta LEFT JOIN"
					+ " horarios_has_conta ON Conta_idConta=idConta LEFT JOIN horarios ON Horarios_idHorarios=idHorarios"
					+ " LEFT JOIN cliente ON Cliente_Pessoa_idPessoa=Pessoa_idPessoa LEFT JOIN pessoa ON Pessoa_idPessoa=idPessoa" ), 
					"Lista de Contas", null, "Lista de Contas" );
			
			
		}
		
		
	}
	
	private static FieldManager pessoaFieldsBuilder(){
		
		FieldManager camposPessoa = new FieldManager( "Pessoa", "idPessoa", "Pessoa_idPessoa", 0, 0, 460, 373 );
		camposPessoa.addTextLabel( "Nome:", 10, 14, 100, 14);
		camposPessoa.addInput("nome", 45, 120, 11, 326, 20, true);
		camposPessoa.addTextLabel( "Celular:", 10, 42, 46, 14);
		camposPessoa.addInput("telefone_cel", "(##) ####-####", 120, 39, 110, 20, true);
		camposPessoa.addTextLabel( "Telefone:", 240, 42, 53, 14);
		camposPessoa.addInput("telefone_fixo", "(##) ####-####", 303, 39, 143, 20, true);
		camposPessoa.addTextLabel( "E-mail:", 10, 67, 46, 14);
		camposPessoa.addInput("email", 45, 120, 64, 151, 20, true);
		//camposPessoa.addTextLabel( "Anivers\u00E1rio:", 281, 67, 76, 14);// cliente, funcionario > aniversario / funcionario > data de cadastro
		camposPessoa.addDateInput("aniversario", 354, 64, 91, 20, true);    		
		camposPessoa.addTextLabel( "Observa\u00E7\u00F5es:", 10, 313, 100, 14);
		camposPessoa.addTextLabel( "Endereço:", 10, 163, 66, 14 );
		camposPessoa.addTextArea( "obs", 255, 120, 283, 326, 79, true );	
		
		FieldManager enderecoPessoa = new FieldManager( "Endereco", "idEndereco", "Endereco_idEndereco", 120, 92, 326, 180 );
		enderecoPessoa.addTextLabel( "Endere\u00E7o:", 10, 11, 74, 14);
		enderecoPessoa.addInput("endereco", 45, 94, 8, 222, 20, true);
		enderecoPessoa.addTextLabel( "N\u00FAmero:", 10, 36, 74, 14);
		enderecoPessoa.addInput("numero", 4, 94, 34, 35, 20, true);
		enderecoPessoa.addTextLabel( "Bairro:", 139, 36, 50, 14);
		enderecoPessoa.addInput("bairro", 45, 186, 34, 130, 20, true);
		enderecoPessoa.addTextLabel( "Cidade:", 10, 61, 55, 14);
		enderecoPessoa.addTextLabel( "CEP:", 10, 89, 55, 14);
		enderecoPessoa.addInput("CEP", "#####-###", 94, 86, 105, 20, true);
		enderecoPessoa.addTextLabel( "Estado:", 209, 89, 55, 14);
		enderecoPessoa.addTextLabel( "Complemento:", 8, 136, 84, 14);
		enderecoPessoa.addTextArea( "complemento", 255, 94, 117, 222, 52, true);			
		camposPessoa.addFieldManagerChild( enderecoPessoa );
		final CustomComboBox estado = new CustomComboBox( "UF", "SELECT idEstado, UF FROM estado" );
		estado.setBounds( 265, 86, 48, 20 );
		enderecoPessoa.addComponent( estado );
		CustomComboBox cidade = new CustomComboBox( "Cidade_idCidade", "SELECT idCidade, name FROM cidade" );
		enderecoPessoa.addComboBox( cidade, 94, 60, 222, 20, true );
		cidade.setEditable( true );
		AutoCompleteDecorator.decorate( cidade );
		cidade.addActionListener( new ActionListener() {
			
			CustomComboBox c = estado;
			
			@Override
			public void actionPerformed(ActionEvent e) {

				CustomComboBox cidadeBox = (CustomComboBox) e.getSource();
				if( cidadeBox.getSelectedItem() == null ) return;
				String cidade = cidadeBox.getSelectedItem().toString();	
				cidadeBox.getSelectedItemId();
				
				if( cidadeBox.getSelectedIndex() >= 0 ){
				
					try {
						
						ResultSet rs = Conexao.query( "SELECT UF from estado LEFT JOIN cidade ON Estado_idEstado=idEstado WHERE name='"+ cidade +"'" );
						rs.next();
						String uf = rs.getString( 1 );
						
						for( int i = 0; i < c.getItemCount(); i++ ){
	
							if( c.getItemAt( i ).toString().equalsIgnoreCase( uf ) ){
								
								c.setSelectedIndex( i );
								c.setEnabled( false );
								
							}
							
						}									
						
					} catch (SQLException e1) {
						
					}
				
				}else 
					c.setEnabled( true );	
				
			}
			
		});
		estado.setEnabled( false );
		
		return camposPessoa;
		
	}
	
	public static void insertCity( FieldManager fm, CustomComboBox city ){
		
		Component[] comps = fm.getPanel().getComponents();
		
		for( Component c : comps ){
			
			try{
				
				CustomComboBox cb = (CustomComboBox) c;
				
				if( cb.getId().equalsIgnoreCase( "UF" ) ){
					
					int index = Conexao.insert( "INSERT INTO cidade( name, Estado_idEstado ) VALUES ( '"
							+ city.getSelectedItem().toString() +"', '"+ cb.getSelectedItemId() +"' )" );
					city.atualizeItens();
					city.selectItemById( index );
					break;
					
				}
				
			}catch( Exception e ){}
			
		}
		
	}
	
}
