package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import data.OperatorData;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

public class GUILoginOperatorsController {
	@FXML
	private TextField tfIdentificationStart;
	@FXML
	private TextField tfPasswordStart;
	@FXML
	private Button btnGo;
	@FXML
	private Label lMessageErrorPassword;
	@FXML
	private Button btnCreateAccount;
	@FXML
	private Label lMessageErrorIdentification;
	public GUILoginOperatorsController() {
		
	}
	// Event Listener on Button[#btnGo].onAction
	@FXML
	public void goToStart(ActionEvent event) {
		 String identification = tfIdentificationStart.getText().trim();
	        String password = tfPasswordStart.getText().trim();

	        if (identification.isEmpty()) {
	            lMessageErrorIdentification.setText("La identificación es requerida");
	            return;
	        }

	        if (password.isEmpty()) {
	            lMessageErrorPassword.setText("La contraseña es requerida");
	            return;
	        }

	        // Validar las credenciales del operador
	        boolean isValid = OperatorData.validateLoginOperator(identification, password);
	        if (isValid) {
	            closeWindows();
	        } else {
	            lMessageErrorPassword.setText("Identificación o contraseña incorrecta");
	        }
	        
	}
	// Event Listener on Button[#btnCreateAccount].onAction
	@FXML
	public void goToCreateAccount(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIRegistrationOperators.fxml"));
			Parent root = loader.load();//cargar la escena
			GUIRegistrationOperatorsController controller = loader.getController();//cargar el controlador que esta asociado a la vista
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			stage.setOnCloseRequest(e->controller.closeWindows());
			//cerramos como tal la vista
			Stage tem = (Stage) this.btnGo.getScene().getWindow();
			tem.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void closeWindows() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIControlCenter.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			Stage temp = (Stage) btnGo.getScene().getWindow();
			temp.close();
			//cargar style (por el momento no la vamos a agregar, pero esta linea es la misma que en el main)
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
}
