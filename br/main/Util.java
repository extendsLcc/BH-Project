package br.main;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class Util {
	
	//  Mostra JDialog Centralizado
	
	public static void showDialog( Component parent, JDialog d ){
		
		d.setModal( true );
		d.setLocationRelativeTo( parent );
		d.setVisible( true );
		
	}

	
	// define width das colunas da jtable, consideranto o tamanho total seguido da porcentagem de cada coluna
	// source: http://www.codejava.net/java-se/swing/setting-column-width-and-row-height-for-jtable
	public static void setJTableColumnsWidth(JTable table, int tablePreferredWidth,
	        double... percentages) {
	    double total = 0;
	    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
	        total += percentages[i];
	    }
	 
	    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
	        TableColumn column = table.getColumnModel().getColumn(i);
	        column.setPreferredWidth((int)
	                (tablePreferredWidth * (percentages[i] / total)));
	    }
	}
	
	public static void p( Object s ){
		
		System.out.println( s );
		
	}
	
}
