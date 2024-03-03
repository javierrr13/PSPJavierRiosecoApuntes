package Cifrado;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
public class PBKDFWithHmacSHA512 {
	public static String cifrar(String password) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		return cifrar(password, 16, 310000, 512);
	}

	public static String cifrar(String password, Integer longitudSal, Integer iteraciones, int longitudClave)throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		byte[] sal = new byte[longitudSal];
		new SecureRandom().nextBytes(sal);
		try (ByteArrayOutputStream as = new ByteArrayOutputStream(); DataOutputStream out = new DataOutputStream(as)) {
			byte[] key = cifrar(password.toCharArray(), sal, iteraciones, longitudClave);
			out.writeInt(iteraciones);
			out.writeInt(sal.length);
			out.writeInt(key.length);
			out.write(sal);
			out.write(key);
			return Base64.getEncoder().encodeToString(as.toByteArray());
		}
	}

	private static byte[] cifrar(char[] password, byte[] salt, int iteraciones, int longitudClave)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
			KeySpec keySpec = new PBEKeySpec(password, salt, iteraciones, longitudClave);
		return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512").generateSecret(keySpec).getEncoded();
	}

	static boolean verificar(String password, String cifrada)throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(cifrada)))) {
			int iteraciones = in.readInt();
			byte[] sal = new byte[in.readInt()];
			byte[] clave = new byte[in.readInt()];
			in.read(sal);
			in.read(clave);
			return Arrays.equals(clave, cifrar(password.toCharArray(), sal, iteraciones, clave.length * 8));
		}

	}
	public static void main (String args[]) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		String contraseña = "12343222";
		String contraseñacifrada = cifrar(contraseña);
		System.out.println("Contraseña sin cifrar " + contraseña);
		escribirEnArchivo("contraseñacifrada.txt", contraseñacifrada);
	}
	private static void escribirEnArchivo(String nombreArchivo, String contenido) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(nombreArchivo)) {
            outputStream.write(contenido.getBytes());
        }
        System.out.println("Contraseña cifrada escrita en el archivo: " + nombreArchivo);
    }
}


