package clienteservidorArchivos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try {
			ServerSocket serverSocket = new ServerSocket(4001);
			System.out.println("Servidor iniciado");
			
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println(socket.getInetAddress().getHostAddress()+ " conectado al servidor");
				ServidorHilo s = new ServidorHilo(socket);
				s.start();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}