package com.epm.gestepm.modelapi.usermanualsigning.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;

@Data
public class UserManualSigningDTO {

	private Long id;

	private Long userId;

	private Long manualTypeId;

	private OffsetDateTime startDate;

	private OffsetDateTime endDate;

	private String description;

	private MultipartFile justification;

	private String geolocation;

}
