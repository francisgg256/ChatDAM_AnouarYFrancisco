package es.damdi.francisco.models;

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
