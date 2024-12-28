package com.epm.gestepm.modelapi.displacement.dto;

import com.epm.gestepm.modelapi.common.utils.JspUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DisplacementTableDTO {

	@JsonProperty("di_id")
	private Long id;

	@JsonProperty("di_activityCenter")
	private String activityCenter;

	@JsonProperty("di_title")
	private String title;
	
	@JsonProperty("di_displacementType")
	private String displacementType;
	
	@JsonProperty("di_totalTime")
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

}
