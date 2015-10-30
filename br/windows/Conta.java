package br.windows;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import br.agenda.CellAgenda;
import br.cadastro.customfields.ComboBox;
import br.cadastro.customfields.Item;
import br.cadastro.customfields.JTextFieldNumbFilter;
import br.main.Conexao;
import br.main.Util;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.toedter.calendar.JDateChooser;

import javax.swing.border.EtchedBorder;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;


public class Conta extends JPanel {

	JTable table;
	DefaultTableModel model;
	private ComboBox cliente;
	private ComboBox horario;
	private JDateChooser dateChooser;
	private JSeparator separator;
	private JTextField parcelas;
	private JTextField valorAlt;
	private JTextField total;
	
	private static String hourSql = "SELECT  idHorarios, CONCAT( data, ' ', horarioInit, ' - ' , horarioEnd, ' ', nome ) "
			+ "as name FROM horarios LEFT JOIN Cliente ON Cliente_Pessoa_idPessoa=Pessoa_idPessoa "
			+ "LEFT JOIN servicos ON Servicos_idServicos=idServicos WHERE concluido=1 AND pago=0  AND "
			+ "idHorarios NOT IN ( SELECT Horarios_idHorarios AS IdHorarios FROM horarios_has_conta ) AND Cliente_Pessoa_idPessoa=";
	private JButton save;
	
	public Conta() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setPreferredSize( new Dimension(374, 420) );
		setLayout(null);
		
