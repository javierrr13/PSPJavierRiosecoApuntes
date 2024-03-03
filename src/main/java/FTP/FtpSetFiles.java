package FTP;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FtpSetFiles {

	public static void main(String[] args) {
		// TODO Auto-generated method stub	
	FTPClient cliente = new FTPClient();
	String servidor = "localhost";
	String user = "usuario";
	String pswd = "contraseña";
	BufferedInputStream in = null;
	
	try {
		System.out.println("Conectandose a " + servidor);
		cliente.connect(servidor);
		boolean login = cliente.login(user, pswd);
		if(login) {
			cliente.changeWorkingDirectory("/psp");
			cliente.setFileType(FTP.BINARY_FILE_TYPE);
			in = new BufferedInputStream(new FileInputStream(""
					+ "E:\\Descargas\\mineriadedatos.docx"));
			cliente.storeFile("mineriadedatos.docx", in);
			in.close();
		}
	} catch (IOException e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	finally {
		if(in!=null) {
			try {
				in.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}	
	}

}
