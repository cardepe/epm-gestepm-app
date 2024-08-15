package com.epm.gestepm.forum.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.epm.gestepm.forum.model.api.dto.ForumDTO;
import org.springframework.stereotype.Repository;

@Repository
public class UserForumRepositoryImpl implements UserForumRepositoryCustom {

	@PersistenceContext(unitName = "forumEntityManager")
	private EntityManager entityManager;
	
	public void createUserGroup(Long userId) {
		Query query = entityManager.createNativeQuery("INSERT INTO phpbb_user_group VALUES (2, " + userId + ", 0, 0)");
		query.executeUpdate();
	}
	
	public void updateUserPostStats(Long userId, long postTime) {
		Query query = entityManager.createNativeQuery("UPDATE phpbb_users SET user_posts = user_posts + 1, user_lastpost_time = " + postTime + " WHERE user_id = " + userId);
		query.executeUpdate();
	}
	
	public void updateForumStatistics(Long userId, String username) {
		Query queryUserId = entityManager.createNativeQuery("UPDATE phpbb_config SET config_value = " + userId + " WHERE config_name = 'newest_user_id'");
		queryUserId.executeUpdate();
		
		Query queryUsername = entityManager.createNativeQuery("UPDATE phpbb_config SET config_value = :username WHERE config_name = 'newest_username'");
		queryUsername.setParameter("username", username);
		queryUsername.executeUpdate();
		
		Query query = entityManager.createNativeQuery("UPDATE phpbb_config SET config_value = config_value + 1 WHERE config_name = 'num_users'");
		query.executeUpdate();
	}	
	
	@SuppressWarnings("unchecked")
	public List<ForumDTO> findAllForumsToDTO() {
		List<ForumDTO> forumDTOs = new ArrayList<>();
		List<Object[]> resultList = entityManager.createNativeQuery("SELECT forum_id, forum_name FROM phpbb_forums").getResultList();
		
		for (Object[] obj : resultList) {
			forumDTOs.add(new ForumDTO(Long.parseLong(obj[0].toString()), obj[1].toString()));
		}
		
		return forumDTOs;
	}
}
