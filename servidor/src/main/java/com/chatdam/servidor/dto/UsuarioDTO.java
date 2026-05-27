package com.chatdam.servidor.dto;

public class UsuarioDTO {
    private String nombreUsuario;
    private String rol;

    public UsuarioDTO() {}
    public UsuarioDTO(String nombreUsuario, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}