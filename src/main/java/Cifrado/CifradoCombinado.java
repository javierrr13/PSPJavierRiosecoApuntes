package Cifrado;


import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;



public class CifradoCombinado {

	public static void main(String[] args) throws NoSuchAlgorithmException, Exception {
		// TODO Auto-generated method stub
		//Creaccion de par de claves PUBLICA Y PRIVADA ((ALGORITMO RSA))
		KeyPairGenerator pairg = KeyPairGenerator.getInstance("RSA");
		
		pairg.initialize(2048);
		KeyPair pairk = pairg.generateKeyPair();
		PrivateKey privK = pairk.getPrivate();
		PublicKey publicK = pairk.getPublic();
		
		//Creaccion clave secreta para cifrado de publica 
		KeyGenerator kg = KeyGenerator.getInstance("AES");
		kg.init(256);
		SecretKey sc = kg.generateKey();
		
		//Cifrado de clave secreta ((AES)) con la clave publica ((RSA))
		Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		c.init(Cipher.WRAP_MODE, publicK);
		byte [] claveCifrada = c.wrap(sc);
		
		//Cifrado del texto con la clave secreta 
		c= Cipher.getInstance("AES/ECB/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, sc);
		byte[] textoCifrado = c.doFinal("Mensaje ".getBytes());
		System.out.println("Texto cifrado " + new String(textoCifrado));
		//Descifrado de la clave secreata AES con la clave RSA privada
		Cipher c2 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		c2.init(Cipher.UNWRAP_MODE, privK);
		Key claveDescifrada = c2.unwrap(claveCifrada, "AES", Cipher.SECRET_KEY);
		//DescifradoDelTexto con clave secreta
		c2= Cipher.getInstance("AES/ECB/PKCS5Padding");
		c2.init(Cipher.DECRYPT_MODE, claveDescifrada);
		byte[] texto = c2.doFinal(textoCifrado);
		System.out.println("Descifrado : "+ new String(texto));
	}

}
