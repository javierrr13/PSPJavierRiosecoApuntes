package SocketsPildoras;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Sockets1 {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		String saludo = "Hola jp";
		Socket misocket = new Socket("localhost", 4001);
		DataOutputStream dos = new DataOutputStream(misocket.getOutputStream());
		dos.writeUTF(saludo);
		dos.close();
		System.out.println("Mensaje enviado");
	}

}

