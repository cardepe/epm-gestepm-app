package com.epm.gestepm.modelapi.deprecated.displacementshare.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class DisplacementShareDTO {

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime displacementDate;

	private Long activityCenter;

	private String manualHours;

	private Boolean roundTrip;

	private String observations;
	
	private String projectId;
	
	private Integer manualDisplacement;

}
