package servidorCifrado;
import java.io.*;
import java.net.Socket;
import java.util.Base64;

import javax.crypto.Cipher;

import PComRed.InnetAddres;

public class Cliente {

    private String host;
    private int port;

    public Cliente(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void enviarSolicitudCifrado(String alias, String data) {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            // Enviar alias y datos al servidor para cifrar
            output.writeUTF(alias);
            output.writeUTF(data);
            output.flush();

            byte[] encryptedData = (byte[]) input.readObject();

         // Opción 1: Representar los datos cifrados en Base64 para visualización
         String base64EncryptedData = Base64.getEncoder().encodeToString(encryptedData);
         System.out.println("Datos cifrados en Base64: " + base64EncryptedData);
            // Aquí podrías agregar código para descifrar los datos si es necesario

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	Socket sc = new Socket();
        Cliente cliente = new Cliente("localhost", 4001);
        cliente.enviarSolicitudCifrado("miAlias", "Texto para cifrar");
    }
}

