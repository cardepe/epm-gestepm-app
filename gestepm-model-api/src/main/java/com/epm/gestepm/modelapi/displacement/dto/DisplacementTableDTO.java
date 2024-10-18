package com.epm.gestepm.modelapi.displacement.dto;

import com.epm.gestepm.modelapi.common.utils.JspUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.google.gson.annotations.SerializedName;

public class DisplacementTableDTO {

	@SerializedName("di_id")
	private Long id;

	@SerializedName("di_activityCenter")
	private String activityCenter;

	@SerializedName("di_title")
	private String title;
	
	@SerializedName("di_displacementType")
	private String displacementType;
	
	@SerializedName("di_totalTime")
	private String totalTime;

	public DisplacementTableDTO(Long id, String activityCenter, String title, String displacementType, int totalTime) {
		super();
		
		JspUtil jspUtil = new JspUtil();

		this.id = id;
		this.activityCenter = activityCenter;
		this.title = title;
		this.displacementType = jspUtil.parseTagToText("displacements.type." + displacementType);
		this.totalTime = Utiles.minutesToHoursAndMinutesString(totalTime);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivityCenter() {
		return activityCenter;
	}

	public void setActivityCenter(String activityCenter) {
		this.activityCenter = activityCenter;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDisplacementType() {
		return displacementType;
	}

	public void setDisplacementType(String displacementType) {
		this.displacementType = displacementType;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

}
