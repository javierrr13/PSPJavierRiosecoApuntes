package cifradoarchivosCServ;

import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.io.*;

public class Servidor {
    private static final int PORT = 4001;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciado...");

        while (true) {
            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("Cliente conectado.");

                // Procesar solicitud del cliente
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(dis);
                // Recibir archivo del cliente
                String fileName = dis.readUTF();
                long fileSize = dis.readLong();
                String path = dis.readUTF();
                Key key = (Key) ois.readObject();
                
                
                FileOutputStream fos = new FileOutputStream(fileName);
                byte[] buffer = new byte[4096];

                int read = 0;
                int totalRead = 0;
                int remaining = (int) fileSize;
                while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                    totalRead += read;
                    remaining -= read;
                    fos.write(buffer, 0, read);
                }

                fos.close();

                // Aquí se invocaría a CifradoFicheros.cifrar() o CifradoFicheros.descifrar() según la lógica deseada
                cifrar(path, key);
                // Enviar archivo cifrado/descifrado de vuelta al cliente
                // Similar al código de recepción pero en sentido inverso
                File comprobar = new File("C:/Users/Jrioseco/Desktop/Parte.rtf.cifrado");
                if(comprobar.exists()) {
                	System.out.println("Archivo cifrado con exito");
                }else {
                	System.out.println("Error en el cifrado");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static public void cifrar(String path, Key key) {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			System.out.println(cipher.getBlockSize());
			try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
					BufferedOutputStream out = new BufferedOutputStream(
							new FileOutputStream(new File(path + ".cifrado")))) {
				byte[] bloque = new byte[501];
				int n;
				while ((n = in.read(bloque)) != -1)
					out.write(cipher.doFinal(bloque, 0, n));
			} catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			e.printStackTrace();
		}
	}
}

