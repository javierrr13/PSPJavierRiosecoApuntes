package servidorclientecifradoV2;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;


public class Servidor {

    private int port;
    private Key publicKey;
    private Key privateKey;

    public Servidor(int port) throws Exception {
        this.port = port;
        generarClavesRSA(); // Genera un nuevo par de claves RSA para este ejemplo
    }

    private void generarClavesRSA() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Servidor iniciado en el puerto " + port);

        while (true) {
            try{
            	Socket socket = serverSocket.accept();
                new ServidorHilo(socket, publicKey, privateKey).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor(4001);
            servidor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

