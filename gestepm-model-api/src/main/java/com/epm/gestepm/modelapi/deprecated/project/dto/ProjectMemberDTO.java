package com.epm.gestepm.modelapi.deprecated.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectMemberDTO {

	@JsonProperty("us_id")
	private Long id;

	@JsonProperty("us_fullName")
	private String fullName;

	@JsonProperty("us_role")
	private String role;

}
