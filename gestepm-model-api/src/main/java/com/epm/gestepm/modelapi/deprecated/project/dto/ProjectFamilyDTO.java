package com.epm.gestepm.modelapi.deprecated.project.dto;

public class ProjectFamilyDTO {

	private Long familyId;
	
	private String nameES;
	
	private String nameFR;
	
	private String brand;
	
	private String model;
	
	private String enrollment;

	public ProjectFamilyDTO(Long familyId, String nameES, String nameFR, String brand, String model, String enrollment) {
		super();
		this.familyId = familyId;
		this.nameES = nameES;
		this.nameFR = nameFR;
		this.brand = brand;
		this.model = model;
		this.enrollment = enrollment;
	}

	public Long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(String enrollment) {
		this.enrollment = enrollment;
	}
}
