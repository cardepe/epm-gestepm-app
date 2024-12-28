package com.epm.gestepm.modelapi.timecontrol.dto;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Date;

@Data
public class TimeControlTableDTO {

	@JsonProperty("tc_id")
	private String id;
	
	@JsonProperty("tc_date")
	private OffsetDateTime date;
	
	@JsonProperty("tc_username")
	private String username;
	
	@JsonProperty("tc_reason")
	private String reason;
	
	@JsonProperty("tc_startHour")
	private OffsetDateTime startHour;
	
	@JsonProperty("tc_endHour")
	private OffsetDateTime endHour;
	
	@JsonProperty("tc_breaks")
	private String breaks;
	
	@JsonProperty("tc_journey")
	private double journey;
	
	@JsonProperty("tc_totalHours")
	private String totalHours;
	
	@JsonProperty("tc_difference")
	private String difference;

	public void setCustomId(Long userId, Date date) {
		String strDate = Utiles.getDateFormatted(date);
		this.id = userId + ";" + strDate;
	}
}
