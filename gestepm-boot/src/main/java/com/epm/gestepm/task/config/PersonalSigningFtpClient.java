package com.epm.gestepm.task.config;

import lombok.Data;
import lombok.extern.java.Log;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Data
@Log
@Component
public class PersonalSigningFtpClient {

    @Value("${gestepm.signing.ftp.server}")
    private String server;

    @Value("${gestepm.signing.ftp.port}")
    private int port;

    @Value("${gestepm.signing.ftp.user}")
    private String user;

    @Value("${gestepm.signing.ftp.password}")
    private String password;

    private FTPClient ftpClient;

    public void connect() throws IOException {
        this.ftpClient = new FTPClient();
        this.ftpClient.connect(server, port);

        int reply = this.ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            this.ftpClient.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }

        this.ftpClient.login(user, password);
        this.ftpClient.enterLocalPassiveMode();
    }

    public void disconnect() throws IOException {
        this.ftpClient.disconnect();
    }

    public List<String> listNames() throws IOException {
        return List.of(this.ftpClient.listNames());
    }

    public String readFileContent(final String filePath) throws IOException {
        final InputStream inputStreamFile = this.ftpClient.retrieveFileStream(filePath);
        this.ftpClient.completePendingCommand();

        if (inputStreamFile == null) {
            log.info(String.format("No se ha encontrado el archivo %s", filePath));
            return null;
        }

        return IOUtils.toString(inputStreamFile, StandardCharsets.UTF_8);
    }

    public void cleanFolder() throws IOException {
        this.listNames().forEach(fileName -> {
            try {
                this.ftpClient.deleteFile(fileName);
            } catch (IOException e) {
                log.info("No se ha podido eliminar el archivo " + fileName);
            }
        });
    }
}