package com.epm.gestepm.modelapi.modifiedsigning.service;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.modifiedsigning.dto.ModifiedSigning;
import com.epm.gestepm.modelapi.modifiedsigning.dto.ModifiedSigningTableDTO;

import java.util.List;

public interface ModifiedSigningService {

    ModifiedSigning getById(Long id);

    ModifiedSigning save(ModifiedSigning modifiedSigning);

    void deleteById(Long id);

    List<ModifiedSigningTableDTO> getModifiedSigningsDataTable(List<Long> projectIds, Long userId, PaginationCriteria pagination);

    Long getModifiedSigningsCount(List<Long> projectIds, Long userId);

}
