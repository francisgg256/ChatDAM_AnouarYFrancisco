package es.damdi.francisco.controllers;

import es.damdi.francisco.utils.SeguridadUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class GestionEmpleadoController {

    @FXML private TextField txtNuevoUsuario;
    @FXML private PasswordField txtNuevaPassword;
    @FXML private ComboBox<String> cmbRol; // Referencia al nuevo ComboBox
    @FXML private Label lblMensaje;

    @FXML
    public void initialize() {
        // Llenamos el desplegable con los roles disponibles
        cmbRol.getItems().addAll("EMPLEADO", "ADMINISTRADOR");
        cmbRol.getSelectionModel().selectFirst(); // Por defecto selecciona EMPLEADO
    }

    @FXML
    public void registrarEmpleado(ActionEvent event) {
        String usuario = txtNuevoUsuario.getText();
        String password = txtNuevaPassword.getText();
        String rol = cmbRol.getValue(); // Obtenemos el rol elegido

        if (usuario.isEmpty() || password.isEmpty() || rol == null) {
            lblMensaje.setText("Rellena todos los campos.");
            lblMensaje.setStyle("-fx-text-fill: red;");
            return;
        }

        // Ciframos la contraseña
        String passwordCifrada = SeguridadUtil.cifrarContrasena(password);

        // CONEXIÓN REAL: Llamamos a la API enviando también el rol
        es.damdi.francisco.services.ApiService apiService = new es.damdi.francisco.services.ApiService();
        boolean exito = apiService.registrarEmpleado(usuario, passwordCifrada, rol);

        if (exito) {
            lblMensaje.setText("¡Usuario registrado como " + rol + "!");
            lblMensaje.setStyle("-fx-text-fill: green;");
            txtNuevoUsuario.clear();
            txtNuevaPassword.clear();
            cmbRol.getSelectionModel().selectFirst();
        } else {
            lblMensaje.setText("Error al registrar en el servidor.");
            lblMensaje.setStyle("-fx-text-fill: red;");
        }
    }
}
