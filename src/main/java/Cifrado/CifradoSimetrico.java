package Cifrado;



import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class CifradoSimetrico {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//CREACCION DE LA CLAVE SECRETA
		KeyGenerator kg = KeyGenerator.getInstance("AES");
		kg.init(256);
		SecretKey sc = kg.generateKey();
		//OBJETO CIPHER
		Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
		//INICIALIZACION DEL CIPHER PARA CIFRADO
		c.init(Cipher.ENCRYPT_MODE, sc);
		byte [] textoCifrado = c.doFinal("Secreto ".getBytes());
		System.out.println("Texto Cifrado : "+ new String(textoCifrado));
		//INICIALIZACION DEL CIPHER PARA DESCIFRADO
		c.init(Cipher.DECRYPT_MODE, sc);
		System.out.println("Texto descifrado " + new String(c.doFinal(textoCifrado)));
	}	

}
