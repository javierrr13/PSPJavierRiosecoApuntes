package Cifrado;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class ECBdescifrado {
	public static void main (String args[]) throws FileNotFoundException, IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		File file = new File(System.getProperty("user.home") + "/miclave.key");
		 try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
		 SecretKey clave = (SecretKey) in.readObject();
		 System.out.println(clave.getAlgorithm());
		 /* Creamos un objeto Cipher para descifrar con el algoritmo AES */
		 Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		 cipher.init(Cipher.DECRYPT_MODE, clave);
		 /*
		 * Se crean los streams de entrada salida para el fichero cifrado y
		 * el fichero pdf y se lleva a cabo el proceso de descifrado
		 */
		 File cifrado = new File(System.getProperty("user.home") + "/cifrado.pdf");
		 File descifrado = new File(System.getProperty("user.home") + "/descifrado.pdf");
	
		 try (FileOutputStream out = new FileOutputStream(descifrado);
		 CipherInputStream inc = new CipherInputStream(
		 new FileInputStream(cifrado), cipher)) {
		 /* Obtenemos el tamaño de bloque del objeto Cipher */
		 int tamBloque = cipher.getBlockSize();
		 /* Creamos un buffer para leer bloque a bloque */
		 byte[] bloque = new byte[tamBloque];
		 /* Leemos bloques descifrados y los guardamos en el fichero pdf */
		 int n;
		 while ((n = inc.read(bloque)) != -1)
		 out.write(bloque, 0, n);
		 }
		 }
	}
}
