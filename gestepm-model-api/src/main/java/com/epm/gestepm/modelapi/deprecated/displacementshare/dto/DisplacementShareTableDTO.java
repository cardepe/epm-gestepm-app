package com.epm.gestepm.modelapi.deprecated.displacementshare.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class DisplacementShareTableDTO {

	@JsonProperty("ds_id")
	private Long id;
	
	@JsonProperty("pr_name")
	private String projectName;
	
	@JsonProperty("di_title")
	private String displacementTitle;
	
	@JsonProperty("ds_displacementDate")
	private LocalDateTime displacementDate;
	
	@JsonProperty("ds_round_trip")
	private Boolean roundTrip;

}
