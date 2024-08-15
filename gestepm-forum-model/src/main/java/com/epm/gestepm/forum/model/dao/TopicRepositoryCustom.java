package com.epm.gestepm.forum.model.dao;

public interface TopicRepositoryCustom {
	public void updateForumStatsWhenCreateTopic(long forumId, long lastPostId, long lastPosterId, String lastPostSubject, long lastPostTime, String lastPosterName);
}
