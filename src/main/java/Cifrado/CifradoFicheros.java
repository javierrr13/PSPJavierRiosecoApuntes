package Cifrado;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CifradoFicheros {

	public static void main(String[] args) throws Exception {
		KeyStore ks = KeyStore.getInstance("pkcs12");
		char [] password = "123456".toCharArray();
		ks.load(new FileInputStream("C:/Users/Jrioseco/Desktop/keystore.p12"), password);
		PrivateKey privKey = (PrivateKey) ks.getKey("javi", "123456".toCharArray());
		PublicKey pubKey = (PublicKey) ks.getCertificate("javi").getPublicKey();
		//cifrar(System.getProperty("user.home") + "\\Desktop\\Parte.rtf", pubKey);
		descifrar(System.getProperty("user.home") + "\\Desktop\\Parte.rtf.cifrado", privKey);
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
	
	static public void descifrar(String path, Key key) {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			System.out.println(cipher.getBlockSize());
			try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
					BufferedOutputStream out = new BufferedOutputStream(
							new FileOutputStream(new File(path + ".rtf")))) {
				byte[] bloque = new byte[512];
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