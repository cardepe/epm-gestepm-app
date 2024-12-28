package com.epm.gestepm.modelapi.expensecorrective.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ExpenseCorrectiveTableDTO {

	@JsonProperty("ec_id")
	private Long id;

	@JsonProperty("pr_projectName")
	private String projectName;

	@JsonProperty("ec_creationDate")
	private Date creationDate;

	@JsonProperty("ec_description")
	private String description;

	@JsonProperty("ec_cost")
	private Double cost;

}
