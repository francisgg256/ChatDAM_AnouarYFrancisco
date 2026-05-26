package es.damdi.francisco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal que extiende de {@link Application} para inicializar
 * la interfaz gráfica de usuario (GUI) de la aplicación.
 * Gestiona la carga del archivo FXML inicial y la configuración de la ventana principal.
 */
public class Main extends Application {

    /**
     * Punto de entrada de la interfaz gráfica de JavaFX.
     * Configura y muestra el escenario (Stage) principal con la escena (Scene) de login.
     * @param stage El escenario principal de la ventana.
     * @throws IOException Si el archivo FXML no se encuentra o no puede ser cargado.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargamos el archivo de diseño Login.fxml desde los recursos
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/es/damdi/francisco/Login.fxml"));

        // Creamos la escena principal con las dimensiones especificadas
        Scene scene = new Scene(fxmlLoader.load(), 400, 350);

        stage.setTitle("Chat Corporativo - Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método main estándar que lanza el ciclo de vida de la aplicación JavaFX.
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch();
    }
}
