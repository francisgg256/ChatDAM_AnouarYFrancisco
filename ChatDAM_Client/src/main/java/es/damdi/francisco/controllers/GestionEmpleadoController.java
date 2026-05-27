package es.damdi.francisco.controllers;

import es.damdi.francisco.utils.SeguridadUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class GestionEmpleadoController {

    @FXML private TextField txtNuevoUsuario;
    @FXML private PasswordField txtNuevaPassword;
    @FXML private Label lblMensaje;

    @FXML
    public void registrarEmpleado(ActionEvent event) {
        String usuario = txtNuevoUsuario.getText();
        String password = txtNuevaPassword.getText();

        if (usuario.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Rellena todos los campos.");
            lblMensaje.setStyle("-fx-text-fill: red;");
            return;
        }

        // Ciframos la contraseña tal y como pide el profesor
        String passwordCifrada = SeguridadUtil.cifrarContrasena(password);

        // TODO: Aquí llamaremos a ApiService para registrarlo en Spring Boot
        System.out.println("Enviando a la API -> Usuario: " + usuario + " | Hash: " + passwordCifrada);

        lblMensaje.setText("¡Empleado registrado con éxito!");
        lblMensaje.setStyle("-fx-text-fill: green;");
        txtNuevoUsuario.clear();
        txtNuevaPassword.clear();
    }
}
