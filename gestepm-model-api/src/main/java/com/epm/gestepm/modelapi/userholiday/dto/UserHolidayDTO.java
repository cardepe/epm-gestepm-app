package com.epm.gestepm.modelapi.userholiday.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserHolidayDTO {

	@JsonProperty("uh_id")
	private Long id;

	@JsonProperty("uh_date")
	private Date date;
	
	@JsonProperty("uh_status")
	private String status;

}
