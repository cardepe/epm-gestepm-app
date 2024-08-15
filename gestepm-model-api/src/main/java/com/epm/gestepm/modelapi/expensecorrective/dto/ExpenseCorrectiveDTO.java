package com.epm.gestepm.modelapi.expensecorrective.dto;

public class ExpenseCorrectiveDTO {

	private Long project;
	
	private Double cost;
	
	private String description;

	public ExpenseCorrectiveDTO() {
		
	}

	public ExpenseCorrectiveDTO(Long project, Double cost, String description) {
		super();
		this.project = project;
		this.cost = cost;
		this.description = description;
	}

	public Long getProject() {
		return project;
	}

	public void setProject(Long project) {
		this.project = project;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
