package clienteServidorCifrado;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.*;
import java.net.Socket;
import java.security.KeyStore;

public class Servidor {

    private static KeyStore keyStore;

    public static void main(String[] args) throws IOException {
        SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        try (SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(9990)) {
            System.out.println("Servidor esperando en el puerto " + 9990);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                     ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {

                    Object object = inputStream.readObject();
                    if (object instanceof KeyStore) {
                        keyStore = (KeyStore) object; // Almacenar KeyStore
                    } else if ("Solicitar clave".equals(object)) {
                        outputStream.writeObject(keyStore); // Enviar KeyStore al cliente
                        outputStream.flush();
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
