package servidorCifrado;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;


public class Servidor {

    private int port;
    private KeyStore keyStore;

    public Servidor(int port, String keyStorePath, String keyStorePassword) throws Exception {
        this.port = port;
        this.keyStore = loadKeyStore(keyStorePath, keyStorePassword);
    }

    private KeyStore loadKeyStore(String path, String password) throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        try (InputStream keystoreStream = new FileInputStream(path)) {
            ks.load(keystoreStream, password.toCharArray());
        }
        return ks;
    }

    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Servidor iniciado en el puerto " + port);

        while (true) {
            Socket socket = serverSocket.accept();
            new ServidorHilo(socket, keyStore).start();
        }
    }

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor(4001, "C:\\Users\\jrioseco\\Desktop\\miKeyStore.jks", "miPasswordSeguro");
            servidor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
