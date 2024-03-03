package Cifrado;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AlmacenamientoclavesSimetricas {

	public static void main(String[] args) throws IOException, Exception {
		// TODO Auto-generated method stub
		KeyGenerator kg = KeyGenerator.getInstance("AES");
		kg.init(256);
		SecretKey clave = kg.generateKey();
		File f = new File(System.getProperty("user.home")+ "/miclave.key");
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f))){
			out.writeObject(clave);
		}
	}

}
