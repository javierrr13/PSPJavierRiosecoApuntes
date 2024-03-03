package servidorclientecifradoV2;
import java.io.*;
import java.net.Socket;

public class Cliente {

    private String host;
    private int port;

    public Cliente(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void recibirArchivo() {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Conectado al servidor.");

            // Enviar un mensaje inicial al servidor si es necesario
            out.writeObject("Hola, servidor!");

            // Recibir el nombre del archivo del servidor
            String fileName = (String) in.readObject();
            System.out.println("Archivo creado en el servidor: " + fileName);

            // Aquí podrías agregar la lógica para descargar el archivo del servidor si es necesario

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente("localhost", 4001);
        cliente.recibirArchivo();
    }
}

