package com.epm.gestepm.model.constructionshare.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;

public interface ConstructionShareRepository extends CrudRepository<ConstructionShare, Long>, ConstructionShareRepositoryCustom {
	
}
