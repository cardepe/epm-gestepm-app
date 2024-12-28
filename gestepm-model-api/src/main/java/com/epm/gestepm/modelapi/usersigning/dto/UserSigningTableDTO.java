package com.epm.gestepm.modelapi.usersigning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class UserSigningTableDTO {
	
	@JsonProperty("si_id")
	private Long id;

	@JsonProperty("si_projectName")
	private String projectName;
	
	@JsonProperty("si_startDate")
	private OffsetDateTime startDate;

	@JsonProperty("si_endDate")
	private OffsetDateTime endDate;

}
