package com.epm.gestepm.modelapi.holiday.dto;

import com.google.gson.annotations.SerializedName;

public class HolidayTableDTO {

	@SerializedName("ho_id")
	private Long id;
	
	@SerializedName("ho_name")
	private String name;
	
	@SerializedName("ho_date")
	private String date;
	
	@SerializedName("ho_activityCenter")
	private String activityCenter;
	
	@SerializedName("ho_country")
	private String country;
	
	public HolidayTableDTO() {
		
	}
	
	public HolidayTableDTO(Long id, String name, String date, String activityCenter, String country) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.activityCenter = activityCenter;
		this.country = country;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getActivityCenter() {
		return activityCenter;
	}

	public void setActivityCenter(String activityCenter) {
		this.activityCenter = activityCenter;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
