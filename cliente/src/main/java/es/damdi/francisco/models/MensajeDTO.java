package es.damdi.francisco.models;

public class MensajeDTO {

    private String contenido;
    private String autor;
    private String fecha;
    private String hora;

    public MensajeDTO(String contenido, String autor, String fecha, String hora) {
        this.contenido = contenido;
        this.autor = autor;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getContenido() { return contenido; }

    public String getAutor() { return autor; }

    public String getFecha() { return fecha; }

    public String getHora() { return hora; }
}
