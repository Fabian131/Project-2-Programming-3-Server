package business;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GUIStartServerController {
    @FXML
    private Button btnInitServer;
    @FXML
    private Label lMessageErrorIP;
    @FXML
    private Label lMessageErrorPort;

    // Event Listener on Button[#btnInitServer].onAction
    @FXML
    public void InitServer(ActionEvent event) {
        // Iniciar el servidor en un hilo separado
        Thread serverThread = new Thread(() -> {
            Server server = new Server();
            server.startServer();
        });
        serverThread.setDaemon(true); // El hilo se detendrá cuando la aplicación termine
        serverThread.start();

        // Abrir la ventana de inicio de sesión
        openLoginWindow();
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
            Stage currentStage = (Stage) btnInitServer.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void closeWindows() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUILoginOperators.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage temp = (Stage) btnInitServer.getScene().getWindow();
            temp.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}