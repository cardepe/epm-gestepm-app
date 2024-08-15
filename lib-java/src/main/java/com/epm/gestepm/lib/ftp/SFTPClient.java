package com.epm.gestepm.lib.ftp;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPClient {

	private static final Log log = LogFactory.getLog(SFTPClient.class);
	
	private String server;
	private int port;
	private String user;
	private String password;
	private Session session;

	public SFTPClient(String server, int port, String user, String password) {
		super();
		this.server = server;
		this.port = port;
		this.user = user;
		this.password = password;
	}

	public void open() {
		
		JSch jsch = new JSch();
		session = null;
		
		try {
			
            session = jsch.getSession(user, server, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();
            
        } catch (JSchException e) {
        	log.error(e);
        }
	}
	
	public void uploadFile(InputStream file, String path) {
		
		try {

			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.put(file, path);
			sftpChannel.exit();
			
		} catch (JSchException | SftpException e) {
			log.error(e);
		}
	}

	public void close() {
		session.disconnect();
	}
}
