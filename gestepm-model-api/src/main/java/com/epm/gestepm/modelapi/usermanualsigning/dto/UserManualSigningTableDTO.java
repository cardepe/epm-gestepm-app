package com.epm.gestepm.modelapi.usermanualsigning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class UserManualSigningTableDTO {

	@JsonProperty("ums_id")
	private Long id;

	@JsonProperty("ums_manualTypeId")
	private String manualTypeId;

	@JsonProperty("ums_startDate")
	private OffsetDateTime startDate;

	@JsonProperty("ums_endDate")
	private OffsetDateTime endDate;

	@JsonProperty("ums_hasFile")
	private Boolean hasFile;

}
