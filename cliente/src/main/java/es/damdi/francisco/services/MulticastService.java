package es.damdi.francisco.services;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.function.Consumer;

public class MulticastService {

    private static final String GRUPO_IP = "230.0.0.0";
    private static final int PUERTO = 4446;
    private MulticastSocket socket;
    private InetAddress grupo;
    private boolean escuchando = true;

    public MulticastService() {
        try {
            grupo = InetAddress.getByName(GRUPO_IP);
            socket = new MulticastSocket(PUERTO);
            socket.joinGroup(grupo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarMensaje(String mensaje) {
        try {
            byte[] buffer = mensaje.getBytes();
            DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, grupo, PUERTO);
            socket.send(paquete);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void escucharMensajes(Consumer<String> alRecibirMensaje) {
        Thread hiloEscucha = new Thread(() -> {
            try {
                byte[] buffer = new byte[1024];
                while (escuchando) {
                    DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                    socket.receive(paquete);
                    String mensajeRecibido = new String(paquete.getData(), 0, paquete.getLength());

                    alRecibirMensaje.accept(mensajeRecibido);
                }
            } catch (Exception e) {
                if (escuchando) e.printStackTrace();
            }
        });
        hiloEscucha.setDaemon(true);
        hiloEscucha.start();
    }

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
