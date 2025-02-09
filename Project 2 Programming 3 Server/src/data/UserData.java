package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import domain.User;

public class UserData {
	

	public static LinkedList<User> getAll(){
		
		LinkedList<User> list = new LinkedList<User>(); 
		
		try {
			Connection cn = DBConnection.getConnection(); 
			String query = "{call getAllClient}"; 
			CallableStatement stmt = cn.prepareCall(query); 
			ResultSet rs = stmt.executeQuery(); 
			
			while(rs.next()) {
				User c = new User(); 
				c.setId(rs.getInt(1));
				c.setIdentification(rs.getString(2));
				c.setSurnames(rs.getString(3));
				c.setName(rs.getString(4));
				c.setPassword(rs.getString(5));
				
				list.add(c); 
			
			}
			
		}catch(SQLException e) {
			
		}
		
		
		return list; 
	}
	
	public static void saveUser(User user) {
		
		try {
			Connection cn = DBConnection.getConnection(); 
			String query = "{call saveClient(?,?,?,?)}"; //cada signo es un parametro
			CallableStatement stmt = cn.prepareCall(query); 
			stmt.setString(1, user.getIdentification());
			stmt.setString(2, user.getSurnames());
			stmt.setString(3, user.getName());
			stmt.setString(4, user.getPassword());
			
			stmt.executeQuery(); 
			
			System.out.println("guardado correctamente");

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteUser(String identification) {
		
		try {
			Connection cn = DBConnection.getConnection(); 
			String query = "{call deleteClient(?)}"; //cada signo es un parametro
			CallableStatement stmt = cn.prepareCall(query); 
			stmt.setString(1, identification);
			stmt.executeQuery(); 
			
			System.out.println("Eliminado exitosamente");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
