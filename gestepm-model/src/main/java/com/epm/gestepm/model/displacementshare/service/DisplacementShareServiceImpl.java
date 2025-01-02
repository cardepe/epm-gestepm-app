package com.epm.gestepm.model.displacementshare.service;

import com.epm.gestepm.model.displacementshare.dao.DisplacementShareRepository;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShareTableDTO;
import com.epm.gestepm.modelapi.displacementshare.service.DisplacementShareService;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class DisplacementShareServiceImpl implements DisplacementShareService {
	
	@Autowired
	private DisplacementShareRepository displacementShareDao;
	
	@Override
	public DisplacementShare getDisplacementShareById(Long id) {
		return displacementShareDao.findById(id).orElse(null);
	}
	
	@Override
	public DisplacementShare save(DisplacementShare interventionShare) {	
		return displacementShareDao.save(interventionShare);
	}
	
	@Override
	public void deleteById(Long shareId) {
		displacementShareDao.deleteById(shareId);
	}

	@Override
	public List<ShareTableDTO> getShareTableByProjectId(Long projectId) {
		return displacementShareDao.findShareTableByProjectId(projectId);
	}
	
	@Override
	public List<ShareTableDTO> getShareTableByUserSigningId(Long userSigningId) {
		return displacementShareDao.findShareTableByUserSigningId(userSigningId);
	}
	
	@Override
	public Long getDisplacementSharesCountByUser(Long userId) {
		return displacementShareDao.findDisplacementSharesCountByUserId(userId);
	}
	
	@Override
	public List<DisplacementShareTableDTO> getDisplacementSharesByUserDataTables(Long userId, PaginationCriteria pagination) {
		return displacementShareDao.findDisplacementSharesByUserDataTables(userId, pagination);
	}
	
	@Override
	public List<DisplacementShare> getWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId, Integer manual) {
		return displacementShareDao.findWeekSigningsByUserId(startDate, endDate, userId, manual);
	}
}
