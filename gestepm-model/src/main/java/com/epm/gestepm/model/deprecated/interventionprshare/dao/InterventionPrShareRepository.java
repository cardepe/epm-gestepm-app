package com.epm.gestepm.model.deprecated.interventionprshare.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.deprecated.interventionprshare.dto.InterventionPrShare;

public interface InterventionPrShareRepository extends CrudRepository<InterventionPrShare, Long>, InterventionPrShareRepositoryCustom {
	
}
