package br.tst;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.*;

import javax.swing.JOptionPane;

public class Conexao {
	
	static String url = "jdbc:mysql://localhost:3306/salao";
	static String usr = "root";
	static String pwd = "ifpr123";
	static Connection conect;
	
	public static void initConnection(){
		
		try {
			
			conect = (Connection) DriverManager.getConnection( url, usr, pwd );
			
		}catch( SQLException e ){

			JOptionPane.showMessageDialog( null, "Erro na conexão do banco de dados :" + e.getMessage());
			
		}
		
	}
	
	public static Connection getConcection(){
		
		return conect;
	
	}
	
	public static ResultSet query( String queryComand ){
		
		try{
			
			Statement stmt = (Statement) conect.createStatement();
			ResultSet result = stmt.executeQuery( queryComand );
			return result;
			
		}catch ( SQLException e ){
			
			JOptionPane.showMessageDialog( null, "Erro consulta do banco de dados :" + e.getMessage());
			
		}
		
		return null;
		
	}
	
	public static boolean update( String insertComand ){

		try{
			
			Statement stmt = (Statement) conect.createStatement();
			stmt.executeUpdate( insertComand );
			return true;
			
		}catch ( SQLException e ){	
			
			JOptionPane.showMessageDialog( null, "Erro na alteração de dados no DB \n" + e.getMessage());
			
		}
		
		return false;
		
	}
	
}
