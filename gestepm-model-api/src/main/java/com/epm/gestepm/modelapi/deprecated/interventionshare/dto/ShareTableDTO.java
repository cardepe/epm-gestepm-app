package com.epm.gestepm.modelapi.deprecated.interventionshare.dto;

import com.epm.gestepm.modelapi.inspection.dto.ActionEnumDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShareTableDTO {

	@JsonProperty("st_id")
	private String id;

	@JsonProperty("st_projectId")
	private String projectId;

	@JsonProperty("st_forumTitle")
	private String forumTitle;

	@JsonProperty("st_username")
	private String username;

	@JsonProperty("st_startDate")
	private LocalDateTime startDate;

	@JsonProperty("st_endDate")
	private LocalDateTime endDate;

	@JsonProperty("st_shareType")
	private String shareType;

	@JsonProperty("st_action")
	private ActionEnumDto action;

	public ShareTableDTO() {

	}

	public ShareTableDTO(long id, String projectId, String username, LocalDateTime startDate, LocalDateTime endDate, String shareType) {
		this.id = id + "_" + shareType;
		this.projectId = projectId;
		this.username = username;
		this.startDate = startDate;
		this.endDate = endDate;
		this.shareType = shareType;
	}

	public ShareTableDTO(long id, String projectId, LocalDateTime startDate, LocalDateTime endDate, String shareType) {
		this.id = id + "_" + shareType;
		this.projectId = projectId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.shareType = shareType;
	}

	/*
	 * findShareTableByUserId(Long userId, Integer progress)
	 */
	public ShareTableDTO(long id, String projectId, LocalDateTime startDate, LocalDateTime endDate, String shareType, String forumTitle) {
		this.id = id + "_" + shareType;
		this.projectId = projectId;
		this.forumTitle = forumTitle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.shareType = shareType;
	}

	public ShareTableDTO(long id, String projectId, String username, LocalDateTime startDate, LocalDateTime endDate,
						 String shareType, ActionEnumDto action) {
		this.id = id + "_" + shareType;
		this.projectId = projectId;
		this.username = username;
		this.startDate = startDate;
		this.endDate = endDate;
		this.shareType = shareType;
		this.action = action;
	}

	public ShareTableDTO(long id, String projectId, String username, LocalDateTime startDate, LocalDateTime endDate,
						 String shareType, String action) {
		this.id = id + "_" + shareType;
		this.projectId = projectId;
		this.username = username;
		this.startDate = startDate;
		this.endDate = endDate;
		this.shareType = shareType;
		this.action = ActionEnumDto.valueOf(action);
	}
}
