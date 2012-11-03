package catchweb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.net.PasswordAuthentication;
//import java.net.Authenticator;
//import java.net.URLConnection;
//import java.util.Properties;



public class URLTest {

	// 一个public方法，返回字符串，错误则返回"error open url"

	public static String getContent(String strUrl) {
		try {
			URL url = new URL(strUrl);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String s = "";
			StringBuffer sb = new StringBuffer("");
			while ((s = br.readLine()) != null) {
				sb.append(s + "\r\n");
			}
			br.close();
			return sb.toString();
		} catch (Exception e) {
			return "error open url:" + strUrl;
		}
	}


//  初始化代理
//	public static void initProxy(String host, int port, final String username,
//
//			final String password) {
//
//		Authenticator.setDefault(new Authenticator() {
//
//			protected PasswordAuthentication getPasswordAuthentication() {
//
//				return new PasswordAuthentication(username,
//
//						new String(password).toCharArray());
//
//			}
//
//		});
//
//		System.setProperty("http.proxyType", "4");
//
//		System.setProperty("http.proxyPort", Integer.toString(port));
//
//		System.setProperty("http.proxyHost", host);
//
//		System.setProperty("http.proxySet", "true");
//
//	}



	public static void main(String[] args) throws IOException {
		 String url = "http://smezj.sme.gov.cn/HELP.html";
//		 String proxy = "http://192.168.22.81";
//		 int port = 80;
//		 String username = "username";
//		 String password = "password";
//		 initProxy(proxy, port, username, password);
		 String curLine = "";
		 String content = "";
		 URL server = new URL(url);
		 HttpURLConnection connection = (HttpURLConnection) server.openConnection();
		 connection.connect();
		 InputStream is = connection.getInputStream();
		 BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		 while ((curLine = reader.readLine()) != null) {
		 content += curLine+ "\r\n";
		 }
		 System.out.println(content);
		 is.close();
		 System.out.println("--------------------------------------------");
		 System.out.println(getContent(url));
	}
}










