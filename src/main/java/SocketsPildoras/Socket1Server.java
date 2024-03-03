package SocketsPildoras;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Socket1Server implements Runnable{

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Thread mihilo = new Thread();
		mihilo.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
		while(true) {
			ServerSocket serverS = new ServerSocket(4001);
			Socket socket = serverS.accept();
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			String mensaje = dis.readUTF();
			System.out.println("El mensaje recibido es : " + mensaje);
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
