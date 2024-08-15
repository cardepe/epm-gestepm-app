package com.epm.gestepm.modelapi.holiday.dto;

public class HolidayDTO {

	private String name;
	private int day;
	private int month;
	private Long country;
	private Long activityCenter;
	
	public HolidayDTO() {
		
	}

	public HolidayDTO(String name, int day, int month, Long country, Long activityCenter) {
		super();
		this.name = name;
		this.day = day;
		this.month = month;
		this.country = country;
		this.activityCenter = activityCenter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	public Long getActivityCenter() {
		return activityCenter;
	}

	public void setActivityCenter(Long activityCenter) {
		this.activityCenter = activityCenter;
	}
}
