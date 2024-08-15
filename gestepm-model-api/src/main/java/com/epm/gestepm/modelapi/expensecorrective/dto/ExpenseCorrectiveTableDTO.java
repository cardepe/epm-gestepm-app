package com.epm.gestepm.modelapi.expensecorrective.dto;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class ExpenseCorrectiveTableDTO {

	@SerializedName("ec_id")
	private Long id;

	@SerializedName("pr_projectName")
	private String projectName;

	@SerializedName("ec_creationDate")
	private Date creationDate;
	
	@SerializedName("ec_description")
	private String description;

	@SerializedName("ec_cost")
	private Double cost;

	public ExpenseCorrectiveTableDTO() {

	}

	public ExpenseCorrectiveTableDTO(Long id, String projectName, Date creationDate, String description, Double cost) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.creationDate = creationDate;
		this.description = description;
		this.cost = cost;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

}
