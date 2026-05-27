package es.damdi.francisco.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class SeguridadUtil {

    public static String cifrarContrasena(String passwordPlana) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(passwordPlana.getBytes(StandardCharsets.UTF_8));

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
