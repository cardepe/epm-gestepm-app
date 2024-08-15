package com.epm.gestepm.modelapi.interventionshare.dto;

import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;
import java.util.Date;

public class ShareTableDTO {

	@SerializedName("st_id")
	private String id;
	
	@SerializedName("st_orderId")
	private String orderId;
	
	@SerializedName("st_projectId")
	private String projectId;
	
	@SerializedName("st_forumTitle")
	private String forumTitle;
	
	@SerializedName("st_username")
	private String username;
	
	@SerializedName("st_startDate")
	private Date startDate;
	
	@SerializedName("st_endDate")
	private Date endDate;
	
	@SerializedName("st_shareType")
	private String shareType;
	
	@SerializedName("st_action")
	private Integer action;
	
	public ShareTableDTO() {
		
	}
	
	public ShareTableDTO(long id, String projectId, String username, Date startDate, Date endDate, String shareType) {
		super();
		this.id = id + "_" + shareType;
		this.projectId = projectId;
		this.username = username;
		this.startDate = startDate;
		this.endDate = endDate;
		this.shareType = shareType;
	}
	
	public ShareTableDTO(long id, String projectId, Date startDate, Date endDate, String shareType) {
		super();
		this.id = id + "_" + shareType;
		this.projectId = projectId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.shareType = shareType;
	}
	
	/*
	 * findShareTableByUserId(Long userId, Integer progress)
	 */
	public ShareTableDTO(long id, String projectId, Date startDate, Date endDate, String shareType, String forumTitle) {
		super();
		this.id = id + "_" + shareType;
		this.projectId = projectId;
		this.forumTitle = forumTitle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.shareType = shareType;
	}

	public ShareTableDTO(long id, String orderId, String projectId, String username, Date startDate, Date endDate,
			String shareType) {
		super();
		this.id = id + "_" + shareType;
		this.orderId = orderId;
		this.projectId = projectId;
		this.username = username;
		this.startDate = startDate;
		this.endDate = endDate;
		this.shareType = shareType;
	}
	
	public ShareTableDTO(long id, String orderId, String projectId, String username, Date startDate, Date endDate,
			String shareType, Integer action) {
		super();
		this.id = id + "_" + shareType;
		this.orderId = orderId;
		this.projectId = projectId;
		this.username = username;
		this.startDate = startDate;
		this.endDate = endDate;
		this.shareType = shareType;
		this.action = action;
	}

	public ShareTableDTO(long id, String orderId, String projectId, String username, OffsetDateTime startDate, OffsetDateTime endDate,
						 String shareType, Integer action) {
		super();
		this.id = id + "_" + shareType;
		this.orderId = orderId;
		this.projectId = projectId;
		this.username = username;
		this.startDate = Date.from(startDate.toInstant());
		this.endDate = Date.from(endDate.toInstant());
		this.shareType = shareType;
		this.action = action;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getForumTitle() {
		return forumTitle;
	}

	public void setForumTitle(String forumTitle) {
		this.forumTitle = forumTitle;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}
}
