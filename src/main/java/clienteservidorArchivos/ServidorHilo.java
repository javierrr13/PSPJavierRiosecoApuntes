package clienteservidorArchivos;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

public class ServidorHilo extends Thread {

	private Socket sc;

	public ServidorHilo(Socket sc) {
		this.sc = sc;
	}
	public void run() {
		DataOutputStream out = null;
		DataInputStream in = null;
		try {
			in = new DataInputStream(sc.getInputStream());
			out = new DataOutputStream(sc.getOutputStream());
			
			String ruta = in.readUTF();
			File f = new File(ruta);
			
			if(f.exists()) {
				out.writeBoolean(true);
				BufferedReader bf = new BufferedReader(new FileReader (ruta));
				String linea = "";
				String contenido = "";
				
				while((linea=bf.readLine())!=null) {
					contenido+=linea;
				}
				bf.close();
				byte[] contenidoFichero = contenido.getBytes();
				out.writeInt(contenidoFichero.length);
				for (int i = 0 ; i<contenidoFichero.length; i ++) {
					out.writeByte(contenidoFichero[i]);
				}
			}else {
				System.out.println("Ruta especificada no existe");
			}
			
		}catch(IOException e ) {
			e.printStackTrace();
		}
	}
}
