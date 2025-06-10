package com.epm.gestepm.modelapi.deprecated.user.dto;

public class UserDTO {

	private Long userId;
	private Long signingId;
	private String name;
	private String surnames;
	private String email;
	private String password;
	private Long activityCenterId;
	private Long roleId;
	private Long subRoleId;
	private Double workingHours;
	private Integer state;
	
	public UserDTO() {
		
	}
	
	public UserDTO(Long userId, String name, String surnames) {
		super();
		this.userId = userId;
		this.name = name;
		this.surnames = surnames;
	}
	
	public UserDTO(Long userId, String name, String surnames, Long signingId, Integer state) {
		super();
		this.userId = userId;
		this.signingId = signingId;
		this.name = name;
		this.surnames = surnames;
		this.state = state;
	}

	public UserDTO(Long userId, String name, String surnames, Long roleId, Long subRoleId) {
		super();
		this.userId = userId;
		this.name = name;
		this.surnames = surnames;
		this.roleId = roleId;
		this.subRoleId = subRoleId;
	}
	
	public UserDTO(Long userId, String name, String surnames, String email, Long roleId, Long subRoleId) {
		super();
		this.userId = userId;
		this.name = name;
		this.surnames = surnames;
		this.email = email;
		this.roleId = roleId;
		this.subRoleId = subRoleId;
	}
	
	public UserDTO(Long userId, String name, String surnames, String email, String password, Long activityCenterId, Long roleId,
			Long subRoleId, Double workingHours) {
		super();
		this.userId = userId;
		this.name = name;
		this.surnames = surnames;
		this.email = email;
		this.password = password;
		this.activityCenterId = activityCenterId;
		this.roleId = roleId;
		this.subRoleId = subRoleId;
		this.workingHours = workingHours;
	}

	public UserDTO(Long userId, Long signingId, String name, String surnames, String email, String password,
			Long activityCenterId, Long roleId, Long subRoleId, Double workingHours) {
		super();
		this.userId = userId;
		this.signingId = signingId;
		this.name = name;
		this.surnames = surnames;
		this.email = email;
		this.password = password;
		this.activityCenterId = activityCenterId;
		this.roleId = roleId;
		this.subRoleId = subRoleId;
		this.workingHours = workingHours;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSigningId() {
		return signingId;
	}

	public void setSigningId(Long signingId) {
		this.signingId = signingId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getActivityCenterId() {
		return activityCenterId;
	}

	public void setActivityCenterId(Long activityCenterId) {
		this.activityCenterId = activityCenterId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getSubRoleId() {
		return subRoleId;
	}

	public void setSubRoleId(Long subRoleId) {
		this.subRoleId = subRoleId;
	}

	public Double getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(Double workingHours) {
		this.workingHours = workingHours;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
