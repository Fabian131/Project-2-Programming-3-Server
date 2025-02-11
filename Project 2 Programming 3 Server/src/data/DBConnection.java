package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private final static String database = "proyecto_fabianyabi"; 
	private final static String user = "root"; 
	private final static String password = ""; 
	private final static String host = "localhost"; 
	//Puerto Abi: 3306, Puerto Fabian: 9999
	//Cambiar cada vez que trate de levantar
	private final static int port = 3306;
	private final static String url = "jdbc:mysql://" + host + ":" + port + "/" + database; 
	
	private static Connection con; 
	
	public static Connection getConnection() {
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			
		}catch(ClassNotFoundException e) { //excepcion cuando no encuentra una clase
			System.out.println("No se encontro la clase " + e.getMessage());
		}
		
		try {
			
			con = DriverManager.getConnection(url , user , password); 
			System.out.println("Conexion establecida correctamente"); 

			
		}catch(SQLException e) {
			System.out.println("Error de conexion " + e.getMessage()); 
		}
		
		return con; 
	}
	

}
