package ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class ftpclient {
    public static void main(String[] args) {
        String server = "192.168.192.219";
        int port = 20;
        String user = "admin";
        String password = "admin";
        FTPClient ftpClient = new FTPClient();
        try {
            // Conectarse al servidor FTP
            ftpClient.connect(server, port);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("No se pudo conectar al servidor FTP.");
                return;
            }
            // Iniciar sesión
            boolean success = ftpClient.login(user, password);
            if (!success) {
                System.out.println("Error al iniciar sesión en el servidor FTP.");
                return;
            }
            // Configurar el modo de transferencia de archivos
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // O puedes usar FTP.ASCII_FILE_TYPE para archivos de texto
            boolean changeDirSuccess = ftpClient.changeWorkingDirectory("/jetty");
            // Realizar operaciones de FTP aquí, como subir o descargar archivos
            FTPFile[] files = ftpClient.listFiles();
            System.out.println("Archivos en el directorio actual:");
            for (FTPFile file : files) {
                System.out.println(file.getName());
            }
            System.out.println("Se conecto exitosamnete servidor FTP.");
            // Cerrar la conexión
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

