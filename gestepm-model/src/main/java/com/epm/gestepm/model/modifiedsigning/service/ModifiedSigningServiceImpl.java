package com.epm.gestepm.model.modifiedsigning.service;

import com.epm.gestepm.model.modifiedsigning.dao.ModifiedSigningRepository;
import com.epm.gestepm.modelapi.modifiedsigning.dto.ModifiedSigningTableDTO;
import com.epm.gestepm.modelapi.modifiedsigning.service.ModifiedSigningService;
import com.epm.gestepm.modelapi.modifiedsigning.dto.ModifiedSigning;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ModifiedSigningServiceImpl implements ModifiedSigningService {

    @Autowired
    private ModifiedSigningRepository modifiedSigningRepository;

    @Override
    public ModifiedSigning getById(Long id) {
        return modifiedSigningRepository.findById(id).get();
    }

    @Override
    public ModifiedSigning save(ModifiedSigning modifiedSigning) {
        return modifiedSigningRepository.save(modifiedSigning);
    }

    @Override
    public void deleteById(Long id) {
        modifiedSigningRepository.deleteById(id);
    }

    @Override
    public List<ModifiedSigningTableDTO> getModifiedSigningsDataTable(List<Long> projectIds, Long userId, PaginationCriteria pagination) {
        return modifiedSigningRepository.findModifiedSigningsDataTables(projectIds, userId, pagination);
    }

    @Override
    public Long getModifiedSigningsCount(List<Long> projectIds, Long userId) {
        return modifiedSigningRepository.findModifiedSigningsCount(projectIds, userId);
    }
}
