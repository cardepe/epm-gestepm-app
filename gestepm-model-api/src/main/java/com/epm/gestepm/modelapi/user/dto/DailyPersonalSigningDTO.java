package com.epm.gestepm.modelapi.user.dto;

import java.util.Date;

public class DailyPersonalSigningDTO {

	private long totalSignedTime;
	private Date signingDate;
	
	public DailyPersonalSigningDTO() {
		
	}

	public DailyPersonalSigningDTO(long totalSignedTime, Date signingDate) {
		super();
		this.totalSignedTime = totalSignedTime;
		this.signingDate = signingDate;
	}

	public long getTotalSignedTime() {
		return totalSignedTime;
	}

	public void setTotalSignedTime(long totalSignedTime) {
		this.totalSignedTime = totalSignedTime;
	}

	public Date getSigningDate() {
		return signingDate;
	}

	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}
}
