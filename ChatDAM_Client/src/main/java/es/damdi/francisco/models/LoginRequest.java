package es.damdi.francisco.models;

/**
 * Objeto de transferencia de datos (DTO) utilizado para empaquetar las credenciales
 * durante el proceso de autenticación en la API REST.
 */
public class LoginRequest {

    private String usuario;
    private String password;

    /**
     * Constructor para inicializar una solicitud de login.
     * @param usuario El nombre de usuario.
     * @param password La contraseña (idealmente enviada ya cifrada).
     */
    public LoginRequest(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    // Getters y Setters

    /** @return El nombre de usuario. */
    public String getUsuario() { return usuario; }

    /** @param usuario Establece el nombre de usuario. */
    public void setUsuario(String usuario) { this.usuario = usuario; }

    /** @return La contraseña (cifrada). */
    public String getPassword() { return password; }

    /** @param password Establece la contraseña. */
    public void setPassword(String password) { this.password = password; }
}
