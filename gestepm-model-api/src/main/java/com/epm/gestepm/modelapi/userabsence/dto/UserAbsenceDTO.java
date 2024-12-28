package com.epm.gestepm.modelapi.userabsence.dto;

import com.epm.gestepm.modelapi.common.utils.JspUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class UserAbsenceDTO {
	@JsonProperty("ua_id")
	private Long id;

	@JsonProperty("at_absenceType")
	private String absenceType;

	@JsonProperty("ua_date")
	private Date date;

	public UserAbsenceDTO() {

	}

	public UserAbsenceDTO(Long id, String absenceType, Date date) {
		super();

		JspUtil jspUtil = new JspUtil();

		this.id = id;
		this.absenceType = jspUtil.parseTagToText(absenceType);
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbsenceType() {
		return absenceType;
	}

	public void setAbsenceType(String absenceType) {
		this.absenceType = absenceType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
