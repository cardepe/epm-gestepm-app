package com.epm.gestepm.modelapi.interventionshare.dto;

import com.epm.gestepm.modelapi.family.dto.Family;
import com.epm.gestepm.modelapi.interventionsharefile.dto.InterventionShareFile;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.subfamily.dto.SubFamily;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "intervention_shares")
public class InterventionShare {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	private Project project;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_SIGNING_ID")
	private UserSigning userSigning;

	@Column(name = "NOTICE_DATE", nullable = false)
	private Timestamp noticeDate;
	
	@Column(name = "END_DATE")
	private Timestamp endDate;

	@Column(name = "INTERVENTION_DESCRIPTION")
	private String description;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "FAMILY")
	private Family family;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "SUB_FAMILY")
	private SubFamily subFamily;

	@Column(name = "TOPIC_ID", nullable = true)
	private Long topicId;
	
	@Column(name = "FORUM_TITLE", length = 256)
	private String forumTitle;
	
	@Column(name = "STATE")
	private int state;
	
	@Column(name = "LAST_DIAGNOSIS")
	private Integer lastDiagnosis;
	
	@OneToMany(mappedBy = "interventionShare")
	private List<InterventionSubShare> interventionSubShares;

	@OneToMany(mappedBy = "interventionShare")
	private List<InterventionShareFile> interventionShareFiles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public UserSigning getUserSigning() {
		return userSigning;
	}

	public void setUserSigning(UserSigning userSigning) {
		this.userSigning = userSigning;
	}
	
	public Timestamp getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(Timestamp noticeDate) {
		this.noticeDate = noticeDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public SubFamily getSubFamily() {
		return subFamily;
	}

	public void setSubFamily(SubFamily subFamily) {
		this.subFamily = subFamily;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public String getForumTitle() {
		return forumTitle;
	}

	public void setForumTitle(String forumTitle) {
		this.forumTitle = forumTitle;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Integer getLastDiagnosis() {
		return lastDiagnosis;
	}

	public void setLastDiagnosis(Integer lastDiagnosis) {
		this.lastDiagnosis = lastDiagnosis;
	}

	public List<InterventionSubShare> getInterventionSubShares() {
		return interventionSubShares;
	}

	public void setInterventionSubShares(List<InterventionSubShare> interventionSubShares) {
		this.interventionSubShares = interventionSubShares;
	}

	public List<InterventionShareFile> getInterventionShareFiles() {
		return interventionShareFiles;
	}

	public void setInterventionShareFiles(List<InterventionShareFile> interventionShareFiles) {
		this.interventionShareFiles = interventionShareFiles;
	}
}
