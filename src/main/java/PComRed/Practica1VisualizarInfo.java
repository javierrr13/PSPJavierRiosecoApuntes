package PComRed;

import java.io.IOException;
import java.net.InetAddress;

public class Practica1VisualizarInfo {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String directoryName = "www.instagram.com";
		Catchinf(InetAddress.getLocalHost(), "metodo extraccion");
		System.out.println("[+] Direcciones obtenidas del directorio dado ->");
		try {
			InetAddress [] direcciones = InetAddress.getAllByName(directoryName);
			for(InetAddress d : direcciones) {
				System.out.println(d.toString());
				System.out.println(d.getHostAddress());
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	private static void Catchinf(InetAddress dir, String metodo) {
		System.out.println("[+] Host name " + dir.getHostName());
		System.out.println("[+] Host address " + dir.getHostAddress());
		System.out.println("[+] Directory " + dir.toString());
		System.out.println("[+] Cannonical name " + dir.getCanonicalHostName());
	}

}
