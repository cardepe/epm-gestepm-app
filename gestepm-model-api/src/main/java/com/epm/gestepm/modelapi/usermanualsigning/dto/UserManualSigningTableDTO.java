package com.epm.gestepm.modelapi.usermanualsigning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserManualSigningTableDTO {

	@JsonProperty("ums_id")
	private Long id;

	@JsonProperty("ums_manualTypeId")
	private String manualTypeId;

	@JsonProperty("ums_startDate")
	private LocalDateTime startDate;

	@JsonProperty("ums_endDate")
	private LocalDateTime endDate;

	@JsonProperty("ums_description")
	private String description;

	@JsonProperty("ums_hasFile")
	private Boolean hasFile;

}
