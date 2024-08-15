package com.epm.gestepm.modelapi.materialrequired.dto;

public class MaterialShareDTO {

	private String description;
	
	private Integer units;
	
	private String reference;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
