package com.epm.gestepm.model.interventionsharematerial.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionShareMaterial;

import java.util.List;

public interface InterventionShareMaterialRepository extends CrudRepository<InterventionShareMaterial, Long>, InterventionShareMaterialRepositoryCustom {

    List<InterventionShareMaterial> findInterventionShareMaterialByInterventionSubShareId(Long id);

}
