package com.epm.gestepm.modelapi.workshare.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class WorkShareTableDTO {

	@JsonProperty("ws_id")
	private Long id;

	@JsonProperty("pr_name")
	private String projectName;

	@JsonProperty("ws_start_date")
	private Date startDate;

	@JsonProperty("ws_end_date")
	private Date endDate;

}
