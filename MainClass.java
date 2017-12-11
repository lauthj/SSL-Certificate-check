import java.io.PrintStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

public class MainClass {
  public static void main(String args[]) throws Exception {
    System.setProperty("javax.net.ssl.keyStore", "lfkeystore2");
    System.setProperty("javax.net.ssl.keyStorePassword", "wshr.ut");

    SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
    ServerSocket ss = ssf.createServerSocket(5432);
    while (true) {
      Socket s = ss.accept();
      SSLSession session = ((SSLSocket) s).getSession();
      Certificate[] cchain2 = session.getLocalCertificates();
      for (int i = 0; i < cchain2.length; i++) {
        System.out.println(((X509Certificate) cchain2[i]).getSubjectDN());
      }
      System.out.println("Peer host is " + session.getPeerHost());
      System.out.println("Cipher is " + session.getCipherSuite());
      System.out.println("Protocol is " + session.getProtocol());
      System.out.println("ID is " + new BigInteger(session.getId()));
      System.out.println("Session created in " + session.getCreationTime());
      System.out.println("Session accessed in " + session.getLastAccessedTime());

      PrintStream out = new PrintStream(s.getOutputStream());
      out.println("Hi");
      out.close();
      s.close();
    }

  }
}
