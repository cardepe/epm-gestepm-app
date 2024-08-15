package com.epm.gestepm.modelapi.manualsigningtype.service;

import com.epm.gestepm.modelapi.manualsigningtype.dto.ManualSigningType;

import java.util.List;

public interface ManualSigningTypeService {

    List<ManualSigningType> findAll();

    ManualSigningType findById(Long id);

}
