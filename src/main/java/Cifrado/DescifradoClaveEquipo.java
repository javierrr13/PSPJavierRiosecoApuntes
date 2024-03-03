package Cifrado;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class DescifradoClaveEquipo {

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
        File file = new File(System.getProperty("user.home") + "/miclave.key");
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            SecretKey clave = (SecretKey) in.readObject();
            System.out.println(clave.getAlgorithm());
            
            // Leer el archivo cifrado para obtener el IV
            File cifrado = new File(System.getProperty("user.home") + "/cifrado.pdf");
            FileInputStream fis = new FileInputStream(cifrado);
            
            // El tamaño del IV para AES es de 16 bytes
            byte[] iv = new byte[16];
            fis.read(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            
            // Inicializar el objeto Cipher para descifrado
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, clave, ivSpec);
            
            File descifrado = new File(System.getProperty("user.home") + "/descifrado.pdf");
            try (FileOutputStream out = new FileOutputStream(descifrado);
                 CipherInputStream inc = new CipherInputStream(fis, cipher)) {
                byte[] bloque = new byte[1024]; // Usar un buffer más grande para mayor eficiencia
                int n;
                while ((n = inc.read(bloque)) != -1) {
                    out.write(bloque, 0, n);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
