package br.agenda;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.SwingConstants;

import com.toedter.calendar.DateUtil;
import com.toedter.calendar.IDateEvaluator;
import com.toedter.calendar.JCalendar;

import br.cadastro.customfields.Item;
import br.cadastro.customfields.NameList;
import br.main.Conexao;
import br.main.Util;
import br.main.Windows;
import br.windows.Conta;

import javax.swing.JButton;

public class Agenda extends JPanel {
	
	private JCalendar calendar;
	private FixedColumnTable horas;
	private JTable table;
	private AgendaModel agenda;
	private JScrollPane scroll;
	private NameList nameList;
	private Horario horario = new Horario( this );
	
	private final static Color[] bg = { 
			new Color(190, 205, 255),
			new Color(177, 235, 252) 
		};
	//private int bgInt = 0;
	
	
	public JCalendar getCalendar() {
		
		return calendar;
		
	}

	public Agenda() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setPreferredSize( new Dimension( 915, 540 ) );
		setLayout(null);
		

		// !!  initCalendar======================================================================================= 
		
		JLabel week = new JLabel("( 23 / 06 / 2015 )");
		week.setHorizontalAlignment(SwingConstants.CENTER);
		week.setFont(new Font("Tahoma", Font.BOLD, 20));
		week.setBounds(10, 40, 202, 27);
		add(week);
		
		JLabel date = new JLabel("Ter\u00E7a-Feira");
		date.setHorizontalAlignment(SwingConstants.CENTER);
		date.setFont(new Font("Tahoma", Font.BOLD, 20));
		date.setBounds(10, 11, 202, 27);
		add(date);
		
