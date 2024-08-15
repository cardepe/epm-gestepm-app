package com.epm.gestepm.modelapi.interventionshare.dto;

import com.google.gson.annotations.SerializedName;

public class InterventionShareTableDTO {

	@SerializedName("is_id")
	private Long id;
	
	@SerializedName("pr_name")
	private String name;
	
	@SerializedName("is_reference")
	private String reference;

	public InterventionShareTableDTO() {

	}

	public InterventionShareTableDTO(Long id, String name, String reference) {
		super();
		this.id = id;
		this.name = name;
		this.reference = reference;
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

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
