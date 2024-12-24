package com.epm.gestepm.forum.model.api.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.multipart.MultipartFile;

import com.epm.gestepm.forum.model.api.dto.Topic;

public interface TopicService {
	CompletableFuture<Topic> create(String topicTitle, String topicContent, Long forumId, String userIp, String username, List<MultipartFile> files);
}