		calendar = new JCalendar( new Date( 1 ));
		calendar.getDayChooser().setWeekOfYearVisible(false);
		calendar.setTodayButtonVisible(true);
		calendar.setBounds(10, 78, 202, 200);
		calendar.getDayChooser().addPropertyChangeListener("day", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {

				String s = new SimpleDateFormat( "EEEE" ).format( calendar.getCalendar().getTime() );
				date.setText( s );
				s = new SimpleDateFormat( "( dd / MM / yyyy )" ).format( calendar.getCalendar().getTime() );
				week.setText( s );
				atualizeAgenda();
				
			}
		});
		calendar.getDayChooser().addDateEvaluator( new Evaluator() );
		add(calendar);


		// !!  endCalendar======================================================================================= 

		// !!  initFunc========================================================================================== 
		
		nameList = new NameList(new String[] {"funcao", "idFuncao", "nome"}){
			
			@Override
			public void atualizeItens(){
				
				super.atualizeItens();
				listModel.add( 0, new Item( -1, "Todas as funções" ));
				
			}
			
		};
		nameList.getSelectionModel().addListSelectionListener( new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {

				if( !e.getValueIsAdjusting() ){
					
					atualizeColumns();
					atualizeAgenda();
					
				}
				
			}
		});
		JScrollPane scr = new JScrollPane( nameList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		scr.setBounds(11, 370, 200, 130);
		add(scr);
		
		JLabel label = new JLabel("Filtrar Funcionarios por Fun\u00E7\u00F5es");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		label.setBounds(10, 344, 202, 14);
		add(label);
		
		JButton button = new JButton("Gerenciar Fun\u00E7\u00F5es");
		button.setBounds(20, 506, 180, 23);
		button.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				Util.showDialog( Agenda.this, Windows.funcoes );
				nameList.atualizeItens();
				
			}
		} );
		add(button);

		// !!  endFunc========================================================================================= 

		// !!  initTable======================================================================================= 

		agenda = new AgendaModel();
		table = new JTable( agenda );
		scroll = new JScrollPane( table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		scroll.setBounds( 241, 11, 664, 518);
		add( scroll );

		AgendacellRenderer  cellRenderer = new AgendacellRenderer();
		cellRenderer.setHorizontalAlignment( SwingConstants.CENTER );
		table.setDefaultRenderer( Object.class , cellRenderer );
		table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		table.setRowSelectionAllowed( false );
		table.setColumnSelectionAllowed( false );
		table.setCellSelectionEnabled( true );
		table.getColumnModel().setSelectionModel(new DefaultListSelectionModel(){

			@Override
			public void setSelectionInterval(int index0, int index1) { 
				
				super.setSelectionInterval(index0, index0); 
				
			}
			
		});
		table.getColumnModel().getSelectionModel().setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		table.getSelectionModel().setSelectionMode( ListSelectionModel.SINGLE_INTERVAL_SELECTION );
		table.setSelectionBackground( new Color( 204, 204, 204 ) );
		horas = new FixedColumnTable( 0, scroll);
		table.setAutoCreateColumnsFromModel(true);
		atualizeColumns();
		
		for( int i = 0; i < horas.getFixedTable().getModel().getRowCount(); i ++ ){
			
			agenda.addRow( new Object[]{ "" } );
			
		}
		
		// !!  endTable=======================================================================================

		JPopupMenu menu = new JPopupMenu();
		JMenuItem add = new JMenuItem( "Agendar" );
		menu.add( add );
		JMenuItem alter = new JMenuItem( "Alterar Reserva" );
		alter.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				horario.edit( (CellAgenda) agenda.getValueAt( table.getSelectedRow(), table.getSelectedColumn() ) );
				Util.showDialog( scroll, horario );
				atualizeAgenda();
				
			}
		});
		alter.setEnabled( false );
		menu.add( alter );
		JMenuItem delete = new JMenuItem( "Excluir" );
		delete.setEnabled( false );
		delete.addActionListener( new ActionListener() {
			
			ArrayList<CellAgenda> selectedCells = new ArrayList<CellAgenda>(); 
			
			@Override
			public void actionPerformed(ActionEvent arg0) {		// area de POG extreme perigo aos olhos

				int selection[] =  table.getSelectedRows();
				
				for( int i = 0; i < selection.length; i++ ){
					
					Object o = agenda.getValueAt( selection[i] , table.getSelectedColumn());
					
					if( o instanceof CellAgenda ){
						
						CellAgenda cell = (CellAgenda) o;
						if( cell.isConcluido() ) 
							continue;
						if( !selectedCells.contains( cell ) )
							selectedCells.add( cell );
						
						for( int x = 0; x < cell.getRows().size(); x++ ){
							
							if( cell.getRows().get( x ) == selection[i] ){
								
								cell.getRows().remove( x );
								agenda.setValueAt( AgendaModel.PLACEHOLDER , selection[i], table.getSelectedColumn());
								x--;
								
							}
							
						}
						
					}
					
				}
				
				for( CellAgenda cell : selectedCells){
					
					if( !cell.getRows().isEmpty() ){
						
						cell.setHourInit( horas.getTimeByRow( cell.getRows().get( 0 ) ) );
						cell.setHourEnd( horas.getTimeByRow( cell.getRows().get( cell.getRows().size() - 1 ) + 1) );
						cell.commit();
						
					}else
						cell.drop();
						
						
				}
				
				selectedCells.clear();
				
			}
		} );
		menu.add( delete );
		add.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if( table.getSelectedColumn() == -1 ) return;
				
				horario.open( agenda.getColumn( table.getSelectedColumn() ).getId(),
						horas.getTimeByRow( table.getSelectionModel().getMinSelectionIndex() ),
						horas.getTimeByRow( table.getSelectionModel().getMaxSelectionIndex() + 1 )  );
				Util.showDialog( scroll, horario );
				delete.doClick();
				atualizeAgenda();
				
			}
			
		} );
		menu.addSeparator();
		JMenuItem conclude = new JMenuItem( "Concluir Horario" );
		conclude.setEnabled( false );
		conclude.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				CellAgenda cell = (CellAgenda) agenda.getValueAt( table.getSelectedRow() , table.getSelectedColumn() );
				cell.setConcluido( true );
				cell.commit();
				
			}
		});
		menu.add( conclude );
		JMenuItem conta = new JMenuItem( "Abrir Conta" );
		conta.setEnabled( false );
		conta.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				Windows.tabbedPane.show( Windows.Conta );
				( (Conta) Windows.Conta.getComponent( 0 )).openFor(  
						(CellAgenda) agenda.getValueAt( table.getSelectedRow() , table.getSelectedColumn() ));
				
			}
		});
		menu.add( conta );
	
		table.addMouseListener( new MouseAdapter() {
			
			public void mousePressed(MouseEvent ev) {
				
		        if (ev.isPopupTrigger()) {
		        	
		         	menu.show(ev.getComponent(), ev.getX(), ev.getY());
		          
		        }
		        
		      }

		      public void mouseReleased(MouseEvent ev) {
		    	  
		        if (ev.isPopupTrigger()) {
		        	
		        	menu.show(ev.getComponent(), ev.getX(), ev.getY());
		        	
		        }
		        
		      }
		      
		});
		
		ListSelectionListener selectionListener = new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {

				if( !e.getValueIsAdjusting() && table.getSelectedRow() >= 0 ){

					int[] selection = table.getSelectedRows();
					Object first = agenda.getValueAt( selection[0] , table.getSelectedColumn() );
					Object last = agenda.getValueAt(  selection[selection.length - 1], table.getSelectedColumn() );
					add.setEnabled( true );
					
					try{
						
						CellAgenda c = (CellAgenda) agenda.getValueAt( selection[0] - 1, table.getSelectedColumn() ),
						c2 = (CellAgenda) agenda.getValueAt( selection[selection.length - 1] + 1, table.getSelectedColumn() );
						
						if( c.getIdHorario() == c2.getIdHorario() ){
							add.setEnabled( false );
							delete.setEnabled( false );
						}else
							delete.setEnabled( true );
						
					}catch( Exception ex ){}
					
					if( first instanceof CellAgenda && last instanceof CellAgenda ){

						if( ((CellAgenda) first).getIdHorario() == ((CellAgenda) last).getIdHorario() ){
							
							alter.setEnabled( true );
							if( !( (CellAgenda) first).isConcluido() ){
								conclude.setEnabled( true );
								conta.setEnabled( false );
							}else{
								
								conclude.setEnabled( false );
								if( !( (CellAgenda) first).isPago() )
									conta.setEnabled( true );
								
							}
							
						}else{
							
							alter.setEnabled( false );
							conclude.setEnabled( false );
							conta.setEnabled( false );
							
						}
						
						
					}else{

						alter.setEnabled( false );
						conclude.setEnabled( false );
						conta.setEnabled( false );
						boolean hasHorario = false;
						
						for( int i = 0; i < selection.length; i++){
							
							if( agenda.getValueAt( selection[i], table.getSelectedColumn() ) instanceof CellAgenda ){
								
								hasHorario = true;
								break;
								
							}
							
						}
						
						if( hasHorario )
							delete.setEnabled( true );
						else
							delete.setEnabled( false );
						
					}
					
				}
				
			}
			
		};
		table.getSelectionModel().addListSelectionListener( selectionListener );
		table.getColumnModel().getSelectionModel().addListSelectionListener( selectionListener );
		
		( (JButton) ( (JPanel) calendar.getComponent( 2 )).getComponent( 0 )).doClick(); //POG
		/// SELECIONAR HOJE
		
	}
	
	public void atualizeAgenda(){
		
		String sql = "SELECT idHorarios, horarioInit, horarioEnd, pessoa.nome as nomeCli, telefone_cel, servicos.nome as nomeServ, "
				+ "Funcionario_Pessoa_idPessoa as func, pago, concluido, observacao, Servicos_idServicos, Cliente_Pessoa_idPessoa as cli "
				+ " FROM horarios LEFT JOIN pessoa ON Cliente_Pessoa_idPessoa=idPessoa"
				+ " LEFT JOIN servicos ON Servicos_idServicos=idservicos WHERE data=";
		sql += "'" + new SimpleDateFormat( "dd/MM/yyyy" ).format( calendar.getDate() ) + "'";
		agenda.clearRows();
		ResultSet horariosDia = Conexao.query( sql );
		
		try {
			
			while( horariosDia.next() ){
				
				int idFuncionario = horariosDia.getInt( "func" );
				
				if( agenda.containsColumnId( idFuncionario ) ){
					
					CellAgenda cell = new CellAgenda();
					cell.setIdHorario( horariosDia.getInt( "idHorarios" ) );
					cell.setHourInit( horariosDia.getString( "horarioInit" ) );
					cell.setHourEnd( horariosDia.getString( "horarioEnd" ) );
					cell.setNomeCliente( horariosDia.getString( "nomeCli" ) );
					cell.setServicoId( horariosDia.getInt( "Servicos_idServicos" ) );
					cell.setClienteId( horariosDia.getInt( "cli" ) );
					cell.setFuncionarioId( idFuncionario );
					cell.setPago( horariosDia.getInt( "pago" ) == 1 );
					cell.setConcluido( horariosDia.getInt( "concluido" ) == 1 );
					cell.setObservacao( horariosDia.getString( "observacao" ) );
					int endIndex = horas.getTimeRowIndex( cell.getHourEnd() ),
					column = agenda.getColumnIndexById( idFuncionario );
					
					for( int i = horas.getTimeRowIndex( cell.getHourInit() ); i < endIndex; i++ ){
						
						agenda.setValueAt( cell, i, column );
						cell.getRows().add( i );
						
					}
					
				}
				
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		}
		
		///  backrgound cell	//deveria adaptar a rowlist da cellAgenda
		int lastIndex = -1,
		bgAlt = 0;	
		
		for( int c = 0; c < agenda.getColumnCount(); c++){
			
			for( int r = 0; r < agenda.getRowCount(); r++ ){
				
				try{

					CellAgenda cell = (CellAgenda) agenda.getValueAt( r, c );
					
					if( cell.getIdHorario() != lastIndex ){
						
						lastIndex = cell.getIdHorario();
						bgAlt = bgAlt == 1 ? 0 : 1;
						cell.setBackground( bg[bgAlt] );
						
					}

				}catch( Exception e ){}
				
			}
			
		}
		
	}
	
	public void atualizeColumns(){
		
		String sql = "SELECT idPessoa, pessoa.nome FROM Funcionario LEFT JOIN pessoa ON Pessoa_idPessoa=idPessoa";
		
		if( nameList.getSelectedIndex() > 0 ){
			
			sql += " RIGHT JOIN funcao_has_funcionario ON "
				+ "Funcionario_Pessoa_idPessoa=Pessoa_idPessoa WHERE funcao_idfuncao=" + nameList.getSelectedValue().getId();
			
		}
		
		agenda.removeAllColumns();
		ResultSet rs = Conexao.query( sql );

		try {
			
			while ( rs.next() ){
				
				agenda.addColumn( new Item( rs.getInt( 1 ), rs.getString( 2 ) ) );
				
			}
			
		} catch (SQLException e) {
		}
		
		for( int i = 0; i < table.getColumnCount(); i ++ ){
			
			table.getColumnModel().getColumn( i ).setMinWidth( 
					Math.max( 130, ( scroll.getWidth() - 78 - scroll.getVerticalScrollBar().getWidth() ) / table.getColumnCount() ) );
			
		}
		
	}
	
}

class Evaluator implements IDateEvaluator{
	
    private DateUtil dateUtil = new DateUtil();
    
    public Evaluator() {

    	dateUtil.setMinSelectableDate( null );
    	Calendar c = Calendar.getInstance();
    	c.add( Calendar.DATE, -1 );
    	dateUtil.setMaxSelectableDate( c.getTime() );

	}

	@Override
	public Color getInvalidBackroundColor() {

		return null;
		
	}

	@Override
	public Color getInvalidForegroundColor() {

		return null;
		
	}

	@Override
	public String getInvalidTooltip() {

		return null;
		
	}

	@Override
	public Color getSpecialBackroundColor() {
		
		return new Color(205, 209, 210);
		
	}

	@Override
	public Color getSpecialForegroundColor() {

		return null;
		
	}

	@Override
	public String getSpecialTooltip() {
		
		return "Dias passados";
		
	}

	@Override
	public boolean isInvalid(Date arg0) {
		return false;
	}

	@Override
	public boolean isSpecial(Date arg0) {
		
		return dateUtil.checkDate( arg0 );
		
	}
	
}