package com.epm.gestepm.model.deprecated.constructionshare.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.deprecated.constructionshare.dto.ConstructionShare;

public interface ConstructionShareRepository extends CrudRepository<ConstructionShare, Long>, ConstructionShareRepositoryCustom {
	
}
