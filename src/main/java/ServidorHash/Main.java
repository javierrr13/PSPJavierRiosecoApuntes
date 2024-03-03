package ServidorHash;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) throws IOException {
		ExecutorService executorService = Executors.newFixedThreadPool(100);
		ServerSocket serverSocket = new ServerSocket(9999);
		System.out.println("Hash Server listening on port 9999");
		while (true) 
			executorService.submit(new ServiceTask(serverSocket.accept()));
	}

}