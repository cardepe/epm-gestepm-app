package com.epm.gestepm.modelapi.userholiday.dto;

import com.epm.gestepm.modelapi.common.utils.JspUtil;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserHolidayDTO {

	@SerializedName("uh_id")
	private Long id;

	@SerializedName("uh_date")
	private Date date;
	
	@SerializedName("uh_status")
	private String status;

	public UserHolidayDTO() {

	}

	public UserHolidayDTO(Long id, Date date, String status) {
		super();

		JspUtil jspUtil = new JspUtil();

		this.id = id;
		this.date = date;
		this.status = jspUtil.parseTagToText(status);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
