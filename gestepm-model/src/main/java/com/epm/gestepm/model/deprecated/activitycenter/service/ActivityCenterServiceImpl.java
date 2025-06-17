package com.epm.gestepm.model.deprecated.activitycenter.service;

import com.epm.gestepm.model.deprecated.activitycenter.dao.ActivityCenterRepository;
import com.epm.gestepm.modelapi.deprecated.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.deprecated.activitycenter.service.ActivityCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("activityCenterServiceOld")
@Transactional
public class ActivityCenterServiceImpl implements ActivityCenterService {

	@Autowired
	private ActivityCenterRepository activityCenterRepository;

	@Override
	public ActivityCenter getById(Long id) {
		return activityCenterRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<ActivityCenter> findAll() {
		return (List<ActivityCenter>) activityCenterRepository.findAll();
	}

}
