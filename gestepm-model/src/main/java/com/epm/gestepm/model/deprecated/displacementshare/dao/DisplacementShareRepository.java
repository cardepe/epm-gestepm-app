package com.epm.gestepm.model.deprecated.displacementshare.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.deprecated.displacementshare.dto.DisplacementShare;

public interface DisplacementShareRepository extends CrudRepository<DisplacementShare, Long>, DisplacementShareRepositoryCustom {
	
}
