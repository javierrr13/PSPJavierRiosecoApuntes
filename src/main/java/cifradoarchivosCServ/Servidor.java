package cifradoarchivosCServ;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private static final int PORT = 4001;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciado...");

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                // Crea un nuevo hilo para cada conexión de cliente
                new ServidorHilo(clientSocket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
