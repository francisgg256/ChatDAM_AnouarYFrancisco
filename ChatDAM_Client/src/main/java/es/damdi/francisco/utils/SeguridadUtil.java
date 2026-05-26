package es.damdi.francisco.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Clase de utilidad para operaciones de seguridad.
 * Proporciona métodos para transformar datos sensibles, como el cifrado
 * de contraseñas mediante funciones hash unidireccionales.
 */
public class SeguridadUtil {

    /**
     * Convierte una contraseña en texto plano a un código cifrado mediante el algoritmo SHA-256.
     * Este proceso es unidireccional (irreversible), lo cual es ideal para almacenar
     * contraseñas de forma segura sin conocer la clave original.
     * * @param passwordPlana La contraseña original escrita por el usuario.
     * @return El hash SHA-256 resultante en formato hexadecimal.
     * @throws RuntimeException Si el algoritmo SHA-256 no está disponible en la JVM.
     */
    public static String cifrarContrasena(String passwordPlana) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(passwordPlana.getBytes(StandardCharsets.UTF_8));

            // Convertimos el array de bytes a una cadena hexadecimal legible
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al cifrar la contraseña", e);
        }
    }
}
