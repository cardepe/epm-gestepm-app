package com.epm.gestepm.model.displacementshare.service;

import java.util.Date;
import java.util.List;

import com.epm.gestepm.model.displacementshare.dao.DisplacementShareRepository;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShareTableDTO;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.user.dto.DailyPersonalSigningDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.displacementshare.service.DisplacementShareService;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;

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
	public List<DisplacementShare> getWeekSigningsByUserId(Date startDate, Date endDate, Long userId, Integer manual) {
		return displacementShareDao.findWeekSigningsByUserId(startDate, endDate, userId, manual);
	}
	
	@Override
	public List<DailyPersonalSigningDTO> getDailyDisplacementShareDTOByUserIdAndYear(Long userId, int year) {
		return displacementShareDao.findDailyDisplacementShareDTOByUserIdAndYear(userId, year);
	}

	@Override
	public List<ExpensesMonthDTO> getTimeMonthDTOByProjectId(Long projectId, Integer year) {
		return displacementShareDao.findTimeMonthDTOByProjectId(projectId, year);
	}
}
