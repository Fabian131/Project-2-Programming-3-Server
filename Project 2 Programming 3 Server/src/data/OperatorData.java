package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import domain.Operator;

public class OperatorData {
	

	public static LinkedList<Operator> getAll(){
		
		LinkedList<Operator> list = new LinkedList<Operator>(); 
		
		try {
			Connection cn = DBConnection.getConnection(); 
			String query = "{call getAllOperator}"; 
			CallableStatement stmt = cn.prepareCall(query); 
			ResultSet rs = stmt.executeQuery(); 
			
			while(rs.next()) {
				Operator c = new Operator(); 
				c.setId(rs.getInt(1));
				c.setSurnames(rs.getString(2));
				c.setName(rs.getString(3));
				
				list.add(c); 
			
			}
			
		}catch(SQLException e) {
			
		}
		
		
		return list; 
	}
	
	public static void saveOperator(Operator opera) {
		
		try {
			Connection cn = DBConnection.getConnection(); 
			String query = "{call saveOperator(?,?)}"; //cada signo es un parametro
			CallableStatement stmt = cn.prepareCall(query); 
			stmt.setString(1, opera.getSurnames());
			stmt.setString(2, opera.getName());
			
			stmt.executeQuery(); 
			
			System.out.println("guardado correctamente");

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
