package servidorCifrado;
import java.io.*;
import java.net.Socket;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;

import javax.crypto.Cipher;

public class ServidorHilo extends Thread {

    private Socket socket;
    private KeyStore keyStore;

    public ServidorHilo(Socket socket, KeyStore keyStore) {
        this.socket = socket;
        this.keyStore = keyStore;
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {

            // Ejemplo de cómo podrías manejar las solicitudes de cifrado
            String alias = input.readUTF();
            String data = input.readUTF();

            Key key = keyStore.getKey(alias, "miPasswordSeguro".toCharArray());
            if (key instanceof PrivateKey) {
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, key);

                byte[] encryptedData = cipher.doFinal(data.getBytes());
                output.writeObject(encryptedData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
