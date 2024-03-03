package PComRed;

import java.io.IOException;
import java.net.InetAddress;

public class InnetAddres {
	public static void main (String args []) {
		try {
			Prueba(InetAddress.getLocalHost(), "getlocalhost");
			Prueba(InetAddress.getLoopbackAddress(), "getloopbackaddress");
			String host = "www.google.com";
			Prueba(InetAddress.getByName(host), "getbyname");
			ObtenerTodo(host);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private static void ObtenerTodo(String host) {
		System.out.println("direcciones obtenidas con el metodo getAllbyName()");
		try {
			InetAddress [] direcciones = InetAddress.getAllByName(host);
			for(InetAddress d : direcciones) {
				System.out.println(d.toString());
				System.out.println(d.getHostAddress());
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	private static void Prueba(InetAddress dir, String metodo) {
		System.out.println("Direccion obtenida con el metodo " + metodo);
		System.out.println("getHostName "+ dir.getHostName());
		System.out.println("getHostAdress " + dir.getHostAddress());
		System.out.println("tostring " + dir.toString());
		System.out.println("getCanonicalHostName " + dir.getCanonicalHostName());
	}
}
