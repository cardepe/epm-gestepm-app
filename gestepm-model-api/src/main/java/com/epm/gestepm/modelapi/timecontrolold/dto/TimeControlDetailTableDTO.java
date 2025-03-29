package com.epm.gestepm.modelapi.timecontrolold.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeControlDetailTableDTO {

	@JsonProperty("tc_type")
	private String type;
	
	@JsonProperty("tc_startHour")
	private LocalDateTime startHour;
	
	@JsonProperty("tc_endHour")
	private LocalDateTime endHour;

}
