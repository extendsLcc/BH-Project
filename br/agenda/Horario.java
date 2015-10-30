package br.agenda;

import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.SwingConstants;

import br.cadastro.customfields.ComboBox;
import br.main.Conexao;
import br.main.Util;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;

public class Horario extends JDialog {
	
	private ComboBox cliente;
	private ComboBox funcionario;
	private ComboBox servico;
	private JTextArea textArea;
	private JCheckBox horarioConcluido;
	private JTextField textField;
	private CellAgenda cellObject;
	private Agenda agendaWindow;
	
	private final String SERVICO_SQL = "SELECT idservicos, servicos.nome FROM funcionario "
			+ "LEFT JOIN  funcao_has_funcionario ON Funcionario_Pessoa_idPessoa=Pessoa_idPessoa "
			+ "LEFT JOIN funcao ON idfuncao=funcao_has_funcionario.funcao_idfuncao "
			+ "LEFT JOIN tiposervicos ON tiposervicos.Funcao_idfuncao=idfuncao "
			+ "RIGHT JOIN servicos ON  tipoServicos_idtipoServicos=idtipoServicos WHERE Pessoa_idPessoa=";
	private JCheckBox servicoPago;


	public Horario( Agenda a ) {
		setTitle("Horario");
		setBounds(100, 100, 260, 440);
		getContentPane().setLayout(null);
		agendaWindow = a;
		
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCliente.setBounds(10, 31, 65, 24);
		getContentPane().add(lblCliente);
		
		cliente = new ComboBox( "SELECT idPessoa, nome FROM pessoa RIGHT JOIN cliente ON idPessoa=Pessoa_idPessoa" );
		cliente.setBounds(10, 56, 224, 30);
		getContentPane().add(cliente);
		
		JLabel lblFuncionario = new JLabel("Funcionario:");
		lblFuncionario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFuncionario.setBounds(10, 97, 102, 24);
		getContentPane().add(lblFuncionario);
		
		funcionario = new ComboBox( "SELECT idPessoa, nome FROM pessoa LEFT JOIN funcionario ON idPessoa=Pessoa_idPessoa" );
		funcionario.setEnabled(false);
		funcionario.setBounds(10, 122, 224, 30);
		getContentPane().add(funcionario);
		
		servico = new ComboBox( "SELECT idServicos, nome FROM servicos" );
		servico.setBounds(10, 188, 224, 30);
		getContentPane().add(servico);
		
		JLabel lblServio = new JLabel("Servi\u00E7o:");
		lblServio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblServio.setBounds(10, 163, 65, 24);
		getContentPane().add(lblServio);
		
		JLabel lblObservao = new JLabel("Observa\u00E7\u00E3o:");
		lblObservao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObservao.setBounds(10, 229, 102, 24);
		getContentPane().add(lblObservao);
		
		textArea = new JTextArea();
		JScrollPane scroll = new JScrollPane( textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		scroll.setBounds(10, 252, 224, 63);
		getContentPane().add(scroll);
		
		JButton save = new JButton("Salvar");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				save();
				
			}
		});
		save.setMargin( new Insets( 0, 0, 0, 0) );
		save.setBounds(10, 368, 65, 23);
		getContentPane().add(save);
		
		JButton cancel = new JButton("Cancelar");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible( false );
				
			}
		});
		cancel.setMargin( new Insets( 0, 0, 0, 0) );
		cancel.setBounds(85, 368, 65, 23);
		getContentPane().add(cancel);
		
		horarioConcluido = new JCheckBox("Horario Concluido");
		horarioConcluido.setEnabled(false);
		horarioConcluido.setMargin( new Insets( 0, 0, 0, 0) );
		horarioConcluido.setBounds(10, 336, 127, 23);
		getContentPane().add(horarioConcluido);
		
		JButton conta = new JButton("Venda");
		conta.setBounds(160, 368, 74, 23);
		getContentPane().add(conta);
		
		JLabel lblHorario = new JLabel("Hor\u00E1rio:");
		lblHorario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHorario.setBounds(10, 11, 54, 14);
		getContentPane().add(lblHorario);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBackground(new Color(231, 231, 231));
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setBounds(62, 7, 121, 24);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		servicoPago = new JCheckBox("Servi\u00E7o pago");
		servicoPago.setMargin(new Insets(0, 0, 0, 0));
		servicoPago.setEnabled(false);
		servicoPago.setBounds(140, 336, 96, 23);
		getContentPane().add(servicoPago);
		
		/*JButton Color = new JButton("");
		Color.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		Color.setBackground( new Color( 177, 235, 252 ));
		Color.setBounds(196, 225, 31, 23);
		Color.setContentAreaFilled( false );
		Color.setOpaque( true );
		Color.setBorder( new EtchedBorder(EtchedBorder.LOWERED, null, null) );
		getContentPane().add(Color);
		
		JLabel lblNewLabel = new JLabel("Cor:");
		lblNewLabel.setBounds(171, 225, 54, 24);
		getContentPane().add(lblNewLabel);*/
	}
	

	public void open( int idFuncionario, String timeInit, String timeEnd ){
		
		textField.setText( timeInit + " - " + timeEnd );
		cliente.atualizeItens();
		funcionario.atualizeItens();
		funcionario.selectItemById( idFuncionario );
		servico.setSQL( SERVICO_SQL + idFuncionario );
		servico.atualizeItens();
		
	}
	
	public void edit( CellAgenda agendamento ){
		
		textField.setText( agendamento.getHourInit() + " - " + agendamento.getHourEnd() );
		cliente.atualizeItens();
		servico.atualizeItens();
		cliente.selectItemById( agendamento.getClienteId() );
		funcionario.selectItemById( agendamento.getFuncionarioId() );
		servico.selectItemById( agendamento.getServicoId() );
		textArea.setText( agendamento.getObservacao() );
		horarioConcluido.setSelected( agendamento.isConcluido() );
		servicoPago.setEnabled( agendamento.isPago() );
		cellObject = agendamento;
		
	}
	
	protected void save() {
		
		if( cellObject != null ){
			
			cellObject.commit();
			cellObject = null;
			
		}
		
		String sql = "INSERT INTO horarios( data, horarioInit, horarioEnd, observacao, Cliente_Pessoa_idPessoa, Funcionario_Pessoa_idPessoa, Servicos_idServicos ) ";
		sql += " VALUES ( '"+ new SimpleDateFormat( "dd/MM/yyyy" ).format( agendaWindow.getCalendar().getDate() ) 
				+"', '"+ textField.getText().substring( 0, 5 ) +"', '"+ textField.getText().substring( 8, 13 )  +"', '"+ textArea.getText() +"'"
				+ ", '"+ cliente.getSelectedItemId() +"', '"+ funcionario.getSelectedItemId() +"', '"+ servico.getSelectedItemId() +"' )";
		Util.p(sql);
		Conexao.update( sql );
		setVisible( false );
		
	}
	
	@Override
	public void setVisible( boolean b ){
		
		if( !b ){
			
			textArea.setText( "" );
			horarioConcluido.setEnabled( false );
			servicoPago.setEnabled( false);
			
		}
		
		super.setVisible(b);
		
	}
}
