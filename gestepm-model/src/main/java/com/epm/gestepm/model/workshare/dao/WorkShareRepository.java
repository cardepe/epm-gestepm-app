package com.epm.gestepm.model.workshare.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.workshare.dto.WorkShare;

public interface WorkShareRepository extends CrudRepository<WorkShare, Long>, WorkShareRepositoryCustom {
	
}
