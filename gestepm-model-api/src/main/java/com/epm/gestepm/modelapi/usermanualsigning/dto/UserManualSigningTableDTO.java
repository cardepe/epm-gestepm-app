package com.epm.gestepm.modelapi.usermanualsigning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserManualSigningTableDTO {

	@JsonProperty("ums_id")
	private Long id;

	@JsonProperty("ums_manualTypeId")
	private String manualTypeId;

	@JsonProperty("ums_startDate")
	private Date startDate;

	@JsonProperty("ums_endDate")
	private Date endDate;

	@JsonProperty("ums_hasFile")
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
}
