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

        // Limpiamos mensajes de error anteriores antes de procesar
        lblError.setText("");

        // Validación básica de campos
        if (usuario.isEmpty() || password.isEmpty()) {
            lblError.setText("Por favor, rellena todos los campos.");
        } else if (password.equals("1234")) {

            // Guardamos el usuario en la sesión global para que sea accesible en otras vistas
            SesionGlobal.usuarioActual = usuario;

            lblError.setStyle("-fx-text-fill: green;");
            lblError.setText("¡Login correcto! Entrando al chat...");

            try {
                // Cargamos el diseño del chat
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/damdi/francisco/Chat.fxml"));
                Parent root = loader.load();

                // Obtenemos el Stage (ventana) actual para cambiar su escena
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(new Scene(root, 600, 500));
                stage.setTitle("Chat Corporativo - Sala Principal");
                stage.centerOnScreen();

            } catch (IOException e) {
                // Manejo de errores en caso de que el archivo FXML no se encuentre
                e.printStackTrace();
                lblError.setStyle("-fx-text-fill: red;");
                lblError.setText("Error crítico al cargar el chat.");
            }

        } else {
            // Error en la autenticación
            lblError.setStyle("-fx-text-fill: red;");
            lblError.setText("Contraseña incorrecta (Usa 1234).");
        }
    }
}
