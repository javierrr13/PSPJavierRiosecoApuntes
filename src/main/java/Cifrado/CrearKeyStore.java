package Cifrado;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.KeyStore;

public class CrearKeyStore {

    public static void main(String[] args) {
        try {
            // Crear una instancia de KeyStore de tipo JKS
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            
            // Cargar el keystore vacío con la contraseña. null indica que el keystore está vacío.
            char[] password = "miPasswordSeguro".toCharArray();
            keyStore.load(null, password);

            // Ruta al escritorio del usuario
            String desktopPath = System.getProperty("user.home") + "\\Desktop\\miKeyStore.jks";

            // Almacenar el keystore en el escritorio
            try (OutputStream writeStream = new FileOutputStream(desktopPath)) {
                keyStore.store(writeStream, password);
            }

            System.out.println("KeyStore creado exitosamente en " + desktopPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
