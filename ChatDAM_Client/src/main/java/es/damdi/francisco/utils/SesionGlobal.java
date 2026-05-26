package es.damdi.francisco.utils;

/**
 * Clase de utilidad que actúa como contenedor global para el estado de la sesión.
 * Permite acceder al nombre del usuario autenticado desde cualquier parte
 * de la aplicación de forma centralizada.
 */
public class SesionGlobal {

    /** * Almacena el nombre del usuario que ha iniciado sesión actualmente.
     * Inicializado por defecto a "Anónimo".
     */
    public static String usuarioActual = "Anónimo";
}
