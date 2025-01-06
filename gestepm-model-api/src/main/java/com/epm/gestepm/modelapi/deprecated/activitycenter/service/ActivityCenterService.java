package com.epm.gestepm.modelapi.deprecated.activitycenter.service;

import com.epm.gestepm.modelapi.deprecated.activitycenter.dto.ActivityCenter;

import java.util.List;

public interface ActivityCenterService {
	ActivityCenter getById(Long id);
	List<ActivityCenter> findAll();
}
