package br.main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Icon;
import javax.swing.JPanel;

public class Tab extends JPanel {
	
	private String title;
	private Icon ico;
	private String tooltip;
	private JPanel pane;
	private static GridBagLayout layout = new GridBagLayout();
	private static GridBagConstraints constraints = new GridBagConstraints();
	
	public Tab( JPanel content, String t, Icon i, String tip ){
		
		title = t;
		ico = i;
		tooltip = tip;
		setLayout( layout );
		constraints.gridx = 0;
		constraints.gridy = 0;
		pane = content;
		add( content, constraints);
		
	}
	
	public String getTitle(){
		
		return title;
		
	}
	
	public Icon getIcon(){
		
		return ico;
		
	}
	
	public String getTip(){
		
		return tooltip;
		
	}
	
	public JPanel getPane(){
		
		return pane;
		
	}

}
