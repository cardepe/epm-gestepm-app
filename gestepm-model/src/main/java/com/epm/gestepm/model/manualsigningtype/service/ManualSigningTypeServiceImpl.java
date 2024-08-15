package com.epm.gestepm.model.manualsigningtype.service;

import com.epm.gestepm.model.manualsigningtype.dao.ManualSigningTypeRepository;
import com.epm.gestepm.modelapi.manualsigningtype.dto.ManualSigningType;
import com.epm.gestepm.modelapi.manualsigningtype.service.ManualSigningTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ManualSigningTypeServiceImpl implements ManualSigningTypeService {

    @Autowired
    private ManualSigningTypeRepository manualSigningTypeRepository;

    @Override
    public List<ManualSigningType> findAll() {
        return (List<ManualSigningType>) this.manualSigningTypeRepository.findAll();
    }

    @Override
    public ManualSigningType findById(Long id) {
        return this.manualSigningTypeRepository.findById(id).orElse(null);
    }
}
