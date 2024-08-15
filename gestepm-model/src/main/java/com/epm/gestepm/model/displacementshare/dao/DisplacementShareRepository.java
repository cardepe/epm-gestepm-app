package com.epm.gestepm.model.displacementshare.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;

public interface DisplacementShareRepository extends CrudRepository<DisplacementShare, Long>, DisplacementShareRepositoryCustom {
	
}
