package com.epm.gestepm.modelapi.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ProjectTableDTO {

	@JsonProperty("pr_id")
	private Long id;

	@JsonProperty("pr_name")
	private String name;

	@JsonProperty("pr_startDate")
	private Date startDate;

	@JsonProperty("pr_objectiveDate")
	private Date objectiveDate;

}
