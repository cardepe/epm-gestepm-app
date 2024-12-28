package com.epm.gestepm.modelapi.role.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleTableDTO {

	@JsonProperty("sr_id")
	private Long id;

	@JsonProperty("sr_rol")
	private String rol;

	@JsonProperty("sr_price")
	private Double price;

}
