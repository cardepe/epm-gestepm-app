package com.epm.gestepm.modelapi.userholiday.dto;

import com.epm.gestepm.modelapi.common.utils.JspUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserHolidayDTO {

	@JsonProperty("uh_id")
	private Long id;

	@JsonProperty("uh_date")
	private Date date;
	
	@JsonProperty("uh_status")
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

}
