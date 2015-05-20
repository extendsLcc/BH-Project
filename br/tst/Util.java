package br.tst;

import javax.swing.JDialog;

public class Util {
	
	public static void showDigalog( JDialog d ){

		d.setModal( true );
		d.setLocationRelativeTo( null );
		d.setVisible( true );
		
	}

}
