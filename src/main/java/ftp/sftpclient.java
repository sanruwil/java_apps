package ftp;

import com.jcraft.jsch.*;

import java.util.Vector;

public class sftpclient {
    public static void main(String[] args) {
        String host = "192.168.192.219";
        int port = 22;
        String user = "admin";
        String password = "admin";
        String remoteDirectory = "/acknak/input";

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);

            // Evitar comprobación de clave de host
            session.setConfig("StrictHostKeyChecking", "no");

            session.connect();

            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // Cambiar al directorio remoto
            channelSftp.cd(remoteDirectory);

            // Listar archivos en el directorio remoto
            Vector<ChannelSftp.LsEntry> fileList = channelSftp.ls(".");
            for (ChannelSftp.LsEntry entry : fileList) {
                System.out.println(entry.getFilename());
            }

            // Cerrar la conexión
            channelSftp.disconnect();
            session.disconnect();
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }
    }
}

