package com.epm.gestepm.model.activitycenter.dao;

import com.epm.gestepm.modelapi.absencetype.dto.ActivityCenterTableDTO;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;

import java.util.List;

public interface ActivityCenterRepositoryCustom {
	List<ActivityCenterTableDTO> findActivityCentersDataTables(PaginationCriteria pagination);
	Long findActivityCentersCount();
}
