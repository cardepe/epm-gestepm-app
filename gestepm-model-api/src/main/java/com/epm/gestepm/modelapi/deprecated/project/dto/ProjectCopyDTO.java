package com.epm.gestepm.modelapi.deprecated.project.dto;

public class ProjectCopyDTO {

	private Long projectId;
	
	public ProjectCopyDTO() {
		
	}

	public ProjectCopyDTO(Long projectId) {
		super();
		this.projectId = projectId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
}
