package com.epm.gestepm.model.modifiedsigning.dao;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.modifiedsigning.dto.ModifiedSigningTableDTO;

import java.util.List;

public interface ModifiedSigningRepositoryCustom {

    List<ModifiedSigningTableDTO> findModifiedSigningsDataTables(List<Long> projectIds, Long userId, PaginationCriteria pagination);

    Long findModifiedSigningsCount(List<Long> projectIds, Long userId);

}
