package com.epm.gestepm.modelapi.displacementshare.dto;

import com.epm.gestepm.modelapi.displacement.dto.DisplacementDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DisplacementShareDTO {

	private LocalDateTime displacementDate;

	private Long activityCenter;

	private String manualHours;

	private Boolean roundTrip;

	private String observations;
	
	private String projectId;
	
	private Integer manualDisplacement;
	
	private DisplacementDTO displacement;

}
