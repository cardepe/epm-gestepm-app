package com.epm.gestepm.modelapi.common.helpers;

import java.util.Date;

public class DatesModel {

	private Date startDate;
	private Date endDate;
	
	public DatesModel() {
		
	}

	public DatesModel(Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
