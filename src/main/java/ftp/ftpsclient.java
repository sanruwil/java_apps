package ftp;
// ftpsclient
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.util.TrustManagerUtils;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;


public class ftpsclient  {

    public static void main(String[] args) {
        String server = "192.168.192.219";
        int port = 990; // Puerto FTPS estándar
        String username = "adminftp";
        String password = "admin";
        String certifiedPassword = "admin";
        String keystorePath = "/Users/wilfredysantamariaruiz/Downloads/EMH-MX-develop/ftpkeystore.jks";
        String keystorePassword = "adminftps";
        String remoteDirectory = "/jetty";
        String localFilePath = "/Users/wilfredysantamariaruiz/Downloads/EjercciosSQL.sql"; // Ruta al archivo local
        int dataPort = 16000;
        System.setProperty("jdk.tls.useExtendedMasterSecret", "true");
        System.setProperty("com.sun.net.ssl.checkRevocation", "false");
        System.setProperty( "javax.net.debug", "ssl");
        System.setProperty("jdk.tls.client.enableSessionTicketExtension", "false");
        try {
            // con manejo de certiifcado

            // Load the keystore
            KeyStore keyStore = KeyStore.getInstance("JKS"); // Puedes usar "PKCS12" si es un archivo PKCS12
            FileInputStream keyStoreFile = new FileInputStream(keystorePath);
            keyStore.load(keyStoreFile, keystorePassword.toCharArray());

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keystorePassword.toCharArray());

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new java.security.SecureRandom());

//
            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

           FTPSClient ftpsClient = new FTPSClient(true,sslContext);
           //ftpsClient.setKeyManager(keyManagers[0]);

            ftpsClient.setEnabledProtocols(new String[] {"TLSv1.2"});
            ftpsClient.connect(server, port);
            if (ftpsClient.login(username, password)) {
                boolean changeDirSuccess = ftpsClient.changeWorkingDirectory(remoteDirectory);
                ftpsClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpsClient.enterLocalPassiveMode();
                String passiveModeCommand = "PASV";
                // Enable TLS 1.2
                ftpsClient.execPBSZ(0);
                ftpsClient.execPROT("P");

                int pasvSuccess = ftpsClient.sendCommand(passiveModeCommand,String.valueOf(dataPort));
                FileInputStream inputStream = new FileInputStream(localFilePath);
                //boolean uploadSuccess = ftpsClient.storeFile("EjercciosSQL.sql", inputStream);
                // operaciones
                // Listar archivos en el directorio actual del servidor
                FTPFile[] files = ftpsClient.listFiles();

                System.out.println("Archivos en el directorio actual:");
                for (FTPFile file : files) {
                    System.out.println(file.getName());
                }

            }

            // Disconnect when done
            ftpsClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
