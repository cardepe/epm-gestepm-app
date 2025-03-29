package com.epm.gestepm.modelapi.timecontrolold.dto;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeControlTableDTO {

	@JsonProperty("tc_id")
	private String id;
	
	@JsonProperty("tc_date")
	private LocalDateTime date;
	
	@JsonProperty("tc_username")
	private String username;
	
	@JsonProperty("tc_reason")
	private String reason;
	
	@JsonProperty("tc_startHour")
	private LocalDateTime startHour;
	
	@JsonProperty("tc_endHour")
	private LocalDateTime endHour;
	
	@JsonProperty("tc_breaks")
	private String breaks;
	
	@JsonProperty("tc_journey")
	private double journey;
	
	@JsonProperty("tc_totalHours")
	private String totalHours;
	
	@JsonProperty("tc_difference")
	private String difference;

	public void setCustomId(Long userId, LocalDateTime date) {
		final String strDate = Utiles.getDateFormatted(date, "dd-MM-yyyy");
		this.id = userId + ";" + strDate;
	}
}
