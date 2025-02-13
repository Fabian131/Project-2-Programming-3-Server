package business;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    public void initialize() {
        // Agregar manejador de mensajes para procesar respuestas del servidor
        ConnectionManager.getInstance().addMessageHandler(message -> {
            if (message.startsWith("LOGIN_RESPONSE:")) {
                handleLoginResponse(message);
            }
        });

        // Conectar al servidor al iniciar la ventana
        ConnectionManager.getInstance().connect("localhost", 5000);
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

        // Enviar mensaje de inicio de sesión al servidor
        String loginMessage = "OPERATOR_LOGIN:" + identification + ":" + password;
        ConnectionManager.getInstance().sendMessage(loginMessage);
    }

    private void handleLoginResponse(String message) {
        String[] parts = message.split(":");
        if (parts.length > 1) {
            javafx.application.Platform.runLater(() -> {
                if (parts[1].equals("SUCCESS")) {
                    // Inicio de sesión exitoso, abrir la ventana de control
                    closeWindows();
                } else if (parts[1].equals("WRONG_PASSWORD")) {
                    lMessageErrorPassword.setText("Identificación o contraseña incorrecta");
                }
            });
        }
    }

    // Event Listener on Button[#btnCreateAccount].onAction
    @FXML
    public void goToCreateAccount(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIRegistrationOperators.fxml"));
            Parent root = loader.load(); // Cargar la escena
            GUIRegistrationOperatorsController controller = loader.getController(); // Cargar el controlador asociado a la vista
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e -> controller.closeWindows());

            // Cerrar la ventana actual
            Stage temp = (Stage) this.btnGo.getScene().getWindow();
            temp.close();
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

            // Cerrar la ventana actual
            Stage temp = (Stage) btnGo.getScene().getWindow();
            temp.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}