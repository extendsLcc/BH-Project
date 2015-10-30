package br.cadastro.customfields;

public interface CustomField {
	
	public String id = "";
	public Boolean enabled = true;
	
	public void setId( String s );
	
	public String getId();
	
	public Object getValue();
	
	public void setValue( Object o );
	
	public void toggle();

	public boolean enabled();
	
}
