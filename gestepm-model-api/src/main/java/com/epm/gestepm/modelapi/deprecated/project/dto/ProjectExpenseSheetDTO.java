package com.epm.gestepm.modelapi.deprecated.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ProjectExpenseSheetDTO {

	@JsonProperty("es_id")
	private Long id;

	@JsonProperty("es_name")
	private String name;

	@JsonProperty("us_fullName")
	private String fullName;

	@JsonProperty("es_status")
	private String status;

	@JsonProperty("es_creationDate")
	private Date creationDate;

	@JsonProperty("es_total")
	private Double total;
	
}
