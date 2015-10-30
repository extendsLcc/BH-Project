package br.agenda;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import br.main.Conexao;

public class CellAgenda {
	
	private int idHorario,
	clienteId,
	funcionarioId,
	servicoId;
	private boolean pago,
	concluido;
	private String hourInit,
	hourEnd,
	observacao, 
	nomeCliente;
	
	private Color background;
	private List<Integer> rows = new ArrayList<Integer>();
	
	public void commit(){
		
		String sql = "UPDATE horarios SET horarioInit='"+ hourInit + "', horarioEnd='"+ hourEnd +"', pago='"+ ( pago ? 1 : 0 ) +
				"', concluido='"+ (concluido ? 1 : 0) +"', observacao='"+ observacao +"', Cliente_Pessoa_idPessoa='"+ clienteId +
				"', Servicos_idServicos='"+ servicoId + "' WHERE idHorarios=" + idHorario;
		Conexao.update( sql );
		
	}

	public void drop() {

		Conexao.update( "DELETE FROM horarios WHERE idHorarios=" + getIdHorario() );
		
	}
	
	public List<Integer> getRows(){
		
		return rows;
		
	}
	
	public boolean isConcluido() {
		return concluido;
	}

	public void setConcluido(boolean concluido) {
		this.concluido = concluido;
	}

	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

	public int getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(int funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public int getServicoId() {
		return servicoId;
	}

	public void setServicoId(int servicoId) {
		this.servicoId = servicoId;
	}

	public boolean isPago() {
		return pago;
	}

	public void setPago(boolean pago) {
		this.pago = pago;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}


	public int getIdHorario() {
		
		return idHorario;
		
	}
	
	public void setIdHorario(int idHorario) {
		
		this.idHorario = idHorario;
		
	}
	
	public String getHourInit() {
		
		return hourInit;
		
	}
	
	public void setHourInit(String hourInit) {
		
		this.hourInit = hourInit;
		
	}
	
	public String getHourEnd() {
		
		return hourEnd;
		
	}
	
	public void setHourEnd(String hourEnd) {
		
		this.hourEnd = hourEnd;
		
	}
	
	public String getNomeCliente() {
		
		return nomeCliente;
		
	}
	
	public void setNomeCliente(String nomeCliente) {
		
		this.nomeCliente = nomeCliente;
		
	}
	
	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	@Override
	public String toString(){
		
		return nomeCliente;
		
	}

}
