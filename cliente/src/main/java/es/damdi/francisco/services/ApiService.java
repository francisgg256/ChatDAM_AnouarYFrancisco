package es.damdi.francisco.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import es.damdi.francisco.models.LoginRequest;
import es.damdi.francisco.models.LoginResponse;
import es.damdi.francisco.models.MensajeDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiService {

    private static final String BASE_URL = "http://192.168.25.27:8080/api";

    private HttpClient httpClient;
    private Gson gson;

    public ApiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public String hacerLogin(String usuario, String passwordCifrada) {
        try {
            LoginRequest request = new LoginRequest(usuario, passwordCifrada);
            String jsonBody = gson.toJson(request);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                LoginResponse loginRes = gson.fromJson(response.body(), LoginResponse.class);
                return loginRes.getRol();
            }
            return null;

        } catch (Exception e) {
            System.err.println("Aviso: No se pudo conectar al servidor para el login. " + e.getMessage());
            return null;
        }
    }

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

    public boolean registrarEmpleado(String usuario, String passwordCifrada, String rol) {
        try {
            es.damdi.francisco.models.RegistroRequest request = new es.damdi.francisco.models.RegistroRequest(usuario, passwordCifrada, rol);
            String jsonBody = gson.toJson(request);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/usuarios"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200 || response.statusCode() == 201;

        } catch (Exception e) {
            System.err.println("Error al intentar registrar: " + e.getMessage());
            return false;
        }
    }
}