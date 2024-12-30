package com.epm.gestepm.modelapi.usermanualsigning.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class UserManualSigningDTO {

	private Long id;

	private Long userId;

	private Long manualTypeId;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime startDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime endDate;

	private String description;

	private MultipartFile justification;

	private String geolocation;

}
