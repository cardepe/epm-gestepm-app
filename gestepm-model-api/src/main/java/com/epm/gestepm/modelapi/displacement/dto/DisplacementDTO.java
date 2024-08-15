package com.epm.gestepm.modelapi.displacement.dto;

import com.epm.gestepm.modelapi.common.utils.Utiles;

public class DisplacementDTO {

	private Long id;
	private String title;
	private Long activityCenter;
	private int displacementType;
	private String totalTime;
	
	public DisplacementDTO() {
		
	}
	
	public DisplacementDTO(Long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public DisplacementDTO(String title, Long activityCenter, int displacementType, String totalTime) {
		super();
		this.title = title;
		this.activityCenter = activityCenter;
		this.displacementType = displacementType;
		this.totalTime = totalTime;
	}

	public DisplacementDTO(Long id, String title, int displacementType, int totalTime) {
		super();
		this.id = id;
		this.title = title;
		this.displacementType = displacementType;
		this.totalTime = Utiles.minutesToHoursAndMinutesString(totalTime);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getActivityCenter() {
		return activityCenter;
	}

	public void setActivityCenter(Long activityCenter) {
		this.activityCenter = activityCenter;
	}

	public int getDisplacementType() {
		return displacementType;
	}

	public void setDisplacementType(int displacementType) {
		this.displacementType = displacementType;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	
}
