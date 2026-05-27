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

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblError;

    @FXML
    public void iniciarSesion(ActionEvent event) {
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();
        lblError.setText("");

        if (usuario.isEmpty() || password.isEmpty()) {
            lblError.setText("Por favor, rellena todos los campos.");
            return;
        }

        String passwordCifrada = es.damdi.francisco.utils.SeguridadUtil.cifrarContrasena(password);

        es.damdi.francisco.services.ApiService apiService = new es.damdi.francisco.services.ApiService();
        String rolRecibido = apiService.hacerLogin(usuario, passwordCifrada);

        if (rolRecibido != null) {
            SesionGlobal.usuarioActual = usuario;
            SesionGlobal.rolActual = rolRecibido;

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/damdi/francisco/Chat.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(new Scene(root, 600, 500));
                stage.setTitle("Chat Corporativo - Rol: " + SesionGlobal.rolActual);
                stage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/damdi/francisco/ErrorLogin.fxml"));
                Parent root = loader.load();
                Stage errorStage = new Stage();
                errorStage.setScene(new Scene(root));
                errorStage.setTitle("Error de Login");
                errorStage.setResizable(false);
                errorStage.show();

                ((Stage) btnLogin.getScene().getWindow()).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
