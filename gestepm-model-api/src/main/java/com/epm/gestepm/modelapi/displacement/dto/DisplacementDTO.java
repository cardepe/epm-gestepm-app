package com.epm.gestepm.modelapi.displacement.dto;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import lombok.Data;

@Data
public class DisplacementDTO {

	private Long id;
	private String title;
	private Long activityCenter;
	private String displacementType;
	private String totalTime;
	
	public DisplacementDTO() {
		
	}
	
	public DisplacementDTO(Long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public DisplacementDTO(String title, Long activityCenter, String displacementType, String totalTime) {
		super();
		this.title = title;
		this.activityCenter = activityCenter;
		this.displacementType = displacementType;
		this.totalTime = totalTime;
	}

	public DisplacementDTO(Long id, String title, String displacementType, int totalTime) {
		super();
		this.id = id;
		this.title = title;
		this.displacementType = displacementType;
		this.totalTime = Utiles.minutesToHoursAndMinutesString(totalTime);
	}
	
}
