package clienteservidorArchivos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			Socket sc = new Socket("localhost", 4001);
			in = new DataInputStream(sc.getInputStream());
			out = new DataOutputStream(sc.getOutputStream());
			Scanner sn = new Scanner(System.in);

			System.out.println("Especifica el path: ");
			String ruta = sn.nextLine();
			out.writeUTF(ruta);

			Boolean existe = in.readBoolean();
			if (existe) {
				int longitud = in.readInt();
				byte[] contenido = new byte[longitud];
				for (int i = 0; i < longitud; i++) {
					contenido[i] = in.readByte();
				}
				String contenidoFichero = new String(contenido);
				System.out.println(contenidoFichero);
			} else {
				System.out.println("Error en la ruta especificada");
			}

			sn.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
