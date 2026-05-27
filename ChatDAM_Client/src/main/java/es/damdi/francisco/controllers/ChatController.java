package es.damdi.francisco.controllers;

import es.damdi.francisco.services.MulticastService;
import es.damdi.francisco.utils.SesionGlobal;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

/**
 * Controlador principal de la interfaz de chat.
 * Gestiona la visualización de mensajes en la lista, la interacción con el
 * MulticastService para el envío y recepción de mensajes en tiempo real,
 * y la navegación entre menús de la aplicación.
 */
public class ChatController {

    @FXML
    private ListView<String> listaMensajes;

    @FXML
    private TextField txtMensaje;

    @FXML
    private MenuItem menuGestion;

    /** Servicio encargado de la comunicación por red UDP Multicast. */
    private MulticastService multicastService;

    /**
     * Inicializa la interfaz del chat.
     * Configura el servicio de multicast y lanza el hilo de escucha para recibir
     * mensajes entrantes de otros clientes.
     */
    @FXML
    public void initialize() {
        multicastService = new MulticastService();

        // Mensaje de bienvenida inicial en la UI
        listaMensajes.getItems().add("Sistema: Hola " + SesionGlobal.usuarioActual + ", conectado al chat.");

        // Ponemos a escuchar el chat en segundo plano para no bloquear la interfaz
        multicastService.escucharMensajes(mensaje -> {
            // Platform.runLater es OBLIGATORIO para actualizar componentes de JavaFX desde un hilo secundario
            Platform.runLater(() -> listaMensajes.getItems().add(mensaje));
        });

        // REQUISITO OPCIONAL: Mostrar u ocultar el menú según el rol
        if (SesionGlobal.rolActual.equals("ADMINISTRADOR")) {
            menuGestion.setVisible(true);
            // Le damos funcionalidad al botón del menú para abrir la ventana
            menuGestion.setOnAction(e -> abrirVentanaGestion());
        } else {
            menuGestion.setVisible(false);
        }
    }

    /**
     * Gestiona el evento de clic en el botón de enviar.
     * Formatea el mensaje con el nombre del usuario actual y lo transmite a la red.
     * * @param event El evento de acción disparado por el botón.
     */
    @FXML
    public void enviarMensaje(ActionEvent event) {
        String texto = txtMensaje.getText();

        if (!texto.trim().isEmpty()) {
            // Formateamos el mensaje: "usuario: texto"
            String mensajeFormateado = SesionGlobal.usuarioActual + ": " + texto;

            // Enviamos el mensaje por la red multicast para que otros clientes lo reciban
            multicastService.enviarMensaje(mensajeFormateado);

            // Limpiamos la caja de texto tras el envío
            txtMensaje.clear();

            // TODO: Integrar aquí la llamada al ApiService (POST) para persistir el mensaje en la base de datos Spring Boot.
        }
    }

    /**
     * Gestiona el cierre de la aplicación.
     * Se encarga de cerrar la conexión multicast de forma segura antes de terminar.
     * * @param event El evento de acción disparado por el menú.
     */
    @FXML
    public void salirApp(ActionEvent event) {
        if (multicastService != null) {
            multicastService.cerrarConexion();
        }
        // Finaliza el ciclo de vida de la aplicación JavaFX
        Platform.exit();
        System.exit(0);
    }

    private void abrirVentanaGestion() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/es/damdi/francisco/GestionEmpleados.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Panel de Administración");
            stage.show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
