package com.epm.gestepm.modelapi.interventionsubshare.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class InterventionSubShareTableDTO {

	@JsonProperty("iss_id")
	private Long id;

	@JsonProperty("iss_action")
	private Integer action;

	@JsonProperty("iss_startDate")
	private OffsetDateTime startDate;

	@JsonProperty("iss_endDate")
	private OffsetDateTime endDate;

	@JsonProperty("iss_materialsFileExt")
	private String materialsFileExt;

	@JsonProperty("iss_topicId")
	private Long topicId;

}