		JLabel txt = new JLabel( "Cliente: " );
		txt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txt.setBounds( 10, 11, 82, 23);
		add( txt );
		cliente = new ComboBox("SELECT idPessoa, nome FROM pessoa RIGHT JOIN cliente ON idPessoa=Pessoa_idPessoa" );
		cliente.setBounds(  106, 11, 244, 23);
		cliente.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				horario.setSQL( hourSql + cliente.getItemAt( cliente.getSelectedIndex() ).getId() );
				horario.atualizeItens();
				for( int i = model.getRowCount() - 1; i >= 0; i-- )
					model.removeRow( i );
				
			}
		} );
		add( cliente );
		txt = new JLabel( "Horario:" );
		txt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txt.setBounds( 10, 121, 82, 23);
		add( txt );
		horario = new ComboBox( "SELECT   idHorarios, data FROM horarios WHERE idHorarios=-1" );
		horario.setBounds( 106, 121, 244, 23);
		add( horario );
		
		txt = new JLabel("Data do vencimento:");
		txt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txt.setBounds(10, 45, 155, 23);
		add(txt);
		
		dateChooser = new JDateChooser();
		dateChooser.getJCalendar().setTodayButtonVisible( true );
		dateChooser.setBounds(175, 45, 126, 20);
		add(dateChooser);

		model = new DefaultTableModel(){
			
			public boolean isCellEditable(int row, int col){ 
				
				return false; 
				
			}
			
		};
		model.addColumn( "Horario" );
		model.addColumn( "Valor" );
		table = new JTable( model );
		DefaultTableCellRenderer  centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
		table.setDefaultRenderer( Object.class , centerRenderer );
		table.getSelectionModel().setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		Util.setJTableColumnsWidth( table, 353,  80, 20);
		JScrollPane scroll = new JScrollPane( table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		scroll.setBounds( 10, 186, 353, 144 );
		add( scroll );
		
		separator = new JSeparator();
		separator.setBounds(10, 108, 353, 2);
		add(separator);
		
		txt = new JLabel("Parcelas:");
		txt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txt.setBounds(10, 74, 82, 23);
		add(txt);
		
		parcelas = new JTextField( "0" );
		parcelas.setBounds(110, 74, 42, 20);
		parcelas.setDocument( new JTextFieldNumbFilter( 2 , 0) );
		add(parcelas);
		
		txt = new JLabel("Valor:");
		txt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txt.setBounds(10, 152, 87, 23);
		add(txt);
		
		valorAlt = new JTextField();
		valorAlt.setBounds(106, 155, 97, 20);
		valorAlt.setDocument( new JTextFieldNumbFilter( 5, 2 ) );
		add(valorAlt);
		
		JButton plus = new JButton("+");
		plus.setBounds(259, 155, 42, 23);
		plus.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if( !(horario.getSelectedItem() instanceof Item) )
					return;

				if( valorAlt.getText().length() == 0 ){
					
					try {
						
						ResultSet rs = Conexao.query( "SELECT precoVenda FROM servicos LEFT JOIN horarios ON "
								+ "idServicos=Servicos_idServicos WHERE idHorarios=" + horario.getSelectedItemId() );
						rs.next();
						valorAlt.setText( rs.getString( 1 ) );
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
				
				model.addRow( new Object[]{ horario.getSelectedItem(), valorAlt.getText() } );
				horario.removeItem( horario.getSelectedItem() );
				valorAlt.setText( "" );
				calculateTotal();
				
			}
		} );
		add(plus);
		
		JButton button = new JButton("-");
		button.setBounds(308, 155, 42, 23);
		button.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if( table.getSelectedRow() >= 0 ){
					
					horario.addItem(  (Item) model.getValueAt( table.getSelectedRow(), 0 ) );
					model.removeRow( table.getSelectedRow() );
					calculateTotal();
					
				}
				
			}
		} );
		add(button);
		
		txt = new JLabel("Valor total:");
		txt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txt.setBounds(10, 341, 87, 23);
		add(txt);
		
		total = new JTextField();
		total.setEditable(false);
		total.setBounds(106, 344, 97, 20);
		add(total);
		
		save = new JButton("Registrar conta");
		save.setBounds(106, 386, 155, 23);
		save.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				String error = "";
				
				if( dateChooser.getDate() == null )
					error += "Escolha a data de vencimento!";
				if( table.getRowCount() < 0 )
					error += "\nA conta precissa de pelo menos um serviço executado!";
				if( error.length() > 0 ){
					JOptionPane.showMessageDialog( null,  error );
					return;
				}

				if( parcelas.getText().length() > 0 ){
					
					int par = Integer.parseInt( parcelas.getText() );
					String sql = "INSERT INTO conta( valor, dataVencimento ) VALUES( '";
					float valorParcela = Float.parseFloat( total.getText() ) / par;
					int index;
					
					for( int i = 0; i < par; i ++ ){
						
						Calendar c =  dateChooser.getCalendar();
						c.add( Calendar.DATE, 30 * ( i + 1) );
						index = Conexao.insert( sql + valorParcela + "', '" +
						new SimpleDateFormat( "dd/MM/yyyy" ).format( c.getTime() ) + "')" );
						insertRows( index );
						
					}
					
					clear();
					
				}else{
					
					int index = Conexao.insert( "INSERT INTO conta( valor, dataVencimento ) VALUES ('"+ Float.parseFloat( total.getText() ) 
							+ "', '"+ new SimpleDateFormat( "dd/MM/yyyy" ).format( dateChooser.getDate() ) + "')" );
					insertRows( index );
					clear();
					
				}
				
			}
				
		} );
		add(save);
		
	}
	
	protected void clear() {

		JOptionPane.showMessageDialog( null, "Conta Registrada!");
		horario.atualizeItens();
		valorAlt.setText( "" );
		for( int i = model.getRowCount() - 1; i >= 0; i -- )
			model.removeRow( i );
		total.setText( "" );
		parcelas.setText( "" );
		
	}

	public void insertRows( int index ){
		
		for( int i = 0; i < model.getRowCount(); i++){
			
			String tmp = ((String) model.getValueAt( i, 1 )).replaceAll( "[,]", ".");
			Conexao.update( "INSERT INTO horarios_has_conta( Horarios_idHorarios, Conta_idConta, valorAlternativo )"
					+ " VALUES ( '"+ ((Item) model.getValueAt( i, 0 )).getId() + "', '"+ index + "', '"
					+ Float.parseFloat( tmp ) + "')" );
			
		}
		
	}
	
	public void openFor( CellAgenda cell ){
		
		cliente.selectItemById( cell.getClienteId() );
		horario.selectItemById( cell.getIdHorario() );
		
	}
	
	public void calculateTotal(){
		
		float total = 0;
		
		for( int i = 0; i < model.getRowCount(); i++ ){
			
			total += Float.parseFloat( ( (String) model.getValueAt( i, 1 )).replaceAll( "[,]", ".") );
			
		}
		
		this.total.setText( total + "" );
		
	}
	
}
