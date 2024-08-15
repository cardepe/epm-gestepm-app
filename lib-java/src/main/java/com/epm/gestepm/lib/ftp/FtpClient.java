package com.epm.gestepm.lib.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpClient {
	 
    private String server;
    private int port;
    private String user;
    private String password;
    private FTPClient ftp;

    public FtpClient(String server, int port, String user, String password) {
		super();
		this.server = server;
		this.port = port;
		this.user = user;
		this.password = password;
	}

	public void open() throws IOException {
        ftp = new FTPClient();
 
        // ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
 
        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }
 
        ftp.login(user, password);
        ftp.enterLocalPassiveMode();
    }
 
    public void close() throws IOException {
        ftp.disconnect();
    }
    
    public Collection<String> listFiles(String path) throws IOException {
		FTPFile[] files = ftp.listFiles(path);
		return Arrays.stream(files).map(FTPFile::getName).collect(Collectors.toList());
    }
    
    public void downloadFile(String source, String destination) throws IOException {
        FileOutputStream out = new FileOutputStream(destination);
        ftp.retrieveFile(source, out);
    }
    
    public void putFileToPath(File file, String path) throws IOException {
        ftp.storeFile(path, new FileInputStream(file));
    }
    
    public String readFileContent(String filePath) throws IOException {
    	InputStream inputStreamFile = ftp.retrieveFileStream(filePath);
    	return IOUtils.toString(inputStreamFile, StandardCharsets.UTF_8.name());
    }
    
    public void cleanFolder(String path) throws IOException {
    	
    	Collection<String> files = listFiles(path);
    	
    	for (String fileName : files) {
    		ftp.deleteFile(fileName);
    	}
    }

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public FTPClient getFtp() {
		return ftp;
	}

	public void setFtp(FTPClient ftp) {
		this.ftp = ftp;
	}
}