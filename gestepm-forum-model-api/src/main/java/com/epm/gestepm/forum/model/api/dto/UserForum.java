package com.epm.gestepm.forum.model.api.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phpbb_users")
public class UserForum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "user_type", nullable = false, length = 2)
	private int type;

	@Column(name = "group_id", nullable = false, length = 8)
	private int groupId;

	@Column(name = "user_permissions", nullable = false)
	private String permissions;

	@Column(name = "user_perm_from", nullable = false, length = 8)
	private int permFrom;

	@Column(name = "user_ip", nullable = false, length = 40)
	private String ip;

	@Column(name = "user_regdate", nullable = false, length = 11)
	private long regDate;

	@Column(name = "username", nullable = false, length = 255)
	private String username;

	@Column(name = "username_clean", nullable = false, length = 255)
	private String usernameClean;

	@Column(name = "user_password", nullable = false, length = 255)
	private String password;

	@Column(name = "user_passchg", nullable = false, length = 11)
	private long passChg;

	@Column(name = "user_email", nullable = false, length = 100)
	private String email;

	@Column(name = "user_birthday", nullable = false, length = 10)
	private String birthday;
	
	@Column(name = "user_lastvisit", nullable = false, length = 11)
	private int lastVisit;
	
	@Column(name = "user_lastmark", nullable = false, length = 11)
	private long lastMark;
	
	@Column(name = "user_lastpost_time", nullable = false, length = 11)
	private int lastPostTime;
	
	@Column(name = "user_lastpage", nullable = false, length = 200)
	private String lastPage;
	
	@Column(name = "user_last_confirm_key", nullable = false, length = 10)
	private String lastConfirmKey;
	
	@Column(name = "user_last_search", nullable = false, length = 11)
	private int lastSearch;
	
	@Column(name = "user_warnings", nullable = false, length = 4)
	private int warnings;
	
	@Column(name = "user_last_warning", nullable = false, length = 11)
	private int lastWarning;
	
	@Column(name = "user_login_attempts", nullable = false, length = 4)
	private int loginAttempts;
	
	@Column(name = "user_inactive_reason", nullable = false, length = 2)
	private int inactiveReason;
	
	@Column(name = "user_inactive_time", nullable = false, length = 11)
	private int inactiveTime;
	
	@Column(name = "user_posts", nullable = false, length = 8)
	private int posts;
	
	@Column(name = "user_lang", nullable = false, length = 30)
	private String lang;
	
	@Column(name = "user_timezone", nullable = false, length = 100)
	private String timeZone;
	
	@Column(name = "user_dateformat", nullable = false, length = 64)
	private String dateFormat;
	
	@Column(name = "user_style", nullable = false, length = 8)
	private int style;
	
	@Column(name = "user_rank", nullable = false, length = 8)
	private int rank;
	
	@Column(name = "user_colour", nullable = false, length = 6)
	private String colour;
	
	@Column(name = "user_new_privmsg", nullable = false, length = 4)
	private int newPrivMsg;
	
	@Column(name = "user_unread_privmsg", nullable = false, length = 4)
	private int unreadPrivMsg;
	
	@Column(name = "user_last_privmsg", nullable = false, length = 11)
	private int lastPrivMsg;
	
	@Column(name = "user_message_rules", nullable = false, length = 1)
	private int messageRules;
	
	@Column(name = "user_full_folder", nullable = false, length = 11)
	private int fullFolder;
	
	@Column(name = "user_emailtime", nullable = false, length = 11)
	private int emailTime;
	
	@Column(name = "user_topic_show_days", nullable = false, length = 4)
	private int topicShowDays;
	
	@Column(name = "user_topic_sortby_type", nullable = false, length = 1)
	private String topicSortByType;
	
	@Column(name = "user_topic_sortby_dir", nullable = false, length = 1)
	private String topicSortByDir;
	
	@Column(name = "user_post_show_days", nullable = false, length = 4)
	private int postShowDays;
	
	@Column(name = "user_post_sortby_type", nullable = false, length = 1)
	private String postSortByType;
	
	@Column(name = "user_post_sortby_dir", nullable = false, length = 1)
	private String postSortByDir;
	
	@Column(name = "user_notify", nullable = false, length = 1)
	private int notify;
	
	@Column(name = "user_notify_pm", nullable = false, length = 1)
	private int notifyPm;
	
	@Column(name = "user_notify_type", nullable = false, length = 4)
	private int notifyType;
	
	@Column(name = "user_allow_pm", nullable = false, length = 1)
	private int allowPm;
	
	@Column(name = "user_allow_viewonline", nullable = false, length = 1)
	private int allowViewOnline;
	
	@Column(name = "user_allow_viewemail", nullable = false, length = 1)
	private int allowViewEmail;
	
	@Column(name = "user_allow_massemail", nullable = false, length = 1)
	private int allowMassEmail;
	
	@Column(name = "user_options", nullable = false, length = 11)
	private int options;
	
	@Column(name = "user_avatar", nullable = false, length = 255)
	private String avatar;
	
	@Column(name = "user_avatar_type", nullable = false, length = 255)
	private String avatarType;
	
	@Column(name = "user_avatar_width", nullable = false, length = 4)
	private int avatarWidth;
	
	@Column(name = "user_avatar_height", nullable = false, length = 4)
	private int avatarHeight;
	
	@Column(name = "user_sig", nullable = false)
	private String sig;
	
	@Column(name = "user_sig_bbcode_uid", nullable = false, length = 8)
	private String sigBbcodeUid;
	
	@Column(name = "user_sig_bbcode_bitfield", nullable = false, length = 255)
	private String sigBbcodeBitField;
	
	@Column(name = "user_jabber", nullable = false, length = 255)
	private String jabber;
	
	@Column(name = "user_actkey", nullable = false, length = 32)
	private String actKey;
	
	@Column(name = "reset_token", nullable = false, length = 64)
	private String resetToken;
	
	@Column(name = "reset_token_expiration", nullable = false, length = 11)
	private int resetTokenExpiration;
	
	@Column(name = "user_newpasswd", nullable = false, length = 255)
	private String newPasswd;
	
	@Column(name = "user_form_salt", nullable = false, length = 32)
	private String formSalt;
	
	@Column(name = "user_new", nullable = false, length = 1)
	private int userNew;
	
	@Column(name = "user_reminded", nullable = false, length = 4)
	private int reminded;
	
	@Column(name = "user_reminded_time", nullable = false, length = 11)
	private int remindedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public int getPermFrom() {
		return permFrom;
	}

	public void setPermFrom(int permFrom) {
		this.permFrom = permFrom;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getRegDate() {
		return regDate;
	}

	public void setRegDate(long regDate) {
		this.regDate = regDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsernameClean() {
		return usernameClean;
	}

	public void setUsernameClean(String usernameClean) {
		this.usernameClean = usernameClean;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getPassChg() {
		return passChg;
	}

	public void setPassChg(long passChg) {
		this.passChg = passChg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getLastVisit() {
		return lastVisit;
	}

	public void setLastVisit(int lastVisit) {
		this.lastVisit = lastVisit;
	}

	public long getLastMark() {
		return lastMark;
	}

	public void setLastMark(long lastMark) {
		this.lastMark = lastMark;
	}

	public int getLastPostTime() {
		return lastPostTime;
	}

	public void setLastPostTime(int lastPostTime) {
		this.lastPostTime = lastPostTime;
	}

	public String getLastPage() {
		return lastPage;
	}

	public void setLastPage(String lastPage) {
		this.lastPage = lastPage;
	}

	public String getLastConfirmKey() {
		return lastConfirmKey;
	}

	public void setLastConfirmKey(String lastConfirmKey) {
		this.lastConfirmKey = lastConfirmKey;
	}

	public int getLastSearch() {
		return lastSearch;
	}

	public void setLastSearch(int lastSearch) {
		this.lastSearch = lastSearch;
	}

	public int getWarnings() {
		return warnings;
	}

	public void setWarnings(int warnings) {
		this.warnings = warnings;
	}

	public int getLastWarning() {
		return lastWarning;
	}

	public void setLastWarning(int lastWarning) {
		this.lastWarning = lastWarning;
	}

	public int getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public int getInactiveReason() {
		return inactiveReason;
	}

	public void setInactiveReason(int inactiveReason) {
		this.inactiveReason = inactiveReason;
	}

	public int getInactiveTime() {
		return inactiveTime;
	}

	public void setInactiveTime(int inactiveTime) {
		this.inactiveTime = inactiveTime;
	}

	public int getPosts() {
		return posts;
	}

	public void setPosts(int posts) {
		this.posts = posts;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public int getNewPrivMsg() {
		return newPrivMsg;
	}

	public void setNewPrivMsg(int newPrivMsg) {
		this.newPrivMsg = newPrivMsg;
	}

	public int getUnreadPrivMsg() {
		return unreadPrivMsg;
	}

	public void setUnreadPrivMsg(int unreadPrivMsg) {
		this.unreadPrivMsg = unreadPrivMsg;
	}

	public int getLastPrivMsg() {
		return lastPrivMsg;
	}

	public void setLastPrivMsg(int lastPrivMsg) {
		this.lastPrivMsg = lastPrivMsg;
	}

	public int getMessageRules() {
		return messageRules;
	}

	public void setMessageRules(int messageRules) {
		this.messageRules = messageRules;
	}

	public int getFullFolder() {
		return fullFolder;
	}

	public void setFullFolder(int fullFolder) {
		this.fullFolder = fullFolder;
	}

	public int getEmailTime() {
		return emailTime;
	}

	public void setEmailTime(int emailTime) {
		this.emailTime = emailTime;
	}

	public int getTopicShowDays() {
		return topicShowDays;
	}

	public void setTopicShowDays(int topicShowDays) {
		this.topicShowDays = topicShowDays;
	}

	public String getTopicSortByType() {
		return topicSortByType;
	}

	public void setTopicSortByType(String topicSortByType) {
		this.topicSortByType = topicSortByType;
	}

	public String getTopicSortByDir() {
		return topicSortByDir;
	}

	public void setTopicSortByDir(String topicSortByDir) {
		this.topicSortByDir = topicSortByDir;
	}

	public int getPostShowDays() {
		return postShowDays;
	}

	public void setPostShowDays(int postShowDays) {
		this.postShowDays = postShowDays;
	}

	public String getPostSortByType() {
		return postSortByType;
	}

	public void setPostSortByType(String postSortByType) {
		this.postSortByType = postSortByType;
	}

	public String getPostSortByDir() {
		return postSortByDir;
	}

	public void setPostSortByDir(String postSortByDir) {
		this.postSortByDir = postSortByDir;
	}

	public int getNotify() {
		return notify;
	}

	public void setNotify(int notify) {
		this.notify = notify;
	}

	public int getNotifyPm() {
		return notifyPm;
	}

	public void setNotifyPm(int notifyPm) {
		this.notifyPm = notifyPm;
	}

	public int getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
	}

	public int getAllowPm() {
		return allowPm;
	}

	public void setAllowPm(int allowPm) {
		this.allowPm = allowPm;
	}

	public int getAllowViewOnline() {
		return allowViewOnline;
	}

	public void setAllowViewOnline(int allowViewOnline) {
		this.allowViewOnline = allowViewOnline;
	}

	public int getAllowViewEmail() {
		return allowViewEmail;
	}

	public void setAllowViewEmail(int allowViewEmail) {
		this.allowViewEmail = allowViewEmail;
	}

	public int getAllowMassEmail() {
		return allowMassEmail;
	}

	public void setAllowMassEmail(int allowMassEmail) {
		this.allowMassEmail = allowMassEmail;
	}

	public int getOptions() {
		return options;
	}

	public void setOptions(int options) {
		this.options = options;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAvatarType() {
		return avatarType;
	}

	public void setAvatarType(String avatarType) {
		this.avatarType = avatarType;
	}

	public int getAvatarWidth() {
		return avatarWidth;
	}

	public void setAvatarWidth(int avatarWidth) {
		this.avatarWidth = avatarWidth;
	}

	public int getAvatarHeight() {
		return avatarHeight;
	}

	public void setAvatarHeight(int avatarHeight) {
		this.avatarHeight = avatarHeight;
	}

	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public String getSigBbcodeUid() {
		return sigBbcodeUid;
	}

	public void setSigBbcodeUid(String sigBbcodeUid) {
		this.sigBbcodeUid = sigBbcodeUid;
	}

	public String getSigBbcodeBitField() {
		return sigBbcodeBitField;
	}

	public void setSigBbcodeBitField(String sigBbcodeBitField) {
		this.sigBbcodeBitField = sigBbcodeBitField;
	}

	public String getJabber() {
		return jabber;
	}

	public void setJabber(String jabber) {
		this.jabber = jabber;
	}

	public String getActKey() {
		return actKey;
	}

	public void setActKey(String actKey) {
		this.actKey = actKey;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public int getResetTokenExpiration() {
		return resetTokenExpiration;
	}

	public void setResetTokenExpiration(int resetTokenExpiration) {
		this.resetTokenExpiration = resetTokenExpiration;
	}

	public String getNewPasswd() {
		return newPasswd;
	}

	public void setNewPasswd(String newPasswd) {
		this.newPasswd = newPasswd;
	}

	public String getFormSalt() {
		return formSalt;
	}

	public void setFormSalt(String formSalt) {
		this.formSalt = formSalt;
	}

	public int getUserNew() {
		return userNew;
	}

	public void setUserNew(int userNew) {
		this.userNew = userNew;
	}

	public int getReminded() {
		return reminded;
	}

	public void setReminded(int reminded) {
		this.reminded = reminded;
	}

	public int getRemindedTime() {
		return remindedTime;
	}

	public void setRemindedTime(int remindedTime) {
		this.remindedTime = remindedTime;
	}
}
