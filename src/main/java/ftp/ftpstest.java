package ftp;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPFile;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class ftpstest {
    public static void main(String[] args) {
        String host = "192.168.192.219";
        int port = 999;
        String user = "adminftp";
        String password = "admin";
        String remoteDirectory = "/jetty";
        System.setProperty("jdk.tls.useExtendedMasterSecret", "true");
        System.setProperty("com.sun.net.ssl.checkRevocation", "false");
        System.setProperty( "javax.net.debug", "ssl");
        System.setProperty("jdk.tls.client.enableSessionTicketExtension", "false");

            try {

                TrustManager[] trustManager = new TrustManager[] { new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                } };
                SSLContext sslContext = null;
                try {
                    sslContext = SSLContext.getInstance("SSL");
                    sslContext.init(null, trustManager, new SecureRandom());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                }
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


                FTPClient ftp4jClient = new FTPClient();
                ftp4jClient.setSSLSocketFactory(sslSocketFactory);
                ftp4jClient.setSecurity(FTPClient.SECURITY_FTPES); // or client.setSecurity(FTPClient.SECURITY_FTPES);
                ftp4jClient.setPassive(true);
                //
                ftp4jClient.connect(host, port);
                ftp4jClient.login(user, password);
                System.out.println("despues de ft4");
                // Cambiar al directorio remoto
                // Cambiar al directorio remoto
                ftp4jClient.changeDirectory(remoteDirectory);

                // Listar archivos en el directorio remoto
                // Listar archivos en el directorio remoto
                FTPFile[] files = ftp4jClient.list();
                for (FTPFile file : files) {
                    System.out.println(file.getName());
                }


                // Cerrar la conexión
                ftp4jClient.disconnect(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
