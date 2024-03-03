package Cifrado;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

public class CifradoClaveEquipo {

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
        //Recuperamos la clave secreta del fichero
        File file = new File(System.getProperty("user.home") + "/miclave.key");
        SecretKey secretKey;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            secretKey = (SecretKey) ois.readObject();
        }

        System.out.println(secretKey.getAlgorithm());
        //Creamos un objeto cipher para cifrar con el algoritmo AES/CBC/PKCS5Padding
        Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
        
        // Generar IV
        byte[] ivBytes = new byte[16]; // AES usa bloques de 16 bytes
        new SecureRandom().nextBytes(ivBytes);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        ci.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        
        // Se crean los streams de entrada y de salida para el fichero pdf
        // y el fichero cifrado y se lleva a cabo el cifrado del pdf
        File original = new File(System.getProperty("user.home") + "/original.pdf");
        File cifrado = new File(System.getProperty("user.home") + "/cifrado.pdf");
        
        try (FileInputStream fis = new FileInputStream(original);
             FileOutputStream fos = new FileOutputStream(cifrado);
             CipherOutputStream cos = new CipherOutputStream(fos, ci)) {
            // Escribir el IV al principio del archivo cifrado para usarlo en el descifrado
            fos.write(ivBytes);

            byte[] bloque = new byte[1024]; // Tamaño de bloque ajustado para optimización
            int n;
            while ((n = fis.read(bloque)) != -1) {
                cos.write(bloque, 0, n);
            }
        }
    }
}
