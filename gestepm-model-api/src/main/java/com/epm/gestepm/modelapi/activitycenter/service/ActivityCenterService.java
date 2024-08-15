package com.epm.gestepm.modelapi.activitycenter.service;

import com.epm.gestepm.modelapi.activitycenter.dto.ActivityCenter;

import java.util.List;

public interface ActivityCenterService {
	ActivityCenter getById(Long id);
	List<ActivityCenter> findAll();
}
