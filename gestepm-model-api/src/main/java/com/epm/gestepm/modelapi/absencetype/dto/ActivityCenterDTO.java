package com.epm.gestepm.modelapi.absencetype.dto;

public class ActivityCenterDTO {

	private Long country;
	private String name;
	
	public ActivityCenterDTO() {
		
	}

	public ActivityCenterDTO(Long country, String name) {
		super();
		this.country = country;
		this.name = name;
	}

	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
