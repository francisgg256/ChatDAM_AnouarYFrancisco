package es.damdi.francisco;

/**
 * Clase lanzadora de la aplicación.
 * Su propósito es evitar el error de configuración de JavaFX permitiendo iniciar
 * la aplicación desde un punto de entrada que no hereda directamente de
 * {@link javafx.application.Application}.
 */
public class Launcher {

    /**
     * Punto de entrada principal que delega la ejecución al método main
     * de la clase {@link Main}.
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        // Delegamos la ejecución a la clase JavaFX principal
        Main.main(args);
    }
}