package com.epm.gestepm.model.interventionsubshare.dao;

import java.util.Date;
import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShareTableDTO;

public interface InterventionSubShareRepositoryCustom {
	InterventionSubShare findByShareAndOrder(Long shareId, Long interventionId);
	Long findInterventionSubSharesCountByShareId(Long shareId);
	List<InterventionSubShareTableDTO> findInterventionSubSharesByShareTables(Long shareId, PaginationCriteria pagination);
	InterventionSubShare findOpenIntervention(Long shareId);
	List<InterventionSubShare> findWeekSigningsByUserId(Date startDate, Date endDate, Long userId);
	List<InterventionSubShare> findWeekSigningsByProjectId(Date startDate, Date endDate, Long projectId);
	List<ShareTableDTO> findShareTableByProjectId(Long projectId);
	List<ShareTableDTO> findShareTableByUserSigningId(Long userSigningId);
}
