package com.epm.gestepm.modelapi.usersigning.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UserSigningDTO {

	private Long id;
	
	private Long project;

	private Long userId;
	
	private Long dispShareId;

	private Integer state;

	private OffsetDateTime startDate;

	private OffsetDateTime endDate;
	
	private String materials;
	
	private String mrSignature;

	private String geolocation;

}
