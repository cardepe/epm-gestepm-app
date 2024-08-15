package com.epm.gestepm.model.interventionprshare.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;

public interface InterventionPrShareRepository extends CrudRepository<InterventionPrShare, Long>, InterventionPrShareRepositoryCustom {
	
}
