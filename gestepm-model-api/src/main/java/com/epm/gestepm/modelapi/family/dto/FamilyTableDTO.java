package com.epm.gestepm.modelapi.family.dto;

import com.google.gson.annotations.SerializedName;

public class FamilyTableDTO {
	
	@SerializedName("fa_id")
	private Long id;
	
	@SerializedName("fa_nameES")
	private String nameES;
	
	@SerializedName("fa_nameFR")
	private String nameFR;
	
	@SerializedName("fa_brand")
	private String brand;
	
	@SerializedName("fa_model")
	private String model;
	
	@SerializedName("fa_enrollment")
	private String enrollment;
	
	public FamilyTableDTO() {
		
	}

	public FamilyTableDTO(Long id, String nameES, String nameFR) {
		super();
		this.id = id;
		this.nameES = nameES;
		this.nameFR = nameFR;
	}
	
	public FamilyTableDTO(Long id, String nameES, String nameFR, String brand, String model, String enrollment) {
		super();
		this.id = id;
		this.nameES = nameES;
		this.nameFR = nameFR;
		this.brand = brand;
		this.model = model;
		this.enrollment = enrollment;
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
