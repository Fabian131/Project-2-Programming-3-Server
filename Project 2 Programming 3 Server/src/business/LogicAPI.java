package business;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.scene.control.TextField;

public class LogicAPI {
	
	public static StringBuilder getAPI(String identification) {
		
		String directionUrl = "https://api.hacienda.go.cr/fe/ae?identificacion=" + identification.trim(); 
		StringBuilder response; 
		try {
			
			URL url = new URL(directionUrl); 
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 	
			connection.setRequestMethod("GET");
			
			int responseCode = connection.getResponseCode();	
			if(responseCode == 200) {		
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				response = new StringBuilder();
				String line; 
					
				while((line = reader.readLine()) != null) {		
					response.append(line); 
					//System.out.println(response);
				}
				reader.close();
			}else {
				throw new RuntimeException("Error al conectarnos al API code " + responseCode);
			}
			
		}catch(Exception e) {
			throw new RuntimeException(e); 
		}	
		return response; 
	}
	
	public static String descomposeAPI(StringBuilder API) {
		String result = ""; 
		
		
		String firstPart = API.toString(); 
		
		String[] secondPart = firstPart.split(","); 
		
		String thirdPart = secondPart[0]; //se extrae el nombre pero todavia falta
		
		String[] quarterPart = thirdPart.split(":"); 
		
		String name = quarterPart[1].trim(); //aqui ya extraimos el nombre pero con los "
		
		String nameYes = name.substring(1, name.length()-1); //aqui ya está siuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu
		
		result += nameYes; 
		
		return result; 
	}
	
	public static void setValues(TextField tfSurnames, TextField tfName, String identification) {
		
		String nameCompleted = descomposeAPI(getAPI(identification)); 
		
		String[] parts = nameCompleted.split(" "); 
		String surnames = " "; 
		String names = " "; 
		
		if(parts.length == 4) {
			
			surnames = parts[parts.length-2] + " " + parts[parts.length-1]; 
			tfSurnames.setText(surnames);
			names = parts[parts.length-4] + " " + parts[parts.length-3];
			tfName.setText(names);
		}else {
			surnames = parts[parts.length-2] + " " + parts[parts.length-1];
			tfSurnames.setText(surnames);
			names = parts[parts.length-3];
			tfName.setText(names);
		}
			
	}
	
	 public static boolean validatePassword(String password) {
	        if (password == null || password.length() < 9) { // Mínimo 9 caracteres para cumplir 4 letras, 4 números y 1 símbolo
	            return false;
	        }

	        int letters = 0, numbers = 0, specials = 0;

	        for (char c : password.toCharArray()) {
	            if (Character.isLetter(c)) {
	            	letters++;
	            } else if (Character.isDigit(c)) {
	            	numbers++;
	            } else if ("!@#$%^&*()-_=+|;:'\",.<>?/".contains(String.valueOf(c))) {
	            	specials++;
	            }
	        }

	        return letters >= 4 && numbers >= 4 && specials >= 1;
	    }

	
}
