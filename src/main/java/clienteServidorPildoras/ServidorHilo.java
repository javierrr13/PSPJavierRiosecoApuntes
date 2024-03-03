package clienteServidorPildoras;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
			int randomNum = RandomNum(0, 100);
			int numeroCliente = 0;
			System.out.println("Numero generado " + randomNum);

			do {
				out.writeUTF("Escribe un numero del 1 al 100");
				numeroCliente = in.readInt();
				System.out.println("Numero recibido " + numeroCliente);
				if (numeroCliente == randomNum) {
					out.writeUTF("Has acertado ");
				} else if (numeroCliente > randomNum) {
					out.writeUTF("El numero es mas bajo");
				} else {
					out.writeUTF("El numero es mas alto");
				}
				out.writeBoolean(numeroCliente == randomNum);
			} while (numeroCliente != randomNum);
			sc.close();
			System.out.println("Cliente desconectado");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private int RandomNum(int maximo, int minimo) {

		int RandomNum = (int) (Math.random() * (maximo - minimo + 1) + (minimo));
		return RandomNum;
	}
}
