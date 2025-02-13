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

        // Agregar manejador de mensajes para procesar respuestas del servidor
        ConnectionManager.getInstance().addMessageHandler(message -> {
            System.out.println("Mensaje recibido del servidor: " + message); // Depuración
            if (message.startsWith("ID_VALIDATION:")) {
                handleIdValidationResponse(message);
            } else if (message.startsWith("REGISTRATION_RESPONSE:")) {
                handleRegistrationResponse(message);
            }
        });

        // Conectar al servidor al iniciar la ventana
        ConnectionManager.getInstance().connect("localhost", 5000);
    }

    @FXML
    public void registrerOperator(ActionEvent event) {
        if (validateFields()) {
            // Validar la identificación con el servidor
            String validationMessage = "VALIDATE_ID:" + tfIdentification.getText().trim();
            System.out.println("Enviando mensaje al servidor: " + validationMessage); // Depuración
            ConnectionManager.getInstance().sendMessage(validationMessage);
        }
    }

    private void handleIdValidationResponse(String message) {
        String[] parts = message.split(":");
        if (parts.length > 1) {
            javafx.application.Platform.runLater(() -> {
                if (parts[1].equals("EXISTS")) {
                    lMessageErrorIdentification.setText("Esta identificación ya está registrada");
                } else {
                    // Proceder con el registro
                    proceedWithRegistration();
                }
            });
        }
    }

    private void proceedWithRegistration() {
        // Enviar mensaje de registro al servidor
        String registrationMessage = "OPERATOR_REGISTER:" +
                tfIdentification.getText().trim() + ":" +
                tfSurnames.getText() + ":" +
                tfName.getText() + ":" +
                tfPassword.getText().trim();
        System.out.println("Enviando mensaje al servidor: " + registrationMessage); // Depuración
        ConnectionManager.getInstance().sendMessage(registrationMessage);
    }

    private void handleRegistrationResponse(String message) {
        String[] parts = message.split(":");
        if (parts.length > 1) {
            javafx.application.Platform.runLater(() -> {
                if (parts[1].equals("SUCCESS")) {
                    System.out.println("Registro exitoso. Abriendo ventana de inicio de sesión."); // Depuración
                    openLoginWindow();
                } else if (parts[1].equals("ID_EXISTS")) {
                    lMessageErrorIdentification.setText("Esta identificación ya está registrada");
                } else {
                    lMessageErrorPassword.setText("Error al registrar el operador");
                }
            });
        }
    }

    private void openLoginWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUILoginOperators.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e -> closeWindows());

            // Cerrar ventana actual
            Stage currentStage = (Stage) btnRegistrer.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

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