package br.tst;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;
import javax.swing.BoxLayout;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import br.tst.customFields.CustomComboBox;
import br.tst.customFields.CustomTable;


public class Window extends JFrame {

	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} 

	/**
	 * Create the frame.
	 */
	
	ListaTipos tipoProduto ,
	tipoServico/* = new ListaTipos( "Tipo de Serviço", new String[]{ "tiposervico", "idtipoServico", "nome" } )*/;
	
	public FieldManager pessoaFieldsBuilder( JPanel panel ){
		
		FieldManager camposPessoa = new FieldManager( "Pessoa", "idPessoa", "", 376, 50, 460, 373 );
		camposPessoa.addToPanel( panel );
		camposPessoa.addTextLabel( "Nome:", 10, 14, 100, 14);
		camposPessoa.addInput("nome", 120, 11, 326, 20);
		camposPessoa.addTextLabel( "Celular:", 10, 42, 46, 14);
		camposPessoa.addInput("telefone_cel", 120, 39, 110, 20);
		camposPessoa.addTextLabel( "Telefone:", 240, 42, 53, 14);
		camposPessoa.addInput("telefone_fixo", 303, 39, 143, 20);
		camposPessoa.addTextLabel( "E-mail:", 10, 67, 46, 14);
		camposPessoa.addInput("email", 120, 64, 151, 20);
		//camposPessoa.addTextLabel( "Anivers\u00E1rio:", 281, 67, 76, 14);// cliente, funcionario > aniversario / funcionario > data de cadastro
		camposPessoa.addInput("aniversario", 364, 64, 81, 20);    		
		camposPessoa.addTextLabel( "Observa\u00E7\u00F5es:", 10, 313, 100, 14);
		camposPessoa.addTextLabel( "Endereço:", 10, 163, 66, 14 );
		camposPessoa.addTextArea( "obs", 120, 283, 326, 79 );
		
		FieldManager enderecoPessoa = new FieldManager( "Endereco", "idEndereco", "Endereco_idEndereco", 120, 92, 326, 180 );
		enderecoPessoa.addTextLabel( "Endere\u00E7o:", 10, 11, 74, 14);
		enderecoPessoa.addInput("endereco", 94, 8, 222, 20);
		enderecoPessoa.addTextLabel( "N\u00FAmero:", 10, 36, 74, 14);
		enderecoPessoa.addInput("numero", 94, 34, 42, 20);
		enderecoPessoa.addTextLabel( "Bairro:", 146, 36, 50, 14);
		enderecoPessoa.addInput("bairro", 193, 34, 123, 20);
		enderecoPessoa.addTextLabel( "Cidade:", 10, 61, 55, 14);
		enderecoPessoa.addTextLabel( "CEP:", 10, 89, 55, 14);
		enderecoPessoa.addInput("CEP", 94, 86, 105, 20);
		enderecoPessoa.addTextLabel( "Estado:", 209, 89, 55, 14);
		enderecoPessoa.addTextLabel( "Complemento:", 8, 136, 84, 14);
		enderecoPessoa.addTextArea( "complemento", 94, 117, 222, 52 );			
		camposPessoa.addFieldManagerChild( enderecoPessoa );
		final CustomComboBox estado = new CustomComboBox( "", "SELECT idEstado, UF FROM estado", 265, 86, 48, 20 );
		enderecoPessoa.addComponent( estado );
		CustomComboBox cidade = new CustomComboBox( "Cidade_idCidade", "SELECT idCidade, name FROM cidade",  94, 60, 222, 20 );
		enderecoPessoa.addComboBox( cidade );
		cidade.setEditable( true );
		AutoCompleteDecorator.decorate( cidade );
		cidade.addActionListener( new ActionListener() {
			
			CustomComboBox c = estado;
			
			@Override
			public void actionPerformed(ActionEvent e) {

				CustomComboBox cidadeBox = (CustomComboBox) e.getSource();
				String cidade = cidadeBox.getSelectedItem().toString();	
				cidadeBox.getSelectedItemId();
				
				if( cidadeBox.getSelectedIndex() > 0 ){
				
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
		
		return camposPessoa;
		
	}
	
	public Window() {
		setTitle("Gerenciamento de Hor\u00E1rios - Sal\u00E3o Korpus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize( new Dimension( 952, 652 ) );
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		try{
			
			Conexao.initConnection();
			
		}catch( Exception e ){
			
			System.out.println( "Falha na conexão!" );
			
		}

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnCadastros = new JMenu(" Cadastros");
		mnCadastros.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnCadastros);
		
		JMenuItem mntmCadastroDeCl = new JMenuItem("Clientes");
		mntmCadastroDeCl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnCadastros.add(mntmCadastroDeCl);
		
		JMenuItem mntmFuncionarios = new JMenuItem("Funcionários");
		mntmFuncionarios.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnCadastros.add(mntmFuncionarios);
		
		JMenuItem mntmProdutos = new JMenuItem("Produtos");
		mntmProdutos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnCadastros.add(mntmProdutos);
		
		JMenuItem mntmServios = new JMenuItem("Servi\u00E7os");
		mntmServios.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnCadastros.add(mntmServios);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Fornecedores");
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnCadastros.add(mntmNewMenuItem);
		
		JMenuItem mntmCompras = new JMenuItem("Compras");
		mntmCompras.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnCadastros.add(mntmCompras);
		
		JMenu mnAgenda = new JMenu("Agenda");
		mnAgenda.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnAgenda);
		
		JMenuItem mntmAbrirAgenda = new JMenuItem("Abrir Agenda");
		mntmAbrirAgenda.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnAgenda.add(mntmAbrirAgenda);
		
		JMenuItem mntmLocalizar = new JMenuItem("Localizar Reserva");
		mntmLocalizar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnAgenda.add(mntmLocalizar);
		
		JMenu mnListas = new JMenu("Listas");
		mnListas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnListas);
		
		JMenuItem mntmClientes = new JMenuItem("Clientes");
		mntmClientes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnListas.add(mntmClientes);
		
		JMenuItem mntmServios_1 = new JMenuItem("Servi\u00E7os");
		mntmServios_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnListas.add(mntmServios_1);
		
		JMenuItem mntmFuncionarios_1 = new JMenuItem("Funcionarios");
		mntmFuncionarios_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnListas.add(mntmFuncionarios_1);
		
		JMenuItem mntmProdutos_1 = new JMenuItem("Produtos");
		mntmProdutos_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnListas.add(mntmProdutos_1);
		
		JMenuItem mntmFornecedores = new JMenuItem("Fornecedores");
		mntmFornecedores.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnListas.add(mntmFornecedores);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Contas");
		mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnListas.add(mntmNewMenuItem_1);
		
		JMenu mnEstoque = new JMenu("Estoque");
		mnEstoque.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnEstoque);
		
		JMenuItem mntmEstoqueAtual = new JMenuItem("Estoque Atual");
		mntmEstoqueAtual.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnEstoque.add(mntmEstoqueAtual);
		
		JMenuItem mntmLa = new JMenuItem("Lan\u00E7ar Compra");
		mntmLa.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnEstoque.add(mntmLa);
		
		JMenu mnContas = new JMenu("Contas");
		mnContas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnContas);
		
		JMenuItem mntmContasAReceber = new JMenuItem("Contas a Receber");
		mntmContasAReceber.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnContas.add(mntmContasAReceber);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Contas Recebidas");
		mntmNewMenuItem_2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnContas.add(mntmNewMenuItem_2);
		
		JMenuItem mntmContasAPagar = new JMenuItem("Contas a Pagar");
		mntmContasAPagar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnContas.add(mntmContasAPagar);
		
		JMenuItem mntmRegistrarRetirada = new JMenuItem("Registrar Retirada");
		mntmRegistrarRetirada.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnContas.add(mntmRegistrarRetirada);
		
		JMenu mnRelatorios = new JMenu("Relat\u00F3rios");
		mnRelatorios.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnRelatorios);
		
		JMenu mnOptions = new JMenu("Op\u00E7\u00F5es");
		mnOptions.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnOptions);
		
		JMenuItem mntmPreferencias = new JMenuItem("Preferencias");
		mntmPreferencias.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnOptions.add(mntmPreferencias);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Sobre");
		mntmNewMenuItem_3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnOptions.add(mntmNewMenuItem_3);
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnOptions.add(mntmSair);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Clientes", null, panel, null);
		panel.setLayout(null);
		
		//!=======================================================!important!================================================================================
		
		FieldManager camposClientes = pessoaFieldsBuilder( panel );
		ElementList clienteList = new ElementList( panel, "Clientes", 89, 50, 267, 373, 
				"SELECT Pessoa_idPessoa, nome FROM Pessoa RIGHT JOIN Cliente ON idPessoa=Pessoa_idPessoa" );
		clienteList.setFields( camposClientes );
		camposClientes.addTextLabel( "Anivers\u00E1rio:", 281, 67, 96, 14);

		//Conexao.insert( "UPDATE `pessoa` SET `aniversario`='"+ (rd( 30 )+1) + "/" + (rd(11)+1) + "/" + (rd( 10 )+ 1980)+"', `obeservacoes`=CONCAT('Observações sobre ', pessoa.nome ), `telefone_cel`='"+ rd(99999999) +"', `telefone_fixo`='"+ rd(99999999) +"', `email`=CONCAT(REPLACE( pessoa.nome, ' ', '_'), '@mail.com' ) WHERE `idPessoa`='"+i+"'" );
		//UPDATE `pessoa` SET `aniversario`=CONCAT( ROUND( RAND()*30 ), '/', ROUND( RAND()*12 ), '/', ROUND( RAND()*20+1980 ) ), `obeservacoes`=CONCAT('Observações sobre ', pessoa.nome ), `telefone_cel`=ROUND( RAND()*99999999 ), `telefone_fixo`=ROUND( RAND()*99999999 ), `email`=CONCAT(REPLACE( pessoa.nome, ' ', '_'), '@mail.com' ) WHERE idpessoa!=1;
		//UPDATE endereco SET numero=ROUND( RAND()*9999), CEP=ROUND( RAND()*99999999 ), cidade_idCidade=ROUND( RAND()*4 ) WHERE idendereco!=1;
		
		JPanel panel_8 = new JPanel();
		panel_8.setBounds(89, 434, 747, 23);
		panel.add(panel_8);
		panel_8.setLayout(null);
		
		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.setBounds(164, 0, 99, 23);
		panel_8.add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("Alterar");
		btnNewButton_2.setBounds(273, 0, 89, 23);
		panel_8.add(btnNewButton_2);
		
		JButton btnNewButton_1 = new JButton("Excluir");
		btnNewButton_1.setBounds(372, 0, 89, 23);
		panel_8.add(btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton("Fechar");
		btnNewButton_3.setBounds(471, 0, 89, 23);
		panel_8.add(btnNewButton_3);
		
		JPanel panel_1 = new JPanel();
		panel_1.setToolTipText("");
		tabbedPane.addTab("Funcionários", null, panel_1, null);
		panel_1.setLayout(null);

		//!=======================================================!important!================================================================================		
		
		FieldManager camposFuncionarios = pessoaFieldsBuilder( panel_1 );
		ElementList funcionariosList = new ElementList( panel_1, "Funcionários", 89, 50, 267, 373, 
				"SELECT Pessoa_idPessoa, nome FROM Pessoa RIGHT JOIN Funcionario ON idPessoa=Pessoa_idPessoa" );
		funcionariosList.setFields( camposFuncionarios );
		camposFuncionarios.addTextLabel( "Anivers\u00E1rio:", 281, 67, 76, 14);
		
		JPanel panel_9 = new JPanel();
		panel_9.setLayout(null);
		panel_9.setBounds(89, 434, 747, 23);
		panel_1.add(panel_9);
		
		JButton button_1 = new JButton("Cadastrar");
		button_1.setBounds(164, 0, 99, 23);
		panel_9.add(button_1);
		
		JButton button_2 = new JButton("Alterar");
		button_2.setBounds(273, 0, 89, 23);
		panel_9.add(button_2);
		
		JButton button_3 = new JButton("Excluir");
		button_3.setBounds(372, 0, 89, 23);
		panel_9.add(button_3);
		
		JButton button_4 = new JButton("Fechar");
		button_4.setBounds(471, 0, 89, 23);
		panel_9.add(button_4);
		
		JPanel panel_10 = new JPanel();
		panel_10.setLayout(null);
		tabbedPane.addTab("Produtos", null, panel_10, null);
		
		//!----
		
		FieldManager camposProdutos = new FieldManager("produto", "idProduto", "",421, 49, 380, 205 );
		camposProdutos.addTextLabel( "Nome: ", 10, 14, 100, 14 );
		camposProdutos.addInput( "nome", 120, 11, 248, 20 );
		camposProdutos.addTextLabel( "Tipo de produto: ", 10, 39, 100, 14 );
		camposProdutos.addComboBox( new CustomComboBox( "tipoProduto_idtipoProduto", "SELECT idtipoProduto, nome FROM tipoProduto", 120, 36, 192, 20 ) );
		camposProdutos.addTextLabel( "Custo: ", 10, 64, 46, 14 );
		camposProdutos.addInput( "custo", 120, 61, 138, 20 );		
		camposProdutos.addTextLabel( "R$ ", 268, 64, 46, 14 );
		camposProdutos.addTextLabel( "Observa\u00E7\u00F5es :", 10, 138, 100, 14 );
		camposProdutos.addTextArea( "obs", 122, 111, 248, 83 );
		camposProdutos.addTextLabel( "Qtd em Estoque :", 10, 89, 100, 14 );
		camposProdutos.addInput( "qtdEstoque", 120, 86, 86, 20 );	
		camposProdutos.getInputById( "qtdEstoque" ).setEditable( false );
		tipoProduto = new ListaTipos( "Tipo de Produto", new String[]{ "tipoproduto", "idtipoProduto", "nome" } );
		JButton tmp = new JButton("+");
		tmp.setBounds(322, 35, 46, 22);
		tmp.addActionListener( new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Util.showDigalog( tipoProduto );
				
			}
			
		});
		camposProdutos.addComponent( tmp );
		camposProdutos.addToPanel( panel_10 );
		ElementList produtosList = new ElementList( panel_10, "Produtos", 134, 49, 267, 205, 
				"SELECT idProduto, nome FROM Produto" );
		produtosList.setFields( camposProdutos );
		
		JPanel panel_14 = new JPanel();
		panel_14.setLayout(null);
		panel_14.setBounds(134, 265, 667, 23);
		panel_10.add(panel_14);
		
		JButton button_6 = new JButton("Cadastrar");
		button_6.setBounds(135, 0, 99, 23);
		panel_14.add(button_6);
		
		JButton button_7 = new JButton("Alterar");
		button_7.setBounds(244, 0, 89, 23);
		panel_14.add(button_7);
		
		JButton button_8 = new JButton("Excluir");
		button_8.setBounds(343, 0, 89, 23);
		panel_14.add(button_8);
		
		JButton button_9 = new JButton("Fechar");
		button_9.setBounds(442, 0, 89, 23);
		panel_14.add(button_9);
		
		//!----
		
		JPanel panel_12 = new JPanel();
		panel_12.setLayout(null);
		tabbedPane.addTab("Serviços", null, panel_12, null);
		
		FieldManager camposServico = new FieldManager( "servicos", "idServicos", "", 376, 50, 460, 337);
		camposServico.addTextLabel( "Nome: ", 10, 14, 100, 14 );
		camposServico.addInput("nome", 147, 11, 299, 20);
		camposServico.addTextLabel( "Tipo de Serviço: ", 10, 39, 100, 14 );
		camposServico.addComboBox( new CustomComboBox( "tipoServicos_idtipoServicos", "SELECT idtipoServicos, nome FROM tiposervicos", 146, 36, 244, 20 ) );
		camposServico.addTextLabel( "Preço de Venda: ", 10, 64, 100, 14 );
		camposServico.addInput("precoVenda", 146, 64, 84, 20);
		camposServico.addTextLabel( "R$", 240, 67, 46, 14 );
		camposServico.addTextLabel( "Comissão: ", 10, 89, 78, 14 );
		camposServico.addInput("comissao", 146, 89, 84, 20);
		camposServico.addTextLabel( "%", 240, 92, 46, 14 );
		camposServico.addTextLabel( "Duração Aproximada: ", 10, 117, 136, 14 );
		camposServico.addInput("duracaoAproximada", 146, 117, 84, 20);
		camposServico.addTextLabel( "minutos", 240, 120, 58, 14 );
		camposServico.addTextLabel( "Produtos usados: ", 10, 190, 112, 14 );
		camposServico.addTextLabel( "Observações: ", 10, 276, 100, 14 );
		camposServico.addTextArea("observacoes", 147, 256, 299, 70 );
		
		//SELECT produto.nome, qtdUsada FROM produto_has_servicos RIGHT JOIN produto ON Produto_idProduto=idProduto WHERE Servicos_idServicos=1;
		
		CustomTable ct = new CustomTable( new Object[]{ "produto", "qtd Usada" }, 
				"SELECT Produto_idProduto, qtdUsada FROM produto_has_servicos WHERE Servicos_idServicos=",
				/*"produto_has_servicos ( Servicos_idServicos, Produto_idProduto,  qtdUsada"
				,*/ 147, 148, 243, 97);
		camposServico.addTableField( ct );
		
		camposServico.addToPanel( panel_12 );
		ElementList servicosList = new ElementList( panel_12, "Serviços", 89, 50, 267, 337,
				"SELECT idServicos, nome FROM Servicos" );
		servicosList.setFields( camposServico );
		
		JPanel panel_18 = new JPanel();
		panel_18.setLayout(null);
		panel_18.setBounds(89, 398, 747, 23);
		panel_12.add(panel_18);
		
		JButton button_11 = new JButton("Cadastrar");
		button_11.setBounds(164, 0, 99, 23);
		panel_18.add(button_11);
		
		JButton button_12 = new JButton("Alterar");
		button_12.setBounds(273, 0, 89, 23);
		panel_18.add(button_12);
		
		JButton button_13 = new JButton("Excluir");
		button_13.setBounds(372, 0, 89, 23);
		panel_18.add(button_13);
		
		JButton button_14 = new JButton("Fechar");
		button_14.setBounds(471, 0, 89, 23);
		panel_18.add(button_14);
		
		JPanel panel_16 = new JPanel();
		panel_16.setLayout(null);
		panel_16.setToolTipText("");
		tabbedPane.addTab("Fornecedores", null, panel_16, null);

		//!=======================================================!important!================================================================================		
		
		FieldManager camposFornecedores = pessoaFieldsBuilder( panel_16 );
		ElementList fornecedoresList = new ElementList( panel_16, "Fornecedores", 89, 50, 267, 392,
				"SELECT Pessoa_idPessoa, nome FROM Pessoa RIGHT JOIN Fornecedor ON idPessoa=Pessoa_idPessoa" );
		fornecedoresList.setFields( camposFornecedores );
		camposFornecedores.addTextLabel( "Data Cadastro:", 277, 67, 100, 14);
		camposFornecedores.getInputById( "aniversario" ).setEditable( false );
		
		JPanel panel_22 = new JPanel();
		panel_22.setLayout(null);
		panel_22.setBounds(89, 453, 747, 23);
		panel_16.add(panel_22);
		
		JButton button_20 = new JButton("Cadastrar");
		button_20.setBounds(164, 0, 99, 23);
		panel_22.add(button_20);
		
		JButton button_21 = new JButton("Alterar");
		button_21.setBounds(273, 0, 89, 23);
		panel_22.add(button_21);
		
		JButton button_22 = new JButton("Excluir");
		button_22.setBounds(372, 0, 89, 23);
		panel_22.add(button_22);
		
		JButton button_23 = new JButton("Fechar");
		button_23.setBounds(471, 0, 89, 23);
		panel_22.add(button_23);
		
		JPanel panel_23 = new JPanel();
		panel_23.setLayout(null);
		tabbedPane.addTab("Compras", null, panel_23, null);
		
		//!----
		
		FieldManager camposCompra = new FieldManager( "compra", "idCompra", "", 376, 50, 446, 295);
		camposCompra.addTextLabel( "Descrição: ", 10, 16, 100, 14 );
		camposCompra.addTextArea("descrip", 136, 11, 300, 55 );
		camposCompra.addTextLabel( "Data do Vencimento: ", 10, 77, 127, 14 );
		camposCompra.addTextLabel( "Data da Quitação: ", 10, 105, 127, 14 );
		camposCompra.addInput("dataVencimento", 136, 74, 127, 20);
		camposCompra.addTextLabel( "Fornecedor: ", 10, 133, 100, 14 );
		camposCompra.addComboBox( new CustomComboBox( "Fornecedor_Pessoa_idPessoa", "SELECT idPessoa, nome FROM pessoa RIGHT JOIN fornecedor ON Pessoa_idPessoa=idPessoa", 136, 130, 192, 20 ) );
		camposCompra.addTextLabel( "Produtos Comprados: ", 10, 186, 127, 14 );
		camposCompra.addInput("dataQuitacao", 136, 102, 127, 20);
		camposCompra.getInputById( "dataQuitacao" ).setEditable( false );
		camposCompra.addTextLabel( "Valor Total: ", 10, 267, 94, 14 );
		camposCompra.addTextLabel( "R$ ", 273, 267, 36, 14 );
		camposCompra.addInput("valorTotal", 136, 264, 127, 20);
		camposCompra.getInputById( "valorTotal" ).setEditable( false );
		camposCompra.addToPanel( panel_23 );
		ElementList comprasList = new ElementList( panel_23, "Compras", 89, 50, 267, 295,
				"SELECT idCompra, descrip FROM Compra" );
		comprasList.setFields( camposCompra );
		
		JPanel panel_27 = new JPanel();
		panel_27.setLayout(null);
		panel_27.setBounds(89, 356, 733, 23);
		panel_23.add(panel_27);
		
		JButton button_24 = new JButton("Cadastrar");
		button_24.setBounds(164, 0, 99, 23);
		panel_27.add(button_24);
		
		JButton button_25 = new JButton("Alterar");
		button_25.setBounds(273, 0, 89, 23);
		panel_27.add(button_25);
		
		JButton button_26 = new JButton("Excluir");
		button_26.setBounds(372, 0, 89, 23);
		panel_27.add(button_26);
		
		JButton button_27 = new JButton("Fechar");
		button_27.setBounds(471, 0, 89, 23);
		panel_27.add(button_27);
		
		JPanel panel_25 = new JPanel();
		tabbedPane.addTab("Lista d. Clientes", null, panel_25, null);
		panel_25.setLayout(null);
		
		//!  TEMP tornar lista dinamica
		
		DefaultTableModel model = new DefaultTableModel();
		JTable table = new JTable( model );
		JScrollPane scroll = new JScrollPane( table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		model.addColumn( "Nome" );
		model.addColumn( "Celular" );
		model.addColumn( "Telefone Fixo" );
		model.addColumn( "Email" );
		model.addColumn( "Aniversário" );
		model.addColumn( "endereço" );
		scroll.setBounds(10, 11, 901, 530);
		panel_25.add( scroll );
		table.getColumnModel().getColumn( 0 ).setMinWidth( 140 );
		table.getColumnModel().getColumn( 0 ).setPreferredWidth( 140 );
		table.getColumnModel().getColumn( 1 ).setMaxWidth( 75 );
		table.getColumnModel().getColumn( 2 ).setMaxWidth( 75 );
		table.getColumnModel().getColumn( 3 ).setPreferredWidth( 120 );
		table.getColumnModel().getColumn( 4 ).setMaxWidth( 75 );
		
		try {

			ResultSet rs = Conexao.query("SELECT pessoa.nome, telefone_cel, telefone_fixo, email, aniversario, endereco, numero, bairro, CEP, name,UF FROM"
			+" cliente LEFT JOIN pessoa ON Pessoa_idPessoa=idpessoa LEFT JOIN endereco ON Endereco_idEndereco=idEndereco LEFT JOIN cidade ON Cidade_idCidade=idCidade"
			+" LEFT JOIN estado ON Estado_idEstado=idEstado");
			
			while( rs.next() ){
			
				model.addRow( new Object[] { rs.getObject(1), rs.getObject(2), rs.getObject(3), rs.getObject(4), rs.getObject(5), rs.getString(6) + " " + rs.getString(7) 
						+ " " + rs.getString(8) + " "+ rs.getString(9) + " " + rs.getString(10) + " " + rs.getString(11) } );
				
			}

			table.setRowHeight( 30 );
			
		} catch (SQLException e) {
			
		}
		
	}
}