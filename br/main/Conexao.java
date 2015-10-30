package br.main;


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
	
	public static int insert( String sqlComand ){
		
		try {
			
			Statement stmt = (Statement) conect.createStatement();
			stmt.executeUpdate( sqlComand, Statement.RETURN_GENERATED_KEYS );
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			int i = rs.getInt( 1 );
			stmt.close();
			return i;
			
		} catch (SQLException e) {

			return Integer.parseInt( sqlComand.replaceAll( "[^\\d.]", "" ) ); //POG Extreme
			
		}
		
	}
	
	public static void updateHandled( String sqlComand ) throws SQLException{

			
		Statement stmt = (Statement) conect.createStatement();
		stmt.executeUpdate( sqlComand );
		stmt.close();
		
	}
	
	public static void update( String sqlComand ){
		
		try {
			
			Statement stmt = (Statement) conect.createStatement();
			stmt.executeUpdate( sqlComand );
			stmt.close();
			
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog( null, "Erro Interação com o banco de dados :\n" + e.getMessage());
			
		}
		
	}
	
}
