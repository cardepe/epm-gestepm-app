package com.epm.gestepm.modelapi.userold.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTableDTO {

	@JsonProperty("us_id")
	private Long id;

	@JsonProperty("us_name")
	private String name;

	@JsonProperty("us_surnames")
	private String surnames;

	@JsonProperty("ro_roleName")
	private String roleName;

	@JsonProperty("sr_subRoleName")
	private String subRoleName;

}
