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

public class ECBcifrado {

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
        // TODO Auto-generated method stub
        
        //Recuperamos la clave secreta del fichero
        File file = new File(System.getProperty("user.home") + "/miclave.key");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            SecretKey secretKey = (SecretKey) ois.readObject();
            System.out.println(secretKey.getAlgorithm());
            //Creamos un objeto cipher para cifrar con el algoritmo AES
            Cipher ci = Cipher.getInstance("AES/ECB/PKCS5Padding");
            ci.init(Cipher.ENCRYPT_MODE, secretKey);
            
            //Se crean los streams de entrada y de salida para el fichero pdf
            //y el fichero cifrado y se lleva a cabo el cifrado del pdf
            File original = new File(System.getProperty("user.home") + "/original.pdf"); // Corregido
            File cifrado = new File(System.getProperty("user.home") + "/cifrado.pdf"); // Corregido
            try(FileInputStream fis = new FileInputStream(original); 
                FileOutputStream fos = new FileOutputStream(cifrado); 
                CipherOutputStream cos = new CipherOutputStream(fos, ci)) {
                byte[] bloque = new byte[1024]; // Tamaño de bloque ajustado para optimización
                int n;
                while ((n = fis.read(bloque)) != -1) { // Corregido: Debe ser fis.read y != -1
                    cos.write(bloque, 0, n);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
