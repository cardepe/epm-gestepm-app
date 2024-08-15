package com.epm.gestepm.modelapi.project.dto;

import com.google.gson.annotations.SerializedName;

public class ProjectMemberDTO {
	
	@SerializedName("us_id")
	private Long id;
	
	@SerializedName("us_fullName")
	private String fullName;
	
	@SerializedName("us_role")
	private String role;
	
	public ProjectMemberDTO() {
		
	}
	
	public ProjectMemberDTO(Long id, String fullName) {
		super();
		this.id = id;
		this.fullName = fullName;
	}
	
	public ProjectMemberDTO(Long id, String fullName, String role) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
