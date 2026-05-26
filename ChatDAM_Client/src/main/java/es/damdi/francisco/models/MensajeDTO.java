package es.damdi.francisco.models;

/**
 * Objeto de transferencia de datos (DTO) que representa un mensaje dentro del chat.
 * Se utiliza tanto para el envío de nuevos mensajes al backend como para la recepción
 * del historial de mensajes desde la base de datos.
 */
public class MensajeDTO {

    private String contenido;
    private String autor;
    private String fecha;
    private String hora;

    /**
     * Constructor para crear una instancia de un mensaje.
     * @param contenido El texto del mensaje enviado por el usuario.
     * @param autor El nombre del usuario que envió el mensaje.
     * @param fecha La fecha en la que se envió (ej. "26-05-2026").
     * @param hora La hora exacta del envío (ej. "17:30").
     */
    public MensajeDTO(String contenido, String autor, String fecha, String hora) {
        this.contenido = contenido;
        this.autor = autor;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Getters

    /** @return El contenido del mensaje. */
    public String getContenido() { return contenido; }

    /** @return El nombre del autor del mensaje. */
    public String getAutor() { return autor; }

    /** @return La fecha de envío. */
    public String getFecha() { return fecha; }

    /** @return La hora de envío. */
    public String getHora() { return hora; }
}
