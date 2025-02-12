package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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
				Operator o = new Operator(); 
				o.setIdDB(rs.getInt(1));
				o.setIdentification(rs.getString(2));
				o.setName(rs.getString(3));
				o.setSurnames(rs.getString(4));
				o.setPassword(rs.getString(5));
				o.setNumberOfEmergenciesAttended(rs.getInt(6));
				o.setAvailable(isSelected(rs.getInt(7)));
				list.add(o); 
			
			}
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error al obtener a los operadores");
		}
		return list; 
	}
	
	public static boolean isSelected(int value) {
		if (value==0) {
			return false;
		}else if (value==1) {
			return true;
		}else{
			return false;
		}
	}
	
	public static void saveOperator(Operator opera) {
		try {
			Connection cn = DBConnection.getConnection(); 
			String query = "{call saveOperator(?,?,?,?,?,?)}"; //cada signo es un parametro
			CallableStatement stmt = cn.prepareCall(query);
			stmt.setInt(0, opera.getIdDB());
			stmt.setString(1, opera.getIdentification());
			stmt.setString(2, opera.getName());
			stmt.setString(3, opera.getSurnames());
			stmt.setString(4, opera.getPassword());
			stmt.setInt(5, opera.getNumberOfEmergenciesAttended());
			stmt.setInt(6, valueSelected(opera.isAvailable()));
			stmt.executeQuery(); 
			System.out.println("guardado correctamente");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int valueSelected(boolean info) {
		if (info == true) {
			return 1;
		}else if (info == false) {
			return 0;
		}else {
			return 0;
		}
	}
	
	public static boolean validateLoginOperator(String identification, String password) {
	    boolean exists = false;
	    try {
	        Connection cn = DBConnection.getConnection();
	        String query = "{call validateLoginOperator(?, ?, ?)}";
	        CallableStatement stmt = cn.prepareCall(query);
	        stmt.setString(1, identification);
	        stmt.setString(2, password);
	        stmt.registerOutParameter(3, java.sql.Types.INTEGER);
	        stmt.execute();
	        
	        exists = stmt.getInt(3) == 1;//1 si es valido el login, 0 si no es valido
	        
	        stmt.close();
	        cn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return exists;
	}
	
	public static boolean checkIdentification(String identification) {
	    boolean exists = false;
	    try {
	        Connection cn = DBConnection.getConnection();
	        String query = "{call checkIdentificationOperator(?, ?)}";
	        CallableStatement stmt = cn.prepareCall(query);
	        stmt.setString(1, identification);
	        stmt.registerOutParameter(2, java.sql.Types.INTEGER);
	        stmt.execute();
	        
	        exists = stmt.getInt(2) == 1;//1 si existe el la identificacion, 0 si no
	        
	        stmt.close();
	        cn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return exists;
	}
	
	 public static Map<String, Integer> getEmergenciesAttendedByOperator() {
	        Map<String, Integer> stats = new HashMap<>();
	        try {
	            Connection cn = DBConnection.getConnection();
	            String query = "{call getEmergenciesAttendedByOperator}"; // Procedimiento almacenado
	            CallableStatement stmt = cn.prepareCall(query);
	            ResultSet rs = stmt.executeQuery();

	            while (rs.next()) {
	                String operatorName = rs.getString("operator_name"); // Columna "operator_name" en la base de datos
	                int count = rs.getInt("count"); // Columna "count" en la base de datos
	                stats.put(operatorName, count);
	            }

	            rs.close();
	            stmt.close();
	            cn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return stats;
	    }
	
}
