package com.epm.gestepm.modelapi.usersigning.dto;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class UserSigningTableDTO {
	
	@SerializedName("si_id")
	private Long id;

	@SerializedName("si_projectName")
	private String projectName;
	
	@SerializedName("si_startDate")
	private Date startDate;

	@SerializedName("si_endDate")
	private Date endDate;

	public UserSigningTableDTO() {

	}

	public UserSigningTableDTO(Long id, String projectName, Date startDate, Date endDate) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
}
