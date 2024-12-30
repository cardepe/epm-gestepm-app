package com.epm.gestepm.modelapi.usersigning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserSigningTableDTO {
	
	@JsonProperty("si_id")
	private Long id;

	@JsonProperty("si_projectName")
	private String projectName;
	
	@JsonProperty("si_startDate")
	private LocalDateTime startDate;

	@JsonProperty("si_endDate")
	private LocalDateTime endDate;

}
