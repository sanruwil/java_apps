package ftp;


import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;


import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Socket;

import javax.net.ssl.*;


public class ftpExampleTLS extends FTPSClient {

    @Override
    protected Socket _openDataConnection_(String command, String arg) throws IOException {
        Socket socket = super._openDataConnection_(command, arg);
        this._prepareDataSocket_(socket);
        return socket;
    }

    protected void _prepareDataSocket_(final Socket socket) {
        if (socket instanceof SSLSocket) {
            // Control socket is SSL
            final SSLSession session = ((SSLSocket) _socket_).getSession();
            if (session.isValid()) {
                final SSLSessionContext context = session.getSessionContext();
                try {
                    final Field sessionHostPortCache = context.getClass().getDeclaredField("sessionHostPortCache");
                    sessionHostPortCache.setAccessible(true);
                    final Object cache = sessionHostPortCache.get(context);
                    final Method method = cache.getClass().getDeclaredMethod("put", Object.class, Object.class);
                    method.setAccessible(true);
                    method.invoke(cache, String.format("%s:%s", socket.getInetAddress().getHostName(), String.valueOf(socket.getPort())).toLowerCase(), session);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        String server = "192.168.192.219";
        int port = 999; // FTPS default port
        String username = "adminftp";
        String password = "admin";
        String remoteDirectory = "/jetty";
        String localFile = "/Users/wilfredysantamariaruiz/Downloads/schema-security.json"; // File to upload
        System.setProperty("jdk.tls.useExtendedMasterSecret", "true");
        System.setProperty("com.sun.net.ssl.checkRevocation", "false");
        System.setProperty( "javax.net.debug", "ssl");
        System.setProperty("jdk.tls.client.enableSessionTicketExtension", "false");



        try {
            ///
            ftpExampleTLS ftps = new ftpExampleTLS();



            //FTPSClient ftps = new FTPSClient();
            ftps.connect(server, port);
            ftps.enterLocalPassiveMode();
            ftps.login(username, password);
            ftps.execPBSZ(0);
            ftps.execPROT("P");
            System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");


            FTPFile[] files = ftps.listFiles(remoteDirectory);
            System.out.println("antes del for");
            for (FTPFile file : files) {
                System.out.println("entro");
                System.out.println(file.getName());
            }
            System.out.println("despues del for");

            //File file = new File(localFile);
            //FileInputStream fis = new FileInputStream(file);
            //ftps.storeFile("/jetty/file.txt", fis);
            //fis.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

