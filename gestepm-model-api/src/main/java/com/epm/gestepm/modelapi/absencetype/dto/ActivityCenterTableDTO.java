package com.epm.gestepm.modelapi.absencetype.dto;

import com.google.gson.annotations.SerializedName;

public class ActivityCenterTableDTO {

	@SerializedName("pv_id")
	private Long id;
	
	@SerializedName("pv_countryName")
	private String countryName;
	
	@SerializedName("pv_name")
	private String name;
	
	public ActivityCenterTableDTO() {
		
	}

	public ActivityCenterTableDTO(Long id, String countryName, String name) {
		super();
		this.id = id;
		this.countryName = countryName;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
