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
    // Añade esta variable al principio de la clase, debajo de MulticastService
    private es.damdi.francisco.services.ApiService apiService;

    @FXML
    public void initialize() {
        multicastService = new MulticastService();
        apiService = new es.damdi.francisco.services.ApiService(); // Iniciamos la API

        listaMensajes.getItems().add("Sistema: Hola " + SesionGlobal.usuarioActual + ", conectado al chat.");

        // CONEXIÓN REAL: Cargar los 10 últimos mensajes de la Base de Datos
        java.util.List<es.damdi.francisco.models.MensajeDTO> historial = apiService.getUltimosMensajes();
        for (es.damdi.francisco.models.MensajeDTO m : historial) {
            listaMensajes.getItems().add(m.getAutor() + " [" + m.getHora() + "]: " + m.getContenido());
        }

        multicastService.escucharMensajes(mensaje -> {
            Platform.runLater(() -> listaMensajes.getItems().add(mensaje));
        });

        if ("ADMINISTRADOR".equals(SesionGlobal.rolActual)) {
            menuGestion.setVisible(true);
            menuGestion.setOnAction(e -> abrirVentanaGestion());
        } else {
            menuGestion.setVisible(false);
        }
    }

    @FXML
    public void enviarMensaje(ActionEvent event) {
        String texto = txtMensaje.getText();

        if (!texto.trim().isEmpty()) {
            // 1. Enviamos por red Multicast (Tiempo Real)
            String mensajeFormateado = SesionGlobal.usuarioActual + ": " + texto;
            multicastService.enviarMensaje(mensajeFormateado);

            // 2. Preparamos fecha y hora
            String fechaStr = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String horaStr = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));

            // 3. CONEXIÓN REAL: Guardamos en MongoDB
            es.damdi.francisco.models.MensajeDTO nuevoMensaje = new es.damdi.francisco.models.MensajeDTO(texto, SesionGlobal.usuarioActual, fechaStr, horaStr);
            apiService.guardarMensaje(nuevoMensaje);

            txtMensaje.clear();
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
