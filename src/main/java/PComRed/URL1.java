package PComRed;

import java.net.MalformedURLException;
import java.net.URL;

public class URL1 {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		URL url;
		try {
			url = new URL ("http://docs.oracle.com");
			visualizate(url);
		} catch (MalformedURLException e) {
			// TODO: handle exception
			e.printStackTrace();
			}
	}
	private static void visualizate(URL url) {
		System.out.println("Url completa " + url.toString());
		System.out.println("Url protocol " + url.getProtocol());
		System.out.println("Url port " + url.getPort());
		System.out.println("Url host " + url.getHost());
	}

}
