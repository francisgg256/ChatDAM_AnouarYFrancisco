package es.damdi.francisco.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ErrorLoginController {
    @FXML
    public void salir(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
}
