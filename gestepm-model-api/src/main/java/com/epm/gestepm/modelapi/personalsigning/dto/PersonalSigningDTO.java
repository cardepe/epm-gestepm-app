package com.epm.gestepm.modelapi.personalsigning.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class PersonalSigningDTO {

	private Long id;

	private Long userId;

	private OffsetDateTime startDate;

	private OffsetDateTime endDate;

	private String startLocation;

	private String endLocation;

}
