package es.damdi.francisco.models;

/**
 * DTO para recibir la respuesta del servidor tras un login exitoso.
 */
public class LoginResponse {
    private String nombreUsuario; // Actualizado para coincidir con Anouar
    private String rol;

    public String getNombreUsuario() { return nombreUsuario; }
    public String getRol() { return rol; }
}
