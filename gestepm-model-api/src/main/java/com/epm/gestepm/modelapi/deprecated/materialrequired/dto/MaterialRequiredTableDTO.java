package com.epm.gestepm.modelapi.deprecated.materialrequired.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaterialRequiredTableDTO {
	
	@JsonProperty("mr_id")
	private Long id;
	
	@JsonProperty("mr_nameES")
	private String nameES;
	
	@JsonProperty("mr_nameFR")
	private String nameFR;
	
	@JsonProperty("mr_required")
	private Integer required;
	
	public MaterialRequiredTableDTO() {
		
	}

	public MaterialRequiredTableDTO(Long id, String nameES, String nameFR, Integer required) {
		super();
		this.id = id;
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
		return required;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}
}
