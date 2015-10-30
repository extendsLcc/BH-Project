package br.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

import javax.swing.BoxLayout;

public class Window extends JFrame {

	private JPanel contentPane;
	private JMenuItem Item_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
		            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
					Window frame = new Window();
					frame.setLocationRelativeTo( null );
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} 

	
	public Window() {
		setTitle("Gerenciamento de Hor\u00E1rios - Beauty Hair");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize( new Dimension( 952, 652 ) );
		
		try{
			
			Conexao.initConnection();
			
		}catch( Exception e ){
			
			System.out.println( "Falha na conexão!" );
			
		}
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		TabbedPane tabbedPane = Windows.tabbedPane;
		contentPane.add(tabbedPane);
		
		Windows.createTabs();
		tabbedPane.show( Windows.Agenda );

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnCadastros = new JMenu(" Cadastros");
		mnCadastros.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnCadastros);
		
		JMenuItem Item = new JMenuItem("Clientes");
		Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tabbedPane.show( Windows.Cliente );
				
			}
		});
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnCadastros.add(Item);
		
		Item = new JMenuItem("Funcionários");
		Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				tabbedPane.show(  Windows.Funcionario );
				
			}
		});
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnCadastros.add(Item);
		
		Item = new JMenuItem("Produtos");
		Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tabbedPane.show( Windows.Produto );
				
			}
		});
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnCadastros.add(Item);
		
		Item = new JMenuItem("Servi\u00E7os");
		Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tabbedPane.show( Windows.Servico );
				
			}
		});
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnCadastros.add(Item);
		
		Item = new JMenuItem("Fornecedores");
		Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tabbedPane.show( Windows.Fornecedor );
				
			}
		});
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnCadastros.add(Item);
		
		Item = new JMenuItem("Compras");
		Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tabbedPane.show( Windows.Compra );
				
			}
		});
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnCadastros.add(Item);
		
		JMenu mnAgenda = new JMenu("Agenda");
		mnAgenda.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnAgenda);
		
		Item = new JMenuItem("Abrir Agenda");
		Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				tabbedPane.show( Windows.Agenda );
				
			}
		});
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnAgenda.add(Item);
		
		Item = new JMenuItem("Localizar Reserva");
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
		});
		mnAgenda.add(Item);
		
		JMenu mnListas = new JMenu("Listas");
		mnListas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnListas);
		
		Item = new JMenuItem("Clientes");
		Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tabbedPane.show( Windows.ListCli );
				
			}
		});
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnListas.add(Item);
		
		Item = new JMenuItem("Servi\u00E7os");
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		Item.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				tabbedPane.show( Windows.ListSev );

			}
		} );
		mnListas.add(Item);
		
		Item = new JMenuItem("Funcionarios");
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tabbedPane.show( Windows.ListFun );
				
			}
		});
		mnListas.add(Item);
		
		Item = new JMenuItem("Produtos");
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		Item.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				tabbedPane.show( Windows.ListPro );

			}
		} );
		mnListas.add(Item);
		
		Item = new JMenuItem("Fornecedores");
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		Item.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				tabbedPane.show( Windows.ListFor );

			}
		} );
		mnListas.add(Item);
		
		Item = new JMenuItem("Contas");
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		Item.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				tabbedPane.show( Windows.ListConta );

			}
		} );
		mnListas.add(Item);
		
		JMenu mnEstoque = new JMenu("Estoque");
		mnEstoque.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnEstoque);
		
		Item = new JMenuItem("Estoque Atual");
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnEstoque.add(Item);
		
		Item = new JMenuItem("Lan\u00E7ar Compra");
		Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				tabbedPane.show( Windows.Compra );
				
			}
		});
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnEstoque.add(Item);
		
		JMenu mnContas = new JMenu("Contas");
		mnContas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnContas);
		
		Item = new JMenuItem("Registrar Conta");
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		Item.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				tabbedPane.show( Windows.Conta );

			}
		} );
		mnContas.add(Item);
		
		Item = new JMenuItem("Contas a Receber");
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnContas.add(Item);
		
		Item = new JMenuItem("Contas Recebidas");
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnContas.add(Item);
		
		Item = new JMenuItem("Contas a Pagar");
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnContas.add(Item);
		
		
		JMenu mnRelatorios = new JMenu("Relat\u00F3rios");
		mnRelatorios.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnRelatorios);
		
		JMenu mnOptions = new JMenu("Op\u00E7\u00F5es");
		mnOptions.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnOptions);
		
		Item = new JMenuItem("Preferencias");
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnOptions.add(Item);
		
		Item = new JMenuItem("Sobre");
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnOptions.add(Item);
		
		Item = new JMenuItem("Sair");
		Item.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mnOptions.add(Item);
		
		
		/*

		//!=======================================================!important!================================================================================

		//Conexao.insert( "UPDATE `pessoa` SET `aniversario`='"+ (rd( 30 )+1) + "/" + (rd(11)+1) + "/" + (rd( 10 )+ 1980)+"', `obeservacoes`=CONCAT('Observações sobre ', pessoa.nome ), `telefone_cel`='"+ rd(99999999) +"', `telefone_fixo`='"+ rd(99999999) +"', `email`=CONCAT(REPLACE( pessoa.nome, ' ', '_'), '@mail.com' ) WHERE `idPessoa`='"+i+"'" );
		//UPDATE `pessoa` SET `aniversario`=CONCAT( ROUND( RAND()*30 ), '/', ROUND( RAND()*12 ), '/', ROUND( RAND()*20+1980 ) ), `obeservacoes`=CONCAT('Observações sobre ', pessoa.nome ), `telefone_cel`=ROUND( RAND()*99999999 ), `telefone_fixo`=ROUND( RAND()*99999999 ), `email`=CONCAT(REPLACE( pessoa.nome, ' ', '_'), '@mail.com' ) WHERE idpessoa!=1;
		//UPDATE endereco SET numero=ROUND( RAND()*9999), CEP=ROUND( RAND()*99999999 ), cidade_idCidade=ROUND( RAND()*4 ) WHERE idendereco!=1;
		
		 */
	}
}