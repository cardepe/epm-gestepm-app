package com.epm.gestepm.model.deprecated.workshare.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.deprecated.workshare.WorkShare;

public interface WorkShareRepository extends CrudRepository<WorkShare, Long>, WorkShareRepositoryCustom {
	
}
