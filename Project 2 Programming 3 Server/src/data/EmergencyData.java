package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import domain.Emergency;


public class EmergencyData {
	

	public static LinkedList<Emergency> getAll(){
		
		LinkedList<Emergency> list = new LinkedList<Emergency>(); 
		
		try {   
			Connection cn = DBConnection.getConnection(); 
			String query = "{call getAllEmergency}"; 
			CallableStatement stmt = cn.prepareCall(query); 
			ResultSet rs = stmt.executeQuery(); 
			
			while(rs.next()) {
				Emergency c = new Emergency(); 
				c.setId(rs.getInt(1));
				c.setIdUser(rs.getString(2));
				c.setTypeEmergency(rs.getString(3));
				c.setUbication(rs.getString(4));
				c.setDescription(rs.getString(5));
				
				list.add(c); 
			
			}
			
		}catch(SQLException e) {
			
		}
		
		
		return list; 
	}
	
	public static void saveEmergency(Emergency emer) {
		
		try {
			Connection cn = DBConnection.getConnection(); 
			String query = "{call saveEmergency(?,?,?,?)}"; //cada signo es un parametro
			CallableStatement stmt = cn.prepareCall(query); 
			stmt.setString(1, emer.getIdUser());
			stmt.setString(2, emer.getTypeEmergency());
			stmt.setString(3, emer.getUbication());
			stmt.setString(4, emer.getDescription());
			
			stmt.executeQuery(); 
			
			System.out.println("guardado correctamente");

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
