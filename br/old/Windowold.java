package br.old;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

public class Windowold extends JFrame {

	private JPanel contentPane;

	private JTextField textField_22;
	private JTextField textField_24;
	private JTextField textField_23;
	private JTextField textField_26;
	private JTextField textField_27;
	private JTextField textField_25;
	private JTextField textField_28;
	private JTextField textField_29;
	private JTextField textField_30;
	private JTextField textField_31;
	private JTextField textField_32;
	private JTextField textField_33;
	private JTextField textField_34;
	private JTextField textField_35;
	private JTextField textField_36;
	private JTextField textField_37;
	private JTextField textField_38;
	private JTextField textField_39;
	private JTextField textField_40;
	private JTextField textField_41;
	private JTextField textField_42;
	private JTextField textField_11;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_17;
	private JTextField textField_18;
	private JTextField textField_19;
	private JTextField textField_20;
	private JTextField textField_21;
	private JTextField textField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Windowold frame = new Windowold();
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
		
		return camposPessoa;
		
	}
	
	
	public Windowold() {
		setTitle("Gerenciamento de Hor\u00E1rios - Sal\u00E3o Korpus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 952, 652);
		setMinimumSize( new Dimension( 952, 652 ) );
		
		try{
			
			Conexao.getConcection();
			
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
				"FROM Pessoa RIGHT JOIN Cliente ON idPessoa=Pessoa_idPessoa", "nome", "Pessoa_idPessoa" );
		clienteList.setFields( camposClientes );
		camposClientes.addTextLabel( "Anivers\u00E1rio:", 281, 67, 96, 14);
		
		//Conexao.insert( "UPDATE `pessoa` SET `aniversario`='"+ (rd( 30 )+1) + "/" + (rd(11)+1) + "/" + (rd( 10 )+ 1980)+"', `obeservacoes`=CONCAT('Observações sobre ', pessoa.nome ), `telefone_cel`='"+ rd(99999999) +"', `telefone_fixo`='"+ rd(99999999) +"', `email`=CONCAT(REPLACE( pessoa.nome, ' ', '_'), '@mail.com' ) WHERE `idPessoa`='"+i+"'" );
		//UPDATE `pessoa` SET `aniversario`=CONCAT( ROUND( RAND()*30 ), '/', ROUND( RAND()*12 ), '/', ROUND( RAND()*20+1980 ) ), `obeservacoes`=CONCAT('Observações sobre ', pessoa.nome ), `telefone_cel`=ROUND( RAND()*99999999 ), `telefone_fixo`=ROUND( RAND()*99999999 ), `email`=CONCAT(REPLACE( pessoa.nome, ' ', '_'), '@mail.com' ) WHERE idpessoa!=1;
		//UPDATE endereco SET numero=ROUND( RAND()*9999), CEP=ROUND( RAND()*99999999 ), cidade_idCidade=ROUND( RAND()*4 ) WHERE idendereco!=1;
		
		//!------------------------------------------------------
		/*JPanel panel_3 = new JPanel();
		panel_3.setBounds(376, 50, 460, 373);
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblE = new JLabel("E-mail:");
		lblE.setBounds(10, 89, 46, 14);
		panel_3.add(lblE);
		
		JLabel lblNewLabel_1 = new JLabel("Celular:");
		lblNewLabel_1.setBounds(10, 64, 46, 14);
		panel_3.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("Nome completo:");
		lblNewLabel.setBounds(10, 39, 100, 14);
		panel_3.add(lblNewLabel);
		
		JLabel lblNewLabel_3 = new JLabel("Nome ou Apelido:");
		lblNewLabel_3.setBounds(10, 14, 100, 14);
		panel_3.add(lblNewLabel_3);
		
		textField = new JTextField();
		textField.setBounds(120, 11, 326, 20);
		panel_3.add(textField);
		textField.setColumns(1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(120, 36, 326, 20);
		panel_3.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(120, 61, 110, 20);
		panel_3.add(textField_2);
		textField_2.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(120, 86, 151, 20);
		panel_3.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(365, 86, 81, 20);
		panel_3.add(textField_5);
		textField_5.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(303, 61, 143, 20);
		panel_3.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Anivers\u00E1rio:");
		lblNewLabel_2.setBounds(281, 89, 74, 14);
		panel_3.add(lblNewLabel_2);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(240, 64, 53, 14);
		panel_3.add(lblTelefone);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o");
		lblEndereo.setBounds(10, 188, 66, 14);
		panel_3.add(lblEndereo);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBounds(120, 117, 326, 180);
		panel_3.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblEndereo_1 = new JLabel("Endere\u00E7o :");
		lblEndereo_1.setBounds(10, 11, 74, 14);
		panel_4.add(lblEndereo_1);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(94, 8, 222, 20);
		panel_4.add(textField_6);
		
		JLabel lblNmero = new JLabel("N\u00FAmero:");
		lblNmero.setBounds(10, 36, 74, 14);
		panel_4.add(lblNmero);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(94, 34, 42, 20);
		panel_4.add(textField_7);
		
		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(146, 34, 50, 14);
		panel_4.add(lblBairro);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(193, 34, 123, 20);
		panel_4.add(textField_8);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(10, 61, 55, 14);
		panel_4.add(lblCidade);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(94, 60, 222, 20);
		panel_4.add(textField_9);
		
		JLabel lblCep = new JLabel("CEP:");
		lblCep.setBounds(10, 89, 55, 14);
		panel_4.add(lblCep);
		
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(94, 86, 105, 20);
		panel_4.add(textField_10);
		
		JLabel lblComplemento = new JLabel("Complemento:");
		lblComplemento.setBounds(8, 136, 84, 14);
		panel_4.add(lblComplemento);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(94, 117, 222, 52);
		textArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.add( textArea );
		textArea.setLineWrap( true );
		textArea.setWrapStyleWord( true );
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(209, 89, 55, 14);
		panel_4.add(lblEstado);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"PR", "SP", "RJ"}));
		comboBox.setBounds(274, 86, 42, 20);
		panel_4.add(comboBox);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setWrapStyleWord(true);
		textArea_1.setLineWrap(true);
		textArea_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textArea_1.setBounds(120, 308, 326, 52);
		panel_3.add(textArea_1);
		
		JLabel lblObservaes = new JLabel("Observa\u00E7\u00F5es :");
		lblObservaes.setBounds(10, 324, 100, 14);
		panel_3.add(lblObservaes);
		*/
		
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
				"FROM Pessoa RIGHT JOIN Funcionario ON idPessoa=Pessoa_idPessoa", "nome", "Pessoa_idPessoa" );
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
		
		/*JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(376, 50, 460, 373);
		panel_1.add(panel_2);
		
		JLabel label_1 = new JLabel("E-mail:");
		label_1.setBounds(10, 67, 46, 14);
		panel_2.add(label_1);
		
		JLabel label_2 = new JLabel("Celular:");
		label_2.setBounds(10, 42, 46, 14);
		panel_2.add(label_2);
		
		JLabel label_4 = new JLabel("Nome ou Apelido:");
		label_4.setBounds(10, 14, 100, 14);
		panel_2.add(label_4);
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(120, 11, 326, 20);
		panel_2.add(textField_11);
		
		textField_13 = new JTextField();
		textField_13.setColumns(10);
		textField_13.setBounds(120, 39, 110, 20);
		panel_2.add(textField_13);
		
		textField_14 = new JTextField();
		textField_14.setColumns(10);
		textField_14.setBounds(120, 64, 151, 20);
		panel_2.add(textField_14);
		
		textField_15 = new JTextField();
		textField_15.setColumns(10);
		textField_15.setBounds(365, 64, 81, 20);
		panel_2.add(textField_15);
		
		textField_16 = new JTextField();
		textField_16.setColumns(10);
		textField_16.setBounds(303, 39, 143, 20);
		panel_2.add(textField_16);
		
		JLabel label_5 = new JLabel("Anivers\u00E1rio:");
		label_5.setBounds(281, 67, 74, 14);
		panel_2.add(label_5);
		
		JLabel label_6 = new JLabel("Telefone:");
		label_6.setBounds(240, 42, 53, 14);
		panel_2.add(label_6);
		
		JLabel label_7 = new JLabel("Endere\u00E7o");
		label_7.setBounds(10, 163, 66, 14);
		panel_2.add(label_7);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_5.setBounds(120, 92, 326, 180);
		panel_2.add(panel_5);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setBounds(10, 11, 74, 14);
		panel_5.add(lblEndereo);
		
		textField_17 = new JTextField();
		textField_17.setColumns(10);
		textField_17.setBounds(94, 8, 222, 20);
		panel_5.add(textField_17);
		
		JLabel label_9 = new JLabel("N\u00FAmero:");
		label_9.setBounds(10, 36, 74, 14);
		panel_5.add(label_9);
		
		textField_18 = new JTextField();
		textField_18.setColumns(10);
		textField_18.setBounds(94, 34, 42, 20);
		panel_5.add(textField_18);
		
		JLabel label_10 = new JLabel("Bairro:");
		label_10.setBounds(146, 36, 50, 14);
		panel_5.add(label_10);
		
		textField_19 = new JTextField();
		textField_19.setColumns(10);
		textField_19.setBounds(193, 34, 123, 20);
		panel_5.add(textField_19);
		
		JLabel label_11 = new JLabel("Cidade:");
		label_11.setBounds(10, 61, 55, 14);
		panel_5.add(label_11);
		
		textField_20 = new JTextField();
		textField_20.setColumns(10);
		textField_20.setBounds(94, 60, 222, 20);
		panel_5.add(textField_20);
		
		JLabel label_12 = new JLabel("CEP:");
		label_12.setBounds(10, 89, 55, 14);
		panel_5.add(label_12);
		
		textField_21 = new JTextField();
		textField_21.setColumns(10);
		textField_21.setBounds(94, 86, 105, 20);
		panel_5.add(textField_21);
		
		JLabel label_13 = new JLabel("Complemento:");
		label_13.setBounds(8, 136, 84, 14);
		panel_5.add(label_13);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setWrapStyleWord(true);
		textArea_2.setLineWrap(true);
		textArea_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textArea_2.setBounds(94, 117, 222, 52);
		panel_5.add(textArea_2);
		
		JLabel label_14 = new JLabel("Estado:");
		label_14.setBounds(209, 89, 55, 14);
		panel_5.add(label_14);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JComboBox cb = (JComboBox) e.getSource();
				System.out.println( cb.getSelectedItem().toString() );
				
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"PR", "SP", "RJ"}));
		comboBox_1.setBounds(274, 86, 42, 20);
		panel_5.add(comboBox_1);
		
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setWrapStyleWord(true);
		textArea_3.setLineWrap(true);
		textArea_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textArea_3.setBounds(120, 283, 326, 79);
		panel_2.add(textArea_3);
		
		JLabel label_15 = new JLabel("Observa\u00E7\u00F5es :");
		label_15.setBounds(10, 313, 100, 14);
		panel_2.add(label_15);*/
		
		JPanel panel_10 = new JPanel();
		panel_10.setLayout(null);
		tabbedPane.addTab("Produtos", null, panel_10, null);
		
		JPanel panel_11 = new JPanel();
		panel_11.setLayout(null);
		panel_11.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_11.setBounds(421, 49, 380, 205);
		panel_10.add(panel_11);
		
		JLabel lblCusto = new JLabel("Custo:");
		lblCusto.setBounds(10, 64, 46, 14);
		panel_11.add(lblCusto);
		
		JLabel lblTipoDeProduto = new JLabel("Tipo de produto:");
		lblTipoDeProduto.setBounds(10, 39, 100, 14);
		panel_11.add(lblTipoDeProduto);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 14, 100, 14);
		panel_11.add(lblNome);
		
		textField_22 = new JTextField();
		textField_22.setColumns(10);
		textField_22.setBounds(120, 11, 248, 20);
		panel_11.add(textField_22);
		
		textField_24 = new JTextField();
		textField_24.setColumns(10);
		textField_24.setBounds(120, 61, 138, 20);
		panel_11.add(textField_24);
		
		JLabel label_30 = new JLabel("Observa\u00E7\u00F5es :");
		label_30.setBounds(10, 138, 100, 14);
		panel_11.add(label_30);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setBounds(120, 36, 192, 20);
		panel_11.add(comboBox_4);
		
		JButton btnNewButton_5 = new JButton("+");

		btnNewButton_5.setBounds(322, 35, 46, 23);
		panel_11.add(btnNewButton_5);
		
		JLabel lblNewLabel_6 = new JLabel("R$");
		lblNewLabel_6.setBounds(268, 64, 46, 14);
		panel_11.add(lblNewLabel_6);
		
		textField = new JTextField();
		textField.setBounds(120, 86, 86, 20);
		panel_11.add(textField);
		textField.setColumns(10);
		
		JLabel lblQtdEmEstoque = new JLabel("Qtd em Estoque:");
		lblQtdEmEstoque.setBounds(10, 89, 100, 14);
		panel_11.add(lblQtdEmEstoque);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textArea.setBounds(122, 111, 248, 83);
		panel_11.add(textArea);
		
		ElementList produtos = new ElementList( panel_10, "Produtos", 134, 49, 267, 205, 
				"FROM Produto", "nome", "idProduto" );
		
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
		
		JPanel panel_12 = new JPanel();
		panel_12.setLayout(null);
		tabbedPane.addTab("Serviços", null, panel_12, null);
		
		JPanel panel_15 = new JPanel();
		panel_15.setLayout(null);
		panel_15.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_15.setBounds(376, 50, 460, 337);
		panel_12.add(panel_15);
		
		JLabel lblComisso_1 = new JLabel("Comiss\u00E3o:");
		lblComisso_1.setBounds(10, 89, 78, 14);
		panel_15.add(lblComisso_1);
		
		JLabel lblComisso = new JLabel("Pre\u00E7o de Venda:");
		lblComisso.setBounds(10, 64, 100, 14);
		panel_15.add(lblComisso);
		
		JLabel lblTipoDeServio = new JLabel("Tipo de Servi\u00E7o:");
		lblTipoDeServio.setBounds(10, 39, 100, 14);
		panel_15.add(lblTipoDeServio);
		
		JLabel lblNome_1 = new JLabel("Nome:");
		lblNome_1.setBounds(10, 14, 100, 14);
		panel_15.add(lblNome_1);
		
		textField_23 = new JTextField();
		textField_23.setColumns(10);
		textField_23.setBounds(147, 11, 299, 20);
		panel_15.add(textField_23);
		
		textField_26 = new JTextField();
		textField_26.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_26.setHorizontalAlignment(SwingConstants.CENTER);
		textField_26.setColumns(10);
		textField_26.setBounds(146, 64, 84, 20);
		panel_15.add(textField_26);
		
		textField_27 = new JTextField();
		textField_27.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_27.setHorizontalAlignment(SwingConstants.CENTER);
		textField_27.setColumns(10);
		textField_27.setBounds(146, 89, 84, 20);
		panel_15.add(textField_27);
		
		JTextArea textArea_6 = new JTextArea();
		textArea_6.setWrapStyleWord(true);
		textArea_6.setLineWrap(true);
		textArea_6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textArea_6.setBounds(147, 256, 299, 70);
		panel_15.add(textArea_6);
		
		JLabel label_29 = new JLabel("Observa\u00E7\u00F5es :");
		label_29.setBounds(10, 276, 100, 14);
		panel_15.add(label_29);
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setBounds(146, 36, 244, 20);
		panel_15.add(comboBox_5);
		
		JButton button_15 = new JButton("+");
		button_15.setBounds(400, 35, 46, 23);
		panel_15.add(button_15);
		
		JLabel lblNewLabel_5 = new JLabel("R$");
		lblNewLabel_5.setBounds(240, 67, 36, 14);
		panel_15.add(lblNewLabel_5);
		
		JLabel label = new JLabel("%");
		label.setBounds(240, 92, 46, 14);
		panel_15.add(label);
		
		JLabel lblDuraoAproximada = new JLabel("Dura\u00E7\u00E3o Aproximada:");
		lblDuraoAproximada.setBounds(10, 117, 136, 14);
		panel_15.add(lblDuraoAproximada);
		
		textField_25 = new JTextField();
		textField_25.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_25.setHorizontalAlignment(SwingConstants.CENTER);
		textField_25.setColumns(10);
		textField_25.setBounds(146, 117, 84, 20);
		panel_15.add(textField_25);
		
		JLabel lblMinutos = new JLabel("minutos");
		lblMinutos.setBounds(240, 120, 58, 14);
		panel_15.add(lblMinutos);
		
		DefaultTableModel model = new DefaultTableModel();
		JTable table = new JTable( model );
		JScrollPane scroll = new JScrollPane( table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		model.addColumn( "Produto" );
		model.addColumn( "qtd usada" );
		scroll.setBounds(147, 148, 243, 97);
		panel_15.add( BorderLayout.CENTER, scroll);
		model.addRow( new Object[]{ "produto 1", "10" } );
		model.addRow( new Object[]{ "produto 2", "5" } );
		
		JButton btnNewButton_6 = new JButton("+");
		btnNewButton_6.setBounds(400, 149, 46, 43);
		panel_15.add(btnNewButton_6);
		
		JButton button_16 = new JButton("-");
		button_16.setBounds(400, 203, 46, 42);
		panel_15.add(button_16);
		
		JLabel lblProdutosUsados = new JLabel("Produtos usados");
		lblProdutosUsados.setBounds(10, 190, 112, 14);
		panel_15.add(lblProdutosUsados);
		

		ElementList servicos = new ElementList( panel_12, "Serviços", 89, 50, 267, 337,
				"FROM Servicos", "nome", "idServicos" );
		
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
				"FROM Pessoa RIGHT JOIN Fornecedor ON idPessoa=Pessoa_idPessoa", "nome", "Pessoa_idPessoa" );
		fornecedoresList.setFields( camposFornecedores );
		camposFornecedores.addTextLabel( "Data Cadastro:", 277, 67, 100, 14);
		camposFornecedores.getInputById( "aniversario" ).setEditable( false );
		
		/*
		JPanel panel_20 = new JPanel();
		panel_20.setLayout(null);
		panel_20.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_20.setBounds(376, 50, 460, 392);
		panel_16.add(panel_20);
		
		JLabel label_17 = new JLabel("E-mail:");
		label_17.setBounds(10, 89, 46, 14);
		panel_20.add(label_17);
		
		JLabel label_18 = new JLabel("Celular:");
		label_18.setBounds(10, 64, 46, 14);
		panel_20.add(label_18);
		
		JLabel label_19 = new JLabel("Nome completo:");
		label_19.setBounds(10, 39, 100, 14);
		panel_20.add(label_19);
		
		JLabel label_20 = new JLabel("Nome ou Apelido:");
		label_20.setBounds(10, 14, 100, 14);
		panel_20.add(label_20);
		
		textField_28 = new JTextField();
		textField_28.setColumns(10);
		textField_28.setBounds(120, 11, 326, 20);
		panel_20.add(textField_28);
		
		textField_29 = new JTextField();
		textField_29.setColumns(10);
		textField_29.setBounds(120, 36, 326, 20);
		panel_20.add(textField_29);
		
		textField_30 = new JTextField();
		textField_30.setColumns(10);
		textField_30.setBounds(120, 61, 110, 20);
		panel_20.add(textField_30);
		
		textField_31 = new JTextField();
		textField_31.setColumns(10);
		textField_31.setBounds(120, 86, 151, 20);
		panel_20.add(textField_31);
		
		textField_32 = new JTextField();
		textField_32.setColumns(10);
		textField_32.setBounds(365, 86, 81, 20);
		panel_20.add(textField_32);
		
		textField_33 = new JTextField();
		textField_33.setColumns(10);
		textField_33.setBounds(303, 61, 143, 20);
		panel_20.add(textField_33);
		
		JLabel label_21 = new JLabel("Anivers\u00E1rio:");
		label_21.setBounds(281, 89, 74, 14);
		panel_20.add(label_21);
		
		JLabel label_22 = new JLabel("Telefone:");
		label_22.setBounds(240, 64, 53, 14);
		panel_20.add(label_22);
		
		JLabel label_23 = new JLabel("Endere\u00E7o");
		label_23.setBounds(10, 210, 66, 14);
		panel_20.add(label_23);
		
		JPanel panel_21 = new JPanel();
		panel_21.setToolTipText("");
		panel_21.setLayout(null);
		panel_21.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_21.setBounds(120, 139, 326, 180);
		panel_20.add(panel_21);
		
		JLabel label_24 = new JLabel("Endere\u00E7o :");
		label_24.setBounds(10, 11, 74, 14);
		panel_21.add(label_24);
		
		textField_34 = new JTextField();
		textField_34.setColumns(10);
		textField_34.setBounds(94, 8, 222, 20);
		panel_21.add(textField_34);
		
		JLabel label_25 = new JLabel("N\u00FAmero:");
		label_25.setBounds(10, 36, 74, 14);
		panel_21.add(label_25);
		
		textField_35 = new JTextField();
		textField_35.setColumns(10);
		textField_35.setBounds(94, 34, 42, 20);
		panel_21.add(textField_35);
		
		JLabel label_26 = new JLabel("Bairro:");
		label_26.setBounds(146, 34, 50, 14);
		panel_21.add(label_26);
		
		textField_36 = new JTextField();
		textField_36.setColumns(10);
		textField_36.setBounds(193, 34, 123, 20);
		panel_21.add(textField_36);
		
		JLabel label_27 = new JLabel("Cidade:");
		label_27.setBounds(10, 61, 55, 14);
		panel_21.add(label_27);
		
		textField_37 = new JTextField();
		textField_37.setColumns(10);
		textField_37.setBounds(94, 60, 222, 20);
		panel_21.add(textField_37);
		
		JLabel label_28 = new JLabel("CEP:");
		label_28.setBounds(10, 89, 55, 14);
		panel_21.add(label_28);
		
		textField_38 = new JTextField();
		textField_38.setColumns(10);
		textField_38.setBounds(94, 86, 105, 20);
		panel_21.add(textField_38);
		
		JLabel label_32 = new JLabel("Complemento:");
		label_32.setBounds(8, 136, 84, 14);
		panel_21.add(label_32);
		
		JTextArea textArea_7 = new JTextArea();
		textArea_7.setWrapStyleWord(true);
		textArea_7.setLineWrap(true);
		textArea_7.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textArea_7.setBounds(94, 117, 222, 52);
		panel_21.add(textArea_7);
		
		JLabel label_33 = new JLabel("Estado:");
		label_33.setBounds(209, 89, 55, 14);
		panel_21.add(label_33);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(274, 86, 42, 20);
		panel_21.add(comboBox_3);
		
		JTextArea textArea_8 = new JTextArea();
		textArea_8.setWrapStyleWord(true);
		textArea_8.setLineWrap(true);
		textArea_8.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textArea_8.setBounds(120, 330, 326, 52);
		panel_20.add(textArea_8);
		
		JLabel label_34 = new JLabel("Observa\u00E7\u00F5es :");
		label_34.setBounds(10, 346, 100, 14);
		panel_20.add(label_34);
		
		JLabel lblContato = new JLabel("Contato:");
		lblContato.setBounds(10, 114, 74, 14);
		panel_20.add(lblContato);
		
		textField_39 = new JTextField();
		textField_39.setColumns(10);
		textField_39.setBounds(120, 111, 326, 20);
		panel_20.add(textField_39);*/
		
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
		
		JPanel panel_24 = new JPanel();
		panel_24.setLayout(null);
		panel_24.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_24.setBounds(376, 50, 446, 295);
		panel_23.add(panel_24);
		
		JTextArea textArea_10 = new JTextArea();
		textArea_10.setWrapStyleWord(true);
		textArea_10.setLineWrap(true);
		textArea_10.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textArea_10.setBounds(136, 11, 300, 55);
		panel_24.add(textArea_10);
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o:");
		lblDescrio.setBounds(10, 16, 100, 14);
		panel_24.add(lblDescrio);
		
		textField_40 = new JTextField();
		textField_40.setBounds(136, 74, 127, 20);
		panel_24.add(textField_40);
		textField_40.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Data do vencimento:");
		lblNewLabel_7.setBounds(10, 77, 127, 14);
		panel_24.add(lblNewLabel_7);
		
		JLabel lblDataDaQuitao = new JLabel("Data da Quita\u00E7\u00E3o:");
		lblDataDaQuitao.setBounds(10, 105, 127, 14);
		panel_24.add(lblDataDaQuitao);
		
		textField_41 = new JTextField();
		textField_41.setEditable(false);
		textField_41.setColumns(10);
		textField_41.setBounds(136, 102, 127, 20);
		panel_24.add(textField_41);
		
		JLabel lblValorTotal = new JLabel("Valor Total:");
		lblValorTotal.setBounds(10, 267, 94, 14);
		panel_24.add(lblValorTotal);
		
		textField_42 = new JTextField();
		textField_42.setEditable(false);
		textField_42.setColumns(10);
		textField_42.setBounds(136, 264, 127, 20);
		panel_24.add(textField_42);
		
		model = new DefaultTableModel();
		table = new JTable( model );
		scroll = new JScrollPane( table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		model.addColumn( "Produto" );
		model.addColumn( "qtd comprada" );
		model.addColumn( "custo" );
		scroll.setBounds(136, 158, 300, 94);
		panel_24.add( BorderLayout.CENTER, scroll);
		model.addRow( new Object[]{ "produto 1", "2", "10.00R$" } );
		model.addRow( new Object[]{ "produto 2", "15", "2.00R$" } );
		
		JLabel lblProdutosComprados = new JLabel("Prod. Comprados:");
		lblProdutosComprados.setBounds(10, 186, 127, 14);
		panel_24.add(lblProdutosComprados);
		
		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setBounds(136, 130, 192, 20);
		panel_24.add(comboBox_6);
		
		JLabel lblFornecedor = new JLabel("Fornecedor:");
		lblFornecedor.setBounds(10, 133, 100, 14);
		panel_24.add(lblFornecedor);
		
		JLabel label_1 = new JLabel("R$");
		label_1.setBounds(273, 267, 36, 14);
		panel_24.add(label_1);

		ElementList compras = new ElementList( panel_23, "Compras", 89, 50, 267, 295,
				"FROM Compra", "descrip", "idCompra" );
		
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
		tabbedPane.addTab("Lista Geral", null, panel_25, null);
		panel_25.setLayout(null);
	}
}
