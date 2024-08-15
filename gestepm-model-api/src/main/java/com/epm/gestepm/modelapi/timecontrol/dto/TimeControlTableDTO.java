package com.epm.gestepm.modelapi.timecontrol.dto;

import java.util.Date;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.google.gson.annotations.SerializedName;

public class TimeControlTableDTO {

	@SerializedName("tc_id")
	private String id;
	
	@SerializedName("tc_date")
	private Date date;
	
	@SerializedName("tc_username")
	private String username;
	
	@SerializedName("tc_reason")
	private String reason;
	
	@SerializedName("tc_startHour")
	private Date startHour;
	
	@SerializedName("tc_endHour")
	private Date endHour;
	
	@SerializedName("tc_breaks")
	private String breaks;
	
	@SerializedName("tc_journey")
	private double journey;
	
	@SerializedName("tc_totalHours")
	private String totalHours;
	
	@SerializedName("tc_difference")
	private String difference;
	
	public TimeControlTableDTO() {
		
	}

	public TimeControlTableDTO(String id, Date date, String username, String reason, Date startHour,
			Date endHour, String breaks, double journey, String totalHours, String difference) {
		super();
		this.id = id;
		this.date = date;
		this.username = username;
		this.reason = reason;
		this.startHour = startHour;
		this.endHour = endHour;
		this.breaks = breaks;
		this.journey = journey;
		this.totalHours = totalHours;
		this.difference = difference;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getBreaks() {
		return breaks;
	}

	public void setBreaks(String breaks) {
		this.breaks = breaks;
	}

	public double getJourney() {
		return journey;
	}

	public void setJourney(double journey) {
		this.journey = journey;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}
	
	public void setCustomId(Long userId, Date date) {
		String strDate = Utiles.getDateFormatted(date);
		this.id = userId + ";" + strDate;
	}
}
