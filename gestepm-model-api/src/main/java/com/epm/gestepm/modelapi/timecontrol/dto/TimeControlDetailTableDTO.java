package com.epm.gestepm.modelapi.timecontrol.dto;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class TimeControlDetailTableDTO {

	@SerializedName("tc_type")
	private String type;
	
	@SerializedName("tc_startHour")
	private Date startHour;
	
	@SerializedName("tc_endHour")
	private Date endHour;
	
	public TimeControlDetailTableDTO() {
		
	}

	public TimeControlDetailTableDTO(String type, Date startHour, Date endHour) {
		super();
		this.type = type;
		this.startHour = startHour;
		this.endHour = endHour;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getStartHour() {
		return startHour;
	}

	public void setStartHour(Date startHour) {
		this.startHour = startHour;
	}

	public Date getEndHour() {
		return endHour;
	}

	public void setEndHour(Date endHour) {
		this.endHour = endHour;
	}
}
