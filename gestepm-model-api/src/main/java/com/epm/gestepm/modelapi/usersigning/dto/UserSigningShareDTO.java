package com.epm.gestepm.modelapi.usersigning.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UserSigningShareDTO {

	private Long shareId;
	
	private String shareType;

	private OffsetDateTime startDate;

	private OffsetDateTime endDate;

}
