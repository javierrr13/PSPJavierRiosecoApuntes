package servidorclientecifradoV2;

import java.io.*;
import java.net.Socket;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class ServidorHilo extends Thread {

    private Socket socket;
    private Key publicKey;
    private Key privateKey;

    public ServidorHilo(Socket socket, Key publicKey, Key privateKey) {
        this.socket = socket;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {

            System.out.println("Cliente conectado.");

            // Recibir texto del cliente
            String text = (String) input.readObject();
            System.out.println("Recibido del cliente: " + text);

            // Cifrado
            SecretKey aesKey = generarClaveAES();
            String encryptedText = cifrarTexto(text, aesKey);
            byte[] encryptedKey = cifrarClaveAES(aesKey);

            // Escribir el texto cifrado y la clave AES cifrada en un archivo
            String fileName = "C:\\Users\\jrioseco\\Desktop\\nombre.cifrado";
            try (ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream(fileName))) {
                fileOut.writeObject(encryptedKey);
                fileOut.writeObject(encryptedText.getBytes());
            }

            // Enviar el nombre del archivo al cliente
            output.writeObject(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SecretKey generarClaveAES() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    private String cifrarTexto(String text, SecretKey aesKey) throws Exception {
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] byteCipherText = aesCipher.doFinal(text.getBytes());
        return new String(byteCipherText);  // Considera usar Base64 para evitar problemas con bytes no imprimibles
    }

    private byte[] cifrarClaveAES(SecretKey aesKey) throws Exception {
        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.WRAP_MODE, publicKey);
        return rsaCipher.wrap(aesKey);
    }
}

