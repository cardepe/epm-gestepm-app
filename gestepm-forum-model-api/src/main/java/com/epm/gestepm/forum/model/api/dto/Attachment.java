package com.epm.gestepm.forum.model.api.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phpbb_attachments")
public class Attachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "attach_id", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@Column(name = "post_msg_id", nullable = false, length = 10)
	private long postMsgId;
	
	@Column(name = "topic_id", nullable = false, length = 10)
	private long topicId;
	
	@Column(name = "in_message", nullable = false, length = 1)
	private int inMessage;
	
	@Column(name = "poster_id", nullable = false, length = 10)
	private long posterId;
	
	@Column(name = "is_orphan", nullable = false, length = 1)
	private int isOrphan;
	
	@Column(name = "physical_filename", nullable = false, length = 255)
	private String physicalFilename;
	
	@Column(name = "real_filename", nullable = false, length = 255)
	private String realFilename;
	
	@Column(name = "download_count", nullable = false, length = 8)
	private int downloadCount;
	
	@Column(name = "attach_comment", nullable = false)
	private String attachComment;
	
	@Column(name = "extension", nullable = false, length = 100)
	private String extension;
	
	@Column(name = "mimetype", nullable = false, length = 100)
	private String mimetype;
	
	@Column(name = "filesize", nullable = false, length = 20)
	private long filesize;
	
	@Column(name = "filetime", nullable = false, length = 11)
	private long filetime;
	
	@Column(name = "thumbnail", nullable = false, length = 1)
	private int thumbnail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getPostMsgId() {
		return postMsgId;
	}

	public void setPostMsgId(long postMsgId) {
		this.postMsgId = postMsgId;
	}

	public long getTopicId() {
		return topicId;
	}

	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}

	public int getInMessage() {
		return inMessage;
	}

	public void setInMessage(int inMessage) {
		this.inMessage = inMessage;
	}

	public long getPosterId() {
		return posterId;
	}

	public void setPosterId(long posterId) {
		this.posterId = posterId;
	}

	public int getIsOrphan() {
		return isOrphan;
	}

	public void setIsOrphan(int isOrphan) {
		this.isOrphan = isOrphan;
	}

	public String getPhysicalFilename() {
		return physicalFilename;
	}

	public void setPhysicalFilename(String physicalFilename) {
		this.physicalFilename = physicalFilename;
	}

	public String getRealFilename() {
		return realFilename;
	}

	public void setRealFilename(String realFilename) {
		this.realFilename = realFilename;
	}

	public int getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}

	public String getAttachComment() {
		return attachComment;
	}

	public void setAttachComment(String attachComment) {
		this.attachComment = attachComment;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	public long getFiletime() {
		return filetime;
	}

	public void setFiletime(long filetime) {
		this.filetime = filetime;
	}

	public int getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(int thumbnail) {
		this.thumbnail = thumbnail;
	}
}
