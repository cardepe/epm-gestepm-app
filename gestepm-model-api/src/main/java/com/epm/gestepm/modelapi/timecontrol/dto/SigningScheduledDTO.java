package com.epm.gestepm.modelapi.timecontrol.dto;

import java.util.Date;

public class SigningScheduledDTO {

	private Long userSigningId;
	private Date date;
	private int value;

	public SigningScheduledDTO(Long userSigningId, Date date, int value) {
		super();
		this.userSigningId = userSigningId;
		this.date = date;
		this.value = value;
	}

	public Long getUserSigningId() {
		return userSigningId;
	}

	public void setUserSigningId(Long userSigningId) {
		this.userSigningId = userSigningId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
