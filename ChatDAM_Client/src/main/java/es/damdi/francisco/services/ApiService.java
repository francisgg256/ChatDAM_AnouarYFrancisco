package es.damdi.francisco.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import es.damdi.francisco.models.LoginRequest;
import es.damdi.francisco.models.MensajeDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de la comunicación cliente-servidor con el backend (Spring Boot).
 * Utiliza {@link HttpClient} para realizar peticiones HTTP (GET/POST) y {@link Gson}
 * para la serialización y deserialización de objetos hacia/desde formato JSON.
 */
public class ApiService {

    /** URL base donde se encuentra expuesta la API REST. */
    private static final String BASE_URL = "http://localhost:8080/api";

    private HttpClient httpClient;
    private Gson gson;

    /**
     * Inicializa el cliente HTTP y el motor de conversión JSON.
     */
    public ApiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    /**
     * Realiza una petición POST para autenticar al usuario contra el servidor.
     * @param usuario El nombre de usuario ingresado.
     * @param passwordCifrada La contraseña cifrada (SHA-256) enviada al servidor.
     * @return true si la autenticación es exitosa (código 200), false en caso contrario.
     */
    public boolean hacerLogin(String usuario, String passwordCifrada) {
        try {
            LoginRequest request = new LoginRequest(usuario, passwordCifrada);
            String jsonBody = gson.toJson(request);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;

        } catch (Exception e) {
            System.err.println("Aviso: No se pudo conectar al servidor para el login. " + e.getMessage());
            return false;
        }
    }

    /**
     * Envía un mensaje al servidor mediante una petición POST para que sea
     * persistido en la base de datos remota.
     * @param mensaje El objeto {@link MensajeDTO} que contiene los datos del mensaje.
     */
    public void guardarMensaje(MensajeDTO mensaje) {
        try {
            String jsonBody = gson.toJson(mensaje);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/mensajes"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            System.err.println("Aviso: No se pudo guardar el mensaje en la BD. " + e.getMessage());
        }
    }

    /**
     * Realiza una petición GET al servidor para recuperar el historial reciente de mensajes.
     * @return Una {@link List} de {@link MensajeDTO} con los mensajes recuperados,
     * o una lista vacía si la conexión falla.
     */
    public List<MensajeDTO> getUltimosMensajes() {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/mensajes"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), new TypeToken<List<MensajeDTO>>(){}.getType());
            }
        } catch (Exception e) {
            System.err.println("Aviso: No se pudo cargar el historial de mensajes. " + e.getMessage());
        }

        return new ArrayList<>();
    }
}