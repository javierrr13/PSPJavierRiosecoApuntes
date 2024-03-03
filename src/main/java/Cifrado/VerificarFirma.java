package Cifrado;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;


public class VerificarFirma {
	static void firma(KeyStore key, String path, char[] password) throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException, InvalidKeyException, SignatureException {
		try {
			key = KeyStore.getInstance("pkcs12");
			key.load(new FileInputStream("C:/Users/Jrioseco/Desktop/Keystore.p12"), password);
			PrivateKey pk = (PrivateKey) key.getKey("javi", password);
			
			Signature signature = Signature.getInstance("SHA512WithRSA");
			signature.initSign(pk);
			
			 try (BufferedInputStream in = new BufferedInputStream(new FileInputStream("C:/Users/Jrioseco/Desktop/Parte2Empresa.rtf"))) {
		            byte[] buffer = new byte[1024];
		            int len;
		            while ((len = in.read(buffer)) != -1) {
		                signature.update(buffer, 0, len);
		            }
		        }
			 byte[] realSig = signature.sign();
		        try (FileOutputStream sigfos = new FileOutputStream(System.getProperty("user.home") + "/Desktop/Parte2Empresa.rtf.sign")) {
		            sigfos.write(realSig);
		        }

		        System.out.println("Firma completada y guardada.");
			
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void main(String[] args) throws Exception {
		KeyStore ks = KeyStore.getInstance("pkcs12");
		char [] password = "123456".toCharArray();
		ks.load(new FileInputStream("C:\\Users\\Jrioseco\\Desktop\\keystore.p12"), password);
		PublicKey pubKey = (PublicKey) ks.getCertificate("javi").getPublicKey();
		
		try (
				BufferedInputStream inF = new BufferedInputStream(
						new FileInputStream("C:/Users/Jrioseco/Desktop/Parte2Empresa.rtf"));
				BufferedInputStream inS = new BufferedInputStream(
						new FileInputStream(System.getProperty("user.home") + "/Desktop/OpenSSL.pdf.sign"))
				) {
			Signature sign = Signature.getInstance("SHA512withRSA");
			sign.initVerify(pubKey);
			byte [] buffer = new byte[1024];
			int n;
			while ((n = inF.read(buffer)) > 0)
				sign.update(buffer, 0, n);
			System.out.println(sign.verify(inS.readAllBytes()));
		   
		}
		//firma(ks, "C:/Users/Jrioseco/Desktop/Parte2Empresa.rtf", password);
	}

}
