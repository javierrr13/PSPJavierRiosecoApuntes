package ServidorHash;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Cliente {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost", 9999);
		
		try (InputStream in = socket.getInputStream(); 
			 DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
			out.writeUTF("Hola Mundo");
			socket.shutdownOutput();
			System.out.println(Arrays.toString(in.readAllBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}