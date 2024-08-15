package com.epm.gestepm.modelapi.workshare.dto;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class WorkShareTableDTO {

	@SerializedName("ws_id")
	private Long id;
	
	@SerializedName("pr_name")
	private String projectName;
	
	@SerializedName("ws_start_date")
	private Date startDate;
	
	@SerializedName("ws_end_date")
	private Date endDate;

	public WorkShareTableDTO() {

	}

	public WorkShareTableDTO(Long id, String projectName, Date startDate, Date endDate) {
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
