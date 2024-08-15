package com.epm.gestepm.forum.model.api.service;

import java.util.List;

import com.epm.gestepm.forum.model.api.dto.UserForum;
import com.epm.gestepm.forum.model.api.dto.ForumDTO;

public interface UserForumService {

	List<UserForum> findAll();
	UserForum save(UserForum entity);
	
	UserForum createUser(String username, String email, String password);
	UserForum updateUserPassword(String email, String password);
	
	String getUserLoginForm();
	
	List<ForumDTO> getAllForumsToDTO();
}
