package com.epm.gestepm.forum.model.api.dto;

public class UserForumDTO {

	private Long userId;
	private String username;
	
	public UserForumDTO() {
		
	}
	
	public UserForumDTO(Long userId, String username) {
		super();
		this.userId = userId;
		this.username = username;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
