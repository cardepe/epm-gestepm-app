package com.epm.gestepm.model.interventionsubshare.service;

import com.epm.gestepm.model.interventionsubshare.dao.InterventionSubShareRepository;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.deprecated.interventionsubshare.service.InterventionSubShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InterventionSubShareServiceImpl implements InterventionSubShareService {
	
	@Autowired
	private InterventionSubShareRepository interventionSubShareDao;
	
	@Override
	public List<ShareTableDTO> getShareTableByProjectId(Long projectId) {
		return interventionSubShareDao.findShareTableByProjectId(projectId);
	}
	
	@Override
	public List<ShareTableDTO> getShareTableByUserSigningId(Long userSigningId) {
		return interventionSubShareDao.findShareTableByUserSigningId(userSigningId);
	}
}
