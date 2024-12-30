package com.epm.gestepm.modelapi.usersigning.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class UserSigningDTO {

	private Long id;
	
	private Long project;

	private Long userId;
	
	private Long dispShareId;

	private Integer state;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime startDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime endDate;
	
	private String materials;
	
	private String mrSignature;

	private String geolocation;

}
