package com.epm.gestepm.forum.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class TopicRepositoryImpl implements TopicRepositoryCustom {

	@PersistenceContext(unitName = "forumEntityManager")
	private EntityManager entityManager;
	
	public void updateForumStatsWhenCreateTopic(long forumId, long lastPostId, long lastPosterId, String lastPostSubject, long lastPostTime, String lastPosterName) {

		Query query = entityManager.createNativeQuery("UPDATE phpbb_forums SET forum_last_post_id = " + lastPostId + ", forum_last_poster_id = " + lastPosterId + ", forum_last_post_subject = :lastPostSubject, forum_last_post_time = " + lastPostTime + ", forum_last_poster_name = :lastPosterName, forum_posts_approved = forum_posts_approved + 1, forum_topics_approved = forum_topics_approved + 1 WHERE forum_id = " + forumId);
		query.setParameter("lastPostSubject", lastPostSubject);
		query.setParameter("lastPosterName", lastPosterName);
		query.executeUpdate();
	}
}
