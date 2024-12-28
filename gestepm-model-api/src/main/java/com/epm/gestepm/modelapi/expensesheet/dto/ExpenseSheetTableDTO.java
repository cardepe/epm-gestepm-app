package com.epm.gestepm.modelapi.expensesheet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ExpenseSheetTableDTO {

	@JsonProperty("es_id")
	private Long id;

	@JsonProperty("es_name")
	private String name;

	@JsonProperty("pr_projectName")
	private String projectName;

	@JsonProperty("es_status")
	private String status;

	@JsonProperty("es_creationDate")
	private Date creationDate;

	@JsonProperty("es_total")
	private Double total;

}
