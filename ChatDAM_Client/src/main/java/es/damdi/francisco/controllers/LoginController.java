package es.damdi.francisco.controllers;

import es.damdi.francisco.utils.SesionGlobal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador de la vista de login.
 * Gestiona la validación de las credenciales del usuario, el establecimiento
 * de la sesión global y la transición a la pantalla principal del chat.
 */
public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblError;

    /**
     * Procesa la acción de inicio de sesión al pulsar el botón correspondiente.
     * Verifica que los campos no estén vacíos y valida la contraseña.
     * En caso de éxito, guarda el usuario en SesionGlobal y carga la vista de chat.
     * * @param event El evento de acción disparado por el botón de login.
     */
    @FXML
    public void iniciarSesion(ActionEvent event) {
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();
        lblError.setText("");

        if (usuario.isEmpty() || password.isEmpty()) {
            lblError.setText("Por favor, rellena todos los campos.");
            return;
        }

        // Simulación de validación (Hasta que se conecte la API)
        if (password.equals("1234")) {
            SesionGlobal.usuarioActual = usuario;
            // Asignamos el rol según el nombre
            SesionGlobal.rolActual = usuario.toLowerCase().equals("admin") ? "ADMINISTRADOR" : "EMPLEADO";

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/damdi/francisco/Chat.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(new Scene(root, 600, 500));
                stage.setTitle("Chat Corporativo - " + SesionGlobal.rolActual);
                stage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // REQUISITO OBLIGATORIO: Abrir nueva ventana de error y cerrar app
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/damdi/francisco/ErrorLogin.fxml"));
                Parent root = loader.load();
                Stage errorStage = new Stage();
                errorStage.setScene(new Scene(root));
                errorStage.setTitle("Error de Login");
                errorStage.setResizable(false);
                errorStage.show();

                // Cerramos la ventana de login actual
                ((Stage) btnLogin.getScene().getWindow()).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
