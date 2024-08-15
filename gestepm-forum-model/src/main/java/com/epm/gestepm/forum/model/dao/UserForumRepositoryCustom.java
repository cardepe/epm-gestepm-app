package com.epm.gestepm.forum.model.dao;

import com.epm.gestepm.forum.model.api.dto.ForumDTO;

import java.util.List;

public interface UserForumRepositoryCustom {
	public void createUserGroup(Long userId);
	public void updateUserPostStats(Long userId, long postTime);
	
	// Custom of another tables
	public void updateForumStatistics(Long userId, String username);
	public List<ForumDTO> findAllForumsToDTO();
}
