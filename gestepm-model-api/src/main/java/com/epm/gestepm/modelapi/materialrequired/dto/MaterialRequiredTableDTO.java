package com.epm.gestepm.modelapi.materialrequired.dto;

import com.google.gson.annotations.SerializedName;

public class MaterialRequiredTableDTO {
	
	@SerializedName("mr_id")
	private Long id;
	
	@SerializedName("mr_nameES")
	private String nameES;
	
	@SerializedName("mr_nameFR")
	private String nameFR;
	
	@SerializedName("mr_required")
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
