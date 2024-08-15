package com.epm.gestepm.modelapi.project.dto;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class ProjectTableDTO {

	@SerializedName("pr_id")
	private Long id;
	
	@SerializedName("pr_name")
	private String name;
	
	@SerializedName("us_userId")
	private Long userId;
	
	@SerializedName("us_fullName")
	private String fullName;
	
	@SerializedName("pr_startDate")
	private Date startDate;
	
	@SerializedName("pr_objectiveDate")
	private Date objectiveDate;
	
	public ProjectTableDTO(Long id, String name, Date startDate, Date objectiveDate) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.objectiveDate = objectiveDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getObjectiveDate() {
		return objectiveDate;
	}

	public void setObjectiveDate(Date objectiveDate) {
		this.objectiveDate = objectiveDate;
	}
}
