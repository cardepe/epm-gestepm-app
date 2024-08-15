package com.epm.gestepm.modelapi.materialrequired.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MaterialRequiredDTO {

	private Long id;
	
	private Long projectId;

	private String nameES;
	
	private String nameFR;
	
	private Integer required;

	public MaterialRequiredDTO() {

	}

	public MaterialRequiredDTO(Long id, String nameES, String nameFR, Integer required) {
		super();
		this.id = id;
		this.nameES = nameES;
		this.nameFR = nameFR;
		this.required = required;
	}
	
	public MaterialRequiredDTO(Long id, Long projectId, String nameES, String nameFR, Integer required) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.nameES = nameES;
		this.nameFR = nameFR;
		this.required = required;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getNameES() {
		return nameES;
	}

	public void setNameES(String nameES) {
		this.nameES = nameES;
	}

	public String getNameFR() {
		return nameFR;
	}

	public void setNameFR(String nameFR) {
		this.nameFR = nameFR;
	}

	public Integer getRequired() {
		
		if (required == null) {
			return 0;
		}
		
		return required;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}
}
