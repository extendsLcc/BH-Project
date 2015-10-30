package br.cadastro.customfields;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

public class DateChooser extends JDateChooser implements CustomField{

	public String id = "";
	public Boolean enabled = true;
	
	public DateChooser( String id ){

		super(  new Date( System.currentTimeMillis()) );
		getJCalendar().setTodayButtonVisible( true );
		setId( id );
		
	}

	public void setId(String s) {
		
		id = s;

	}

	public String getId() {

		return id;
		
	}

	@Override
	public Object getValue() {
		
		DateFormat formatter = new SimpleDateFormat( "dd/MM/yyyy" );
		String s = "";
		
		try{
			
			s = formatter.format( getDate() );
			
		}catch( Exception e ){}
		
		return s;
		
	}

	@Override
	public void setValue(Object o) {

		DateFormat formatter = new SimpleDateFormat( "dd/MM/yyyy" );
		
		try {
			
			setDate( (Date) formatter.parse( (String) o ) );
			
		} catch ( Exception e) {

			setDate( null );
			
		}
		
	}

	public void toggle() {

		enabled = !enabled;
		
	}
	
	public boolean enabled() {
		
		return enabled;
		
	}
	
}
