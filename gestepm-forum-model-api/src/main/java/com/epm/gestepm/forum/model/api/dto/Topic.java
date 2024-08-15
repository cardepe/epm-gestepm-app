package com.epm.gestepm.forum.model.api.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phpbb_topics")
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "topic_id", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@Column(name = "forum_id", nullable = false, length = 8)
	private long forumId;
	
	@Column(name = "icon_id", nullable = false, length = 8)
	private int iconId;
	
	@Column(name = "topic_attachment", nullable = false, length = 1)
	private int topicAttachment;
	
	@Column(name = "topic_reported", nullable = false, length = 1)
	private int topicReported;
	
	@Column(name = "topic_title", nullable = false, length = 255)
	private String topicTitle;
	
	@Column(name = "topic_poster", nullable = false, length = 10)
	private long topicPoster;
	
	@Column(name = "topic_time", nullable = false, length = 11)
	private long topicTime;
	
	@Column(name = "topic_time_limit", nullable = false, length = 11)
	private int topicTimeLimit;
	
	@Column(name = "topic_views", nullable = false, length = 8)
	private int topicViews;
	
	@Column(name = "topic_status", nullable = false, length = 3)
	private int topicStatus;
	
	@Column(name = "topic_type", nullable = false, length = 3)
	private int topicType;
	
	@Column(name = "topic_first_post_id", nullable = false, length = 10)
	private long topicFirstPostId;
	
	@Column(name = "topic_first_poster_name", nullable = false, length = 255)
	private String topicFirstPosterName;
	
	@Column(name = "topic_first_poster_colour", nullable = false, length = 6)
	private String topicFirstPosterColour;
	
	@Column(name = "topic_last_post_id", nullable = false, length = 10)
	private long topicLastPostId;
	
	@Column(name = "topic_last_poster_id", nullable = false, length = 10)
	private long topicLastPosterId;
	
	@Column(name = "topic_last_poster_name", nullable = false, length = 255)
	private String topicLastPosterName;
	
	@Column(name = "topic_last_poster_colour", nullable = false, length = 6)
	private String topicLastPosterColour;
	
	@Column(name = "topic_last_post_subject", nullable = false, length = 255)
	private String topicLastPostSubject;
	
	@Column(name = "topic_last_post_time", nullable = false, length = 11)
	private long topicLastPostTime;
	
	@Column(name = "topic_last_view_time", nullable = false, length = 11)
	private long topicLastViewTime;
	
	@Column(name = "topic_moved_id", nullable = false, length = 10)
	private int topicMovedId;
	
	@Column(name = "topic_bumped", nullable = false, length = 1)
	private int topicBumped;
	
	@Column(name = "topic_bumper", nullable = false, length = 8)
	private int topicBumper;
	
	@Column(name = "poll_title", nullable = false, length = 255)
	private String pollTitle;
	
	@Column(name = "poll_start", nullable = false, length = 11)
	private int pollStart;
	
	@Column(name = "poll_length", nullable = false, length = 11)
	private int pollLength;
	
	@Column(name = "poll_max_options", nullable = false, length = 4)
	private int pollMaxOptions;
	
	@Column(name = "poll_last_vote", nullable = false, length = 11)
	private int pollLastVote;
	
	@Column(name = "poll_vote_change", nullable = false, length = 1)
	private int pollVoteChange;
	
	@Column(name = "topic_visibility", nullable = false, length = 3)
	private int topicVisibility;
	
	@Column(name = "topic_delete_time", nullable = false, length = 11)
	private int topicDeleteTime;
	
	@Column(name = "topic_delete_reason", nullable = false, length = 255)
	private String topicDeleteReason;
	
	@Column(name = "topic_delete_user", nullable = false, length = 10)
	private int topicDeleteUser;
	
	@Column(name = "topic_posts_approved", nullable = false, length = 8)
	private int topicPostsApproved;
	
	@Column(name = "topic_posts_unapproved", nullable = false, length = 8)
	private int topicPostsUnapproved;
	
	@Column(name = "topic_posts_softdeleted", nullable = false, length = 8)
	private int topicPostsSoftdeleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getForumId() {
		return forumId;
	}

	public void setForumId(long forumId) {
		this.forumId = forumId;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public int getTopicAttachment() {
		return topicAttachment;
	}

	public void setTopicAttachment(int topicAttachment) {
		this.topicAttachment = topicAttachment;
	}

	public int getTopicReported() {
		return topicReported;
	}

	public void setTopicReported(int topicReported) {
		this.topicReported = topicReported;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public long getTopicPoster() {
		return topicPoster;
	}

	public void setTopicPoster(long topicPoster) {
		this.topicPoster = topicPoster;
	}

	public long getTopicTime() {
		return topicTime;
	}

	public void setTopicTime(long topicTime) {
		this.topicTime = topicTime;
	}

	public int getTopicTimeLimit() {
		return topicTimeLimit;
	}

	public void setTopicTimeLimit(int topicTimeLimit) {
		this.topicTimeLimit = topicTimeLimit;
	}

	public int getTopicViews() {
		return topicViews;
	}

	public void setTopicViews(int topicViews) {
		this.topicViews = topicViews;
	}

	public int getTopicStatus() {
		return topicStatus;
	}

	public void setTopicStatus(int topicStatus) {
		this.topicStatus = topicStatus;
	}

	public int getTopicType() {
		return topicType;
	}

	public void setTopicType(int topicType) {
		this.topicType = topicType;
	}

	public long getTopicFirstPostId() {
		return topicFirstPostId;
	}

	public void setTopicFirstPostId(long topicFirstPostId) {
		this.topicFirstPostId = topicFirstPostId;
	}

	public String getTopicFirstPosterName() {
		return topicFirstPosterName;
	}

	public void setTopicFirstPosterName(String topicFirstPosterName) {
		this.topicFirstPosterName = topicFirstPosterName;
	}

	public String getTopicFirstPosterColour() {
		return topicFirstPosterColour;
	}

	public void setTopicFirstPosterColour(String topicFirstPosterColour) {
		this.topicFirstPosterColour = topicFirstPosterColour;
	}

	public long getTopicLastPostId() {
		return topicLastPostId;
	}

	public void setTopicLastPostId(long topicLastPostId) {
		this.topicLastPostId = topicLastPostId;
	}

	public long getTopicLastPosterId() {
		return topicLastPosterId;
	}

	public void setTopicLastPosterId(long topicLastPosterId) {
		this.topicLastPosterId = topicLastPosterId;
	}

	public String getTopicLastPosterName() {
		return topicLastPosterName;
	}

	public void setTopicLastPosterName(String topicLastPosterName) {
		this.topicLastPosterName = topicLastPosterName;
	}

	public String getTopicLastPosterColour() {
		return topicLastPosterColour;
	}

	public void setTopicLastPosterColour(String topicLastPosterColour) {
		this.topicLastPosterColour = topicLastPosterColour;
	}

	public String getTopicLastPostSubject() {
		return topicLastPostSubject;
	}

	public void setTopicLastPostSubject(String topicLastPostSubject) {
		this.topicLastPostSubject = topicLastPostSubject;
	}

	public long getTopicLastPostTime() {
		return topicLastPostTime;
	}

	public void setTopicLastPostTime(long topicLastPostTime) {
		this.topicLastPostTime = topicLastPostTime;
	}

	public long getTopicLastViewTime() {
		return topicLastViewTime;
	}

	public void setTopicLastViewTime(long topicLastViewTime) {
		this.topicLastViewTime = topicLastViewTime;
	}

	public int getTopicMovedId() {
		return topicMovedId;
	}

	public void setTopicMovedId(int topicMovedId) {
		this.topicMovedId = topicMovedId;
	}

	public int getTopicBumped() {
		return topicBumped;
	}

	public void setTopicBumped(int topicBumped) {
		this.topicBumped = topicBumped;
	}

	public int getTopicBumper() {
		return topicBumper;
	}

	public void setTopicBumper(int topicBumper) {
		this.topicBumper = topicBumper;
	}

	public String getPollTitle() {
		return pollTitle;
	}

	public void setPollTitle(String pollTitle) {
		this.pollTitle = pollTitle;
	}

	public int getPollStart() {
		return pollStart;
	}

	public void setPollStart(int pollStart) {
		this.pollStart = pollStart;
	}

	public int getPollLength() {
		return pollLength;
	}

	public void setPollLength(int pollLength) {
		this.pollLength = pollLength;
	}

	public int getPollMaxOptions() {
		return pollMaxOptions;
	}

	public void setPollMaxOptions(int pollMaxOptions) {
		this.pollMaxOptions = pollMaxOptions;
	}

	public int getPollLastVote() {
		return pollLastVote;
	}

	public void setPollLastVote(int pollLastVote) {
		this.pollLastVote = pollLastVote;
	}

	public int getPollVoteChange() {
		return pollVoteChange;
	}

	public void setPollVoteChange(int pollVoteChange) {
		this.pollVoteChange = pollVoteChange;
	}

	public int getTopicVisibility() {
		return topicVisibility;
	}

	public void setTopicVisibility(int topicVisibility) {
		this.topicVisibility = topicVisibility;
	}

	public int getTopicDeleteTime() {
		return topicDeleteTime;
	}

	public void setTopicDeleteTime(int topicDeleteTime) {
		this.topicDeleteTime = topicDeleteTime;
	}

	public String getTopicDeleteReason() {
		return topicDeleteReason;
	}

	public void setTopicDeleteReason(String topicDeleteReason) {
		this.topicDeleteReason = topicDeleteReason;
	}

	public int getTopicDeleteUser() {
		return topicDeleteUser;
	}

	public void setTopicDeleteUser(int topicDeleteUser) {
		this.topicDeleteUser = topicDeleteUser;
	}

	public int getTopicPostsApproved() {
		return topicPostsApproved;
	}

	public void setTopicPostsApproved(int topicPostsApproved) {
		this.topicPostsApproved = topicPostsApproved;
	}

	public int getTopicPostsUnapproved() {
		return topicPostsUnapproved;
	}

	public void setTopicPostsUnapproved(int topicPostsUnapproved) {
		this.topicPostsUnapproved = topicPostsUnapproved;
	}

	public int getTopicPostsSoftdeleted() {
		return topicPostsSoftdeleted;
	}

	public void setTopicPostsSoftdeleted(int topicPostsSoftdeleted) {
		this.topicPostsSoftdeleted = topicPostsSoftdeleted;
	}
}
