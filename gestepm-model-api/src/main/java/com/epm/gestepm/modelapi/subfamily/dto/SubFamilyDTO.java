package com.epm.gestepm.modelapi.subfamily.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SubFamilyDTO {

	private Long id;

	@SerializedName("subFamilyNameES")
	private String nameES;
	
	@SerializedName("subFamilyNameFR")
	private String nameFR;
	
	@SerializedName("subRoleNames")
	private String subRoleNames;
	
	private List<Long> subRoles;

	public SubFamilyDTO() {

	}

	public SubFamilyDTO(Long id, String nameES, String nameFR) {
		super();
		this.id = id;
		this.nameES = nameES;
		this.nameFR = nameFR;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameES() {
		return nameES;
	}

	public void setNameES(String nameES) {
		this.nameES = nameES;
	}

	public String getNameFR() {
		return nameFR;
	}

	public void setNameFR(String nameFR) {
		this.nameFR = nameFR;
	}

	public String getSubRoleNames() {
		return subRoleNames;
	}

	public void setSubRoleNames(String subRoleNames) {
		this.subRoleNames = subRoleNames;
	}

	public List<Long> getSubRoles() {
		return subRoles;
	}

	public void setSubRoles(List<Long> subRoles) {
		this.subRoles = subRoles;
	}
}
