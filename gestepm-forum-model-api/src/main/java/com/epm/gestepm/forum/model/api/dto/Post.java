package com.epm.gestepm.forum.model.api.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phpbb_posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@Column(name = "topic_id", nullable = false, length = 10)
	private long topicId;
	
	@Column(name = "forum_id", nullable = false, length = 10)
	private long forumId;
	
	@Column(name = "poster_id", nullable = false, length = 10)
	private long posterId;
	
	@Column(name = "icon_id", nullable = false, length = 8)
	private int iconId;
	
	@Column(name = "poster_ip", nullable = false, length = 40)
	private String posterIp;
	
	@Column(name = "post_time", nullable = false, length = 11)
	private long postTime;
	
	@Column(name = "post_reported", nullable = false, length = 1)
	private int postReported;
	
	@Column(name = "enable_bbcode", nullable = false, length = 1)
	private int enableBbcode;
	
	@Column(name = "enable_smilies", nullable = false, length = 1)
	private int enableSmilies;
	
	@Column(name = "enable_magic_url", nullable = false, length = 1)
	private int enableMagicUrl;
	
	@Column(name = "enable_sig", nullable = false, length = 1)
	private int enableSig;
	
	@Column(name = "post_username", nullable = false, length = 255)
	private String postUsername;
	
	@Column(name = "post_subject", nullable = false, length = 255)
	private String postSubject;
	
	@Column(name = "post_text", nullable = false)
	private String postText;
	
	@Column(name = "post_checksum", nullable = false, length = 32)
	private String postChecksum;
	
	@Column(name = "post_attachment", nullable = false, length = 1)
	private int postAttachment;
	
	@Column(name = "bbcode_bitfield", nullable = false, length = 255)
	private String bbcodeBitfield;
	
	@Column(name = "bbcode_uid", nullable = false, length = 8)
	private String bbcodeUid;
	
	@Column(name = "post_postcount", nullable = false, length = 1)
	private int postPostCount;
	
	@Column(name = "post_edit_time", nullable = false, length = 11)
	private int postEditTime;
	
	@Column(name = "post_edit_reason", nullable = false, length = 255)
	private String postEditReason;
	
	@Column(name = "post_edit_user", nullable = false, length = 10)
	private int postEditUser;
	
	@Column(name = "post_edit_count", nullable = false, length = 4)
	private int postEditCount;
	
	@Column(name = "post_edit_locked", nullable = false, length = 1)
	private int postEditLocked;
	
	@Column(name = "post_visibility", nullable = false, length = 3)
	private int postVisibility;
	
	@Column(name = "post_delete_time", nullable = false, length = 11)
	private int postDeleteTime;
	
	@Column(name = "post_delete_reason", nullable = false, length = 255)
	private String postDeleteReason;
	
	@Column(name = "post_delete_user", nullable = false, length = 10)
	private int postDeleteUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getTopicId() {
		return topicId;
	}

	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}

	public long getForumId() {
		return forumId;
	}

	public void setForumId(long forumId) {
		this.forumId = forumId;
	}

	public long getPosterId() {
		return posterId;
	}

	public void setPosterId(long posterId) {
		this.posterId = posterId;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public String getPosterIp() {
		return posterIp;
	}

	public void setPosterIp(String posterIp) {
		this.posterIp = posterIp;
	}

	public long getPostTime() {
		return postTime;
	}

	public void setPostTime(long postTime) {
		this.postTime = postTime;
	}

	public int getPostReported() {
		return postReported;
	}

	public void setPostReported(int postReported) {
		this.postReported = postReported;
	}

	public int getEnableBbcode() {
		return enableBbcode;
	}

	public void setEnableBbcode(int enableBbcode) {
		this.enableBbcode = enableBbcode;
	}

	public int getEnableSmilies() {
		return enableSmilies;
	}

	public void setEnableSmilies(int enableSmilies) {
		this.enableSmilies = enableSmilies;
	}

	public int getEnableMagicUrl() {
		return enableMagicUrl;
	}

	public void setEnableMagicUrl(int enableMagicUrl) {
		this.enableMagicUrl = enableMagicUrl;
	}

	public int getEnableSig() {
		return enableSig;
	}

	public void setEnableSig(int enableSig) {
		this.enableSig = enableSig;
	}

	public String getPostUsername() {
		return postUsername;
	}

	public void setPostUsername(String postUsername) {
		this.postUsername = postUsername;
	}

	public String getPostSubject() {
		return postSubject;
	}

	public void setPostSubject(String postSubject) {
		this.postSubject = postSubject;
	}

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public String getPostChecksum() {
		return postChecksum;
	}

	public void setPostChecksum(String postChecksum) {
		this.postChecksum = postChecksum;
	}

	public int getPostAttachment() {
		return postAttachment;
	}

	public void setPostAttachment(int postAttachment) {
		this.postAttachment = postAttachment;
	}

	public String getBbcodeBitfield() {
		return bbcodeBitfield;
	}

	public void setBbcodeBitfield(String bbcodeBitfield) {
		this.bbcodeBitfield = bbcodeBitfield;
	}

	public String getBbcodeUid() {
		return bbcodeUid;
	}

	public void setBbcodeUid(String bbcodeUid) {
		this.bbcodeUid = bbcodeUid;
	}

	public int getPostPostCount() {
		return postPostCount;
	}

	public void setPostPostCount(int postPostCount) {
		this.postPostCount = postPostCount;
	}

	public int getPostEditTime() {
		return postEditTime;
	}

	public void setPostEditTime(int postEditTime) {
		this.postEditTime = postEditTime;
	}

	public String getPostEditReason() {
		return postEditReason;
	}

	public void setPostEditReason(String postEditReason) {
		this.postEditReason = postEditReason;
	}

	public int getPostEditUser() {
		return postEditUser;
	}

	public void setPostEditUser(int postEditUser) {
		this.postEditUser = postEditUser;
	}

	public int getPostEditCount() {
		return postEditCount;
	}

	public void setPostEditCount(int postEditCount) {
		this.postEditCount = postEditCount;
	}

	public int getPostEditLocked() {
		return postEditLocked;
	}

	public void setPostEditLocked(int postEditLocked) {
		this.postEditLocked = postEditLocked;
	}

	public int getPostVisibility() {
		return postVisibility;
	}

	public void setPostVisibility(int postVisibility) {
		this.postVisibility = postVisibility;
	}

	public int getPostDeleteTime() {
		return postDeleteTime;
	}

	public void setPostDeleteTime(int postDeleteTime) {
		this.postDeleteTime = postDeleteTime;
	}

	public String getPostDeleteReason() {
		return postDeleteReason;
	}

	public void setPostDeleteReason(String postDeleteReason) {
		this.postDeleteReason = postDeleteReason;
	}

	public int getPostDeleteUser() {
		return postDeleteUser;
	}

	public void setPostDeleteUser(int postDeleteUser) {
		this.postDeleteUser = postDeleteUser;
	}
}
