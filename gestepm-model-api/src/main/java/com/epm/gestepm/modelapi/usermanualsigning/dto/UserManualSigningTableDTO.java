package com.epm.gestepm.modelapi.usermanualsigning.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserManualSigningTableDTO {

	@SerializedName("ums_id")
	private Long id;

	@SerializedName("ums_manualTypeId")
	private String manualTypeId;

	@SerializedName("ums_startDate")
	private Date startDate;

	@SerializedName("ums_endDate")
	private Date endDate;

	@SerializedName("ums_hasFile")
	private Boolean hasFile;

	public UserManualSigningTableDTO() {

	}

	public UserManualSigningTableDTO(Long id, String manualTypeId, Date startDate, Date endDate, Boolean hasFile) {
		this.id = id;
		this.manualTypeId = manualTypeId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.hasFile = hasFile;
	}

	public UserManualSigningTableDTO(Long id, String manualTypeId, Date startDate, Date endDate, byte[] file) {
		this.id = id;
		this.manualTypeId = manualTypeId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.hasFile = file != null && file.length > 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getManualTypeId() {
		return manualTypeId;
	}

	public void setManualTypeId(String manualTypeId) {
		this.manualTypeId = manualTypeId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getHasFile() {
		return hasFile;
	}

	public void setHasFile(Boolean hasFile) {
		this.hasFile = hasFile;
	}
}
