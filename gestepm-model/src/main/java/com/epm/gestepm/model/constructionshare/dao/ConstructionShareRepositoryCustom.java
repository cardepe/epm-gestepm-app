package com.epm.gestepm.model.constructionshare.dao;

import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.user.dto.DailyPersonalSigningDTO;

import java.util.Date;
import java.util.List;

public interface ConstructionShareRepositoryCustom {

	List<ShareTableDTO> findShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress);
	List<ShareTableDTO> findShareTableByUserId(Long userId, Long projectId, Integer progress);
	List<ShareTableDTO> findShareTableByProjectId(Long projectId);
	List<ShareTableDTO> findShareTableByUserSigningId(Long userSigningId);
	List<ConstructionShare> findWeekSigningsByUserId(Date startDate, Date endDate, Long userId);
	List<ConstructionShare> findWeekSigningsByProjectId(Date startDate, Date endDate, Long projectId);
	List<DailyPersonalSigningDTO> findDailyConstructionShareDTOByUserIdAndDate(final Long userId, final Integer month, final Integer year);
}
