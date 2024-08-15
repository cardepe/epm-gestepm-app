package com.epm.gestepm.modelapi.holiday.dto;

import java.util.Date;
import java.util.List;

public class YearCalendarDTO {

	private Long id;
	private String color;
	private Date date;
	private List<String> usernames;

	public YearCalendarDTO(Long id, String color, Date date, List<String> usernames) {
		this.id = id;
		this.color = color;
		this.date = date;
		this.usernames = usernames;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}

	public void addUsername(String username) {
		this.usernames.add(username);
	}
}
