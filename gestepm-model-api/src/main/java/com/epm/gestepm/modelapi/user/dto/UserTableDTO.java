package com.epm.gestepm.modelapi.user.dto;

import com.google.gson.annotations.SerializedName;

public class UserTableDTO {

	@SerializedName("us_id")
	private Long id;
	
	@SerializedName("us_name")
	private String name;
	
	@SerializedName("us_surnames")
	private String surnames;
	
	@SerializedName("ro_roleName")
	private String roleName;
	
	@SerializedName("sr_subRoleName")
	private String subRoleName;


	public UserTableDTO(Long id, String name, String surnames, String roleName, String subRoleName) {
		super();
		this.id = id;
		this.name = name;
		this.surnames = surnames;
		this.roleName = roleName;
		this.subRoleName = subRoleName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurnames() {
		return surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getSubRoleName() {
		return subRoleName;
	}

	public void setSubRoleName(String subRoleName) {
		this.subRoleName = subRoleName;
	}
	
	
}
