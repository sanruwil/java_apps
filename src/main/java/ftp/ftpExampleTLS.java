package ftp;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.util.TrustManagerUtils;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class ftpExampleTLS {
    public static void main(String[] args) {
        String server = "192.168.192.219";
        int port = 990; // FTPS default port
        String username = "adminftp";
        String password = "admin";
        String remoteDirectory = "/jetty";
        String localFile = "/Users/wilfredysantamariaruiz/Downloads/EjercciosSQL.sql"; // File to upload
        System.setProperty("jdk.tls.useExtendedMasterSecret", "true");
        System.setProperty("com.sun.net.ssl.checkRevocation", "false");
        System.setProperty( "javax.net.debug", "ssl");
        System.setProperty("jdk.tls.client.enableSessionTicketExtension", "false");




        String keystorePath = "/Users/wilfredysantamariaruiz/Downloads/EMH-MX-develop/ftpkeystore.jks";
        String keystorePassword = "adminftps";
        System.setProperty("javax.net.ssl.trustStore", keystorePath);

        String certifiedPath = "/Users/wilfredysantamariaruiz/Downloads/filezillacert.cer"; // File to upload
        try {

            // Cargar el keystore con el certificado
            /*KeyStore keyStore = KeyStore.getInstance("JKS");
            FileInputStream keystoreInputStream = new FileInputStream(keystorePath);
            keyStore.load(keystoreInputStream, keystorePassword.toCharArray());
            keystoreInputStream.close();

            // Crear un KeyManagerFactory e inicializarlo con el keystore
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keystorePassword.toCharArray());


            SSLContext sslContext = SSLContext.getInstance("TLS");
            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
            sslContext.init(keyManagers, null, null);*/


            // Cargar el certificado .cer en un objeto X509Certificate
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            FileInputStream certificateInputStream = new FileInputStream(certifiedPath);
            X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(certificateInputStream);
            certificateInputStream.close();


            // Crear un KeyStore y cargar el certificado en él
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null); // Crea un keystore vacío
            keyStore.setCertificateEntry("trusted_certificate", certificate);

            // Inicializar un TrustManagerFactory con el keystore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            // Obtener el TrustManager que contiene el certificado público
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();



            // Crear un cliente FTPS y configurarlo con el SSLContext personalizado
            FTPSClient ftpsClient = new FTPSClient("TLSv1.2"); // false para FTPS implícito




            ftpsClient.connect(server, port);
            int reply = ftpsClient.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                boolean success = ftpsClient.login(username, password);
                if (success) {
                    // Set passive mode (optional)
                    ftpsClient.enterLocalPassiveMode();

                    // Change to the remote directory (optional)
                    ftpsClient.changeWorkingDirectory(remoteDirectory);

                    // Enable TLS 1.2
                    ftpsClient.execPBSZ(0);
                    ftpsClient.execPROT("P");
                    ftpsClient.setFileType(FTP.BINARY_FILE_TYPE);
                    ftpsClient.enterLocalPassiveMode();
                    ftpsClient.setUseEPSVwithIPv4(false);
                    ftpsClient.setTrustManager(trustManagers[0]);
                    FTPFile[] files = ftpsClient.listFiles();
                    for (FTPFile file : files) {
                        System.out.println(file.getName());
                    }

                    System.out.println("Archivos en el directorio actual:");
                    // Upload a file
                    //FileInputStream localFileStream = new FileInputStream(localFile);
                    //ftpsClient.storeFile("EjercciosSQL.sql", localFileStream);
                    //localFileStream.close();

                    // Logout and disconnect
                    ftpsClient.logout();
                    ftpsClient.disconnect();
                } else {
                    System.err.println("Login failed.");
                }
            } else {
                System.err.println("FTP server refused connection.");
            }
        } catch (IOException e) {
            e.printStackTrace();

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}

