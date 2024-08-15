package com.epm.gestepm.modelapi.displacementshare.dto;

import java.time.LocalDateTime;

import com.epm.gestepm.modelapi.displacement.dto.DisplacementDTO;
import org.springframework.format.annotation.DateTimeFormat;

public class DisplacementShareDTO {

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime displacementDate;

	private Long activityCenter;

	private String manualHours;

	private Boolean roundTrip;

	private String observations;
	
	private String projectId;
	
	private Integer manualDisplacement;
	
	private DisplacementDTO displacement;

	public LocalDateTime getDisplacementDate() {
		return displacementDate;
	}

	public void setDisplacementDate(LocalDateTime displacementDate) {
		this.displacementDate = displacementDate;
	}

	public Long getActivityCenter() {
		return activityCenter;
	}

	public void setActivityCenter(Long activityCenter) {
		this.activityCenter = activityCenter;
	}

	public String getManualHours() {
		return manualHours;
	}

	public void setManualHours(String manualHours) {
		this.manualHours = manualHours;
	}

	public Boolean getRoundTrip() {
		return roundTrip;
	}

	public void setRoundTrip(Boolean roundTrip) {
		this.roundTrip = roundTrip;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Integer getManualDisplacement() {
		return manualDisplacement;
	}

	public void setManualDisplacement(Integer manualDisplacement) {
		this.manualDisplacement = manualDisplacement;
	}

	public DisplacementDTO getDisplacement() {
		return displacement;
	}

	public void setDisplacement(DisplacementDTO displacement) {
		this.displacement = displacement;
	}
}
