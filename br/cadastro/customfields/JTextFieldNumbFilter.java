package br.cadastro.customfields;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public final class JTextFieldNumbFilter extends PlainDocument {
	
	   public static final String NUMERIC =
	        "0123456789";
	   public static final String FLOAT =
	        NUMERIC + ",";

	   protected String acceptedChars = null;
	   protected int decimal = 0;
	   protected int limit;  // char limit
	 
	   public JTextFieldNumbFilter( int max, int decimal ) {
		   
		   if( decimal > 0 )
			   acceptedChars = FLOAT;
		   else
			   acceptedChars = NUMERIC;
		   this.decimal = decimal;
		   limit = max;
	     
	    }

	   public void insertString (int offset, String  str, AttributeSet attr) throws BadLocationException {
	  
		   if (str == null || getLength() == limit ) return;
		   
		   str = str.replaceAll( "[.]", ",");
		   
		   for (int i=0; i < str.length(); i++) {
			   
			   int virgula = getText( 0, getLength() ).indexOf( "," );
			   
		       if (acceptedChars.indexOf(str.valueOf(str.charAt(i))) == -1  || ( virgula != -1 && virgula + decimal < getLength() && offset > virgula)){
		    	   
		    	   java.awt.Toolkit.getDefaultToolkit().beep();  
		    	   return;
		    	   
		       }
		       
	       }

		   if (acceptedChars.equals(FLOAT) ) {
			   if (str.indexOf(",") != -1) {
				   if (getText(0, getLength()).indexOf(",") != -1) {
					   java.awt.Toolkit.getDefaultToolkit().beep();  
					   return;
				   }
			   }
		   }
		
		   super.insertString(offset, str, attr);
				   
	   }
	   
}
