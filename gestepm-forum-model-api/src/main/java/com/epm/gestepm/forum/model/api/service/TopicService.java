package com.epm.gestepm.forum.model.api.service;

import com.epm.gestepm.forum.model.api.dto.Topic;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TopicService {
	CompletableFuture<Topic> create(String topicTitle, String topicContent, Integer forumId, String userIp, String username, List<MultipartFile> files);
}
