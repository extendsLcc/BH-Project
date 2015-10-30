package br.cadastro.customfields;

public class Item {
	
	private int id;
	private String nome; // output
	
	public Item( int whichId, String whichName ){
		
		id = whichId;
		nome = whichName;
		
	}
	
	public int getId(){
		
		return id;
		
	}
	
	public String getName(){
		
		return nome;
		
	}
	
	@Override
	public String toString(){
		
		return nome;
		
	}

}
