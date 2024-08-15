package com.epm.gestepm.modelapi.displacementshare.dto;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class DisplacementShareTableDTO {

	@SerializedName("ds_id")
	private Long id;
	
	@SerializedName("pr_name")
	private String projectName;
	
	@SerializedName("di_title")
	private String displacementTitle;
	
	@SerializedName("ds_displacementDate")
	private Date displacementDate;
	
	@SerializedName("ds_round_trip")
	private Boolean roundTrip;

	public DisplacementShareTableDTO() {

	}

	public DisplacementShareTableDTO(Long id, String projectName, String displacementTitle, Date displacementDate,
			Boolean roundTrip) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.displacementTitle = displacementTitle;
		this.displacementDate = displacementDate;
		this.roundTrip = roundTrip;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDisplacementTitle() {
		return displacementTitle;
	}

	public void setDisplacementTitle(String displacementTitle) {
		this.displacementTitle = displacementTitle;
	}

	public Date getDisplacementDate() {
		return displacementDate;
	}

	public void setDisplacementDate(Date displacementDate) {
		this.displacementDate = displacementDate;
	}

	public Boolean getRoundTrip() {
		return roundTrip;
	}

	public void setRoundTrip(Boolean roundTrip) {
		this.roundTrip = roundTrip;
	}
}
