package cifradoarchivosCServ;

import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.io.*;

public class Cliente {
    private static final String HOST = "localhost";
    private static final int PORT = 4001;

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, UnrecoverableKeyException {
        Socket socket = new Socket(HOST, PORT);
        
        File file = new File("C:/Users/Jrioseco/Desktop/Parte.rtf");
        byte[] buffer = new byte[4096];
        
        KeyStore ks = KeyStore.getInstance("pkcs12");
		char [] password = "123456".toCharArray();
		ks.load(new FileInputStream("C:/Users/Jrioseco/Desktop/keystore.p12"), password);
		PrivateKey privKey = (PrivateKey) ks.getKey("javi", "123456".toCharArray());
		PublicKey pubKey = (PublicKey) ks.getCertificate("javi").getPublicKey();

        // Enviar archivo al servidor
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        ObjectOutputStream obs = new ObjectOutputStream(dos);
        
        
        dos.writeUTF(file.getName());
        dos.writeLong(file.length());
        dos.writeUTF(file.getPath());
        obs.writeObject(pubKey);
       
        
        while (bis.read(buffer) > 0) {
            dos.write(buffer);
        }

        bis.close();
        dos.close();
        socket.close();

        // Recibir archivo cifrado/descifrado de vuelta del servidor y guardar
        // Similar al código de envío pero en sentido inverso
    }
}

