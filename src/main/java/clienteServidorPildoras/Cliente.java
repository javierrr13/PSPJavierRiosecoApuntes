package clienteServidorPildoras;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
	public static void main(String[] args) throws UnknownHostException, IOException { // 'main' debe ser en minúsculas
		Socket sc = new Socket("localhost", 4001);
		DataInputStream in = null;
		DataOutputStream out = null;
		Boolean salir = null;
		try {
			System.out.println("Conectado : ");
			in = new DataInputStream(sc.getInputStream());
			out = new DataOutputStream(sc.getOutputStream());
			salir = false;
			Scanner sn = new Scanner(System.in);

			do {
				String mensaje = in.readUTF();
				System.out.println(mensaje);

				int num = sn.nextInt();
				out.writeInt(num);
				
				mensaje = in.readUTF();
				System.out.println(mensaje);
				
				salir = in.readBoolean();
			} while (!salir);
			sc.close();
			// Aquí deberías agregar la lógica para enviar/recibir mensajes al/desde el
			// servidor.

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Es importante cerrar los recursos en el bloque finally para evitar fugas de
			// recursos.
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
			if (sc != null) {
				sc.close();
			}
		}
	}
}
