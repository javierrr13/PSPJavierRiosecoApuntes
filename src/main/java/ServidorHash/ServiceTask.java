package ServidorHash;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ServiceTask implements Runnable {

	Socket socket;
	InetAddress address;
	
	public ServiceTask(Socket socket) throws SocketException {
		socket.setSoTimeout(3000);
		this.socket = socket;
		address = socket.getInetAddress();
	}
	
	@Override
	public void run() {
		try (InputStream in = socket.getInputStream();
		     OutputStream out = socket.getOutputStream()) {
			MessageDigest md = MessageDigest.getInstance("SHA-224");
			byte [] buffer = new byte[1024];
			int len;
			while ((len = in.read(buffer)) != -1) {
				md.update(buffer, 0, len);
			}
			byte [] hash = md.digest();
			System.out.println(address + ": " + Base64.getEncoder().encodeToString(hash));
			out.write(hash);
			out.flush();
		} catch (SocketTimeoutException e) {
			System.err.println("TIMEOUT: " + e.getLocalizedMessage() + " (" + address + ")");
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getLocalizedMessage() + " (" + address + ")");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("ERROR: " + e.getLocalizedMessage() + " (" + address + ")");
		}
	}

}
