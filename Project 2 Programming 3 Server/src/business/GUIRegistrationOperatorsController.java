package business;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUIRegistrationOperatorsController {
	@FXML
	private TextField tfIdentification;
	@FXML
	private TextField tfSurnames;
	@FXML
	private TextField tfName;
	@FXML
	private Label lMessageErrorIdentification;
	@FXML
	private Button btnRegistrer;
	@FXML
	private TextField tfPassword;
	@FXML
	private Label lMessageErrorPassword;
	@FXML
	private Button btnReturn;
	public void initialize() {
		addTextFieldListener(); 
	}
	// Event Listener on Button[#btnRegistrer].onAction
	@FXML
	public void registrerOperator(ActionEvent event) {
		System.out.println("Identificación: " + tfIdentification.getText());
        System.out.println("Apellidos: " + tfSurnames.getText());
        System.out.println("Nombre: " + tfName.getText());
        System.out.println("Contraseña: " + tfPassword.getText());
	}
	// Event Listener on Button[#btnReturn].onAction
	@FXML
	public void returnToLogin(ActionEvent event) {
		closeWindows();
	}
	
	public void closeWindows() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUILoginOperators.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			Stage temp = (Stage) btnRegistrer.getScene().getWindow();
			temp.close();
			//cargar style (por el momento no la vamos a agregar, pero esta linea es la misma que en el main)
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public boolean validateFields() {

		if (tfIdentification.getText().trim().isEmpty()) {
			lMessageErrorIdentification.setText("La identificación es requerida");
			return false;
		}

		if (tfPassword.getText().trim().isEmpty()) {
			lMessageErrorPassword.setText("La contraseña es requerida");
			return false;
		}
		
		return true; 
	}
	
	private void addTextFieldListener() {
		PauseTransition pause = new PauseTransition(Duration.seconds(1)); // Espera 1 segundo

		tfIdentification.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				pause.setOnFinished(event -> {
					if (newValue.trim().length() == 9) { // Solo ejecuta si hay 9 dígitos
						LogicAPI.setValues(tfSurnames, tfName, newValue.trim());
					}
				});
				pause.playFromStart(); // Reinicia 
			}
		});
	}
}
