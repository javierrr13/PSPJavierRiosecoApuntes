package clienteServidorCifrado;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.security.KeyStore;

public class Cliente {

    public static void main(String[] args) throws Exception {
        // Generar KeyStore con una clave secreta
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null); // Inicializar KeyStore
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // Por ejemplo, una clave AES de 256 bits
        SecretKey secretKey = keyGen.generateKey();
        KeyStore.SecretKeyEntry secret = new KeyStore.SecretKeyEntry(secretKey);
        KeyStore.ProtectionParameter password = new KeyStore.PasswordProtection("password".toCharArray());
        keyStore.setEntry("secretKeyAlias", secret, password);

        // Conexión al servidor
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try (SSLSocket socket = (SSLSocket) factory.createSocket("localhost", 9990);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            // Enviar KeyStore al servidor
            outputStream.writeObject(keyStore);
            outputStream.flush();

            // Solicitar clave de cifrado al servidor y recibirla
            outputStream.writeObject("Solicitar clave");
            outputStream.flush();
            KeyStore receivedKeyStore = (KeyStore) inputStream.readObject();
            KeyStore.ProtectionParameter receivedPassword = new KeyStore.PasswordProtection("password".toCharArray());
            SecretKey receivedSecretKey = (SecretKey) receivedKeyStore.getKey("secretKeyAlias", "password".toCharArray());

            // Usar la clave recibida para alguna operación, como descifrar un archivo
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

