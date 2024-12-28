package com.epm.gestepm.modelapi.absencetype.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActivityCenterTableDTO {

	@JsonProperty("pv_id")
	private Long id;
	
	@JsonProperty("pv_countryName")
	private String countryName;
	
	@JsonProperty("pv_name")
	private String name;

}
