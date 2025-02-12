package business;

import java.sql.Connection;

import data.DBConnection;
import data.OperatorData;
import domain.Operator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/presentation/GUILoginOperators.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			//Operator opera = new Operator("Juarez Bonaparte", "Chavo"); 
		//	OperatorData.saveOperator(opera);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		Connection connection = DBConnection.getConnection();
//		for (Operator o : OperatorData.getAll()) {
//			System.out.println(o.toString());
//		}
//		System.out.println("validar cedula "+OperatorData.checkIdentification("123"));
//		System.out.println("validar login: "+OperatorData.validateLoginOperator("12334","Fabian1234+"));
		launch(args);
	}
}
