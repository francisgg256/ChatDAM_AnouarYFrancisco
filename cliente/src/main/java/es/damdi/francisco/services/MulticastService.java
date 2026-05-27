package es.damdi.francisco.services;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.function.Consumer;

/**
 * Servicio encargado de la comunicación en tiempo real mediante Sockets Multicast (UDP).
 * Permite a los clientes enviar mensajes a un grupo específico y escuchar de forma
 * asíncrona los mensajes emitidos por otros clientes conectados al mismo grupo.
 */
public class MulticastService {

    private static final String GRUPO_IP = "230.0.0.0";
    private static final int PUERTO = 4446;
    private MulticastSocket socket;
    private InetAddress grupo;
    private boolean escuchando = true;

    /**
     * Constructor que inicializa el socket y se une al grupo multicast definido.
     */
    public MulticastService() {
        try {
            grupo = InetAddress.getByName(GRUPO_IP);
            socket = new MulticastSocket(PUERTO);
            socket.joinGroup(grupo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Envía un mensaje de texto al grupo multicast definido.
     * @param mensaje El contenido del mensaje a enviar.
     */
    public void enviarMensaje(String mensaje) {
        try {
            byte[] buffer = mensaje.getBytes();
            DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, grupo, PUERTO);
            socket.send(paquete);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicia un hilo de ejecución independiente para escuchar mensajes entrantes
     * del grupo multicast.
     * @param alRecibirMensaje Un {@link Consumer} que define qué hacer con el mensaje
     * cuando se recibe (por ejemplo, actualizar la UI).
     */
    public void escucharMensajes(Consumer<String> alRecibirMensaje) {
        Thread hiloEscucha = new Thread(() -> {
            try {
                byte[] buffer = new byte[1024];
                while (escuchando) {
                    DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                    socket.receive(paquete);
                    String mensajeRecibido = new String(paquete.getData(), 0, paquete.getLength());

                    // Cuando llega un mensaje, avisamos a la interfaz a través del consumidor
                    alRecibirMensaje.accept(mensajeRecibido);
                }
            } catch (Exception e) {
                if (escuchando) e.printStackTrace();
            }
        });
        hiloEscucha.setDaemon(true); // El hilo muere automáticamente al cerrar la aplicación
        hiloEscucha.start();
    }

    /**
     * Cierra la conexión de forma segura, abandonando el grupo y liberando el socket.
     */
    public void cerrarConexion() {
        try {
            escuchando = false;
            socket.leaveGroup(grupo);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
