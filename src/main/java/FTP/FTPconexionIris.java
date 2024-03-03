package FTP;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

public class FTPconexionIris {

    public static void main(String[] args) {
        String server = "ftp.rediris.es";
        int port = 21;
        String user = "anonymous";
        String pass = "anonymous";
        int DirectoryCount =0;
        int ArchiveCount=0;
        FTPClient ftpClient = new FTPClient();
        try {
            // Conexión al servidor FTP
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Listar directorios del directorio raíz
            String[] directorios = ftpClient.listNames();
            if (directorios != null && directorios.length > 0) {
                for (String directorio : directorios) {
                    System.out.println("Directorio: " + directorio);
                    DirectoryCount++;
                    // Entra en el directorio
                    ftpClient.changeWorkingDirectory(directorio);

                    // Lista los archivos y directorios dentro del directorio
                    String[] archivos = ftpClient.listNames();
                    if (archivos != null && archivos.length > 0) {
                        for (String archivo : archivos) {
                            System.out.println("\tArchivo: " + archivo);
                            ArchiveCount++;
                        }
                    }
                    // Vuelve al directorio raíz
                    ftpClient.changeToParentDirectory();
                }
            }
            System.out.println("[+]Numero de directorios encontrados " + DirectoryCount );
            System.out.println("[+]Numero de archivos encontrados " + ArchiveCount );
            // Desconexión
            ftpClient.logout();
            ftpClient.disconnect();

        } catch (IOException ex) {
            System.out.println("Error al conectar al servidor FTP: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

