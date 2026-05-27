package es.damdi.francisco.models;

/**
 * DTO para enviar los datos completos de un nuevo empleado a la API.
 */
public class RegistroRequest {
    private String nombreUsuario;
    private String password;
    private String rol;

    public RegistroRequest(String nombreUsuario, String password, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
    }

    public String getNombreUsuario() { return nombreUsuario; }
    public String getPassword() { return password; }
    public String getRol() { return rol; }
}
