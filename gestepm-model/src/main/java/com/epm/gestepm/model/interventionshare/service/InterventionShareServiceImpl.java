package com.epm.gestepm.model.interventionshare.service;

import com.epm.gestepm.model.interventionshare.dao.InterventionShareRepository;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.deprecated.interventionshare.service.InterventionShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InterventionShareServiceImpl implements InterventionShareService {

	@Autowired
	private InterventionShareRepository interventionShareDao;

	@Override
	public List<ShareTableDTO> getShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress) {
		return interventionShareDao.findShareTableByActivityCenterId(id, activityCenterId, projectId, progress);
	}

	@Override
	public Long getAllShareTableCount(Long id, String type, Long projectId, Integer progress, Long userId) {
		return interventionShareDao.findAllShareTableCount(id, type, projectId, progress, userId);
	}
	
	@Override
	public List<ShareTableDTO> getAllShareTable(Integer pageNumber, Integer pageSize, Long id, String type, Long projectId, Integer progress, Long userId) {
		return interventionShareDao.findAllShareTable(pageNumber, pageSize, id, type, projectId, progress, userId);
	}
}
