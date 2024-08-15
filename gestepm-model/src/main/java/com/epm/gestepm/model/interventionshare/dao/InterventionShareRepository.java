package com.epm.gestepm.model.interventionshare.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShare;

public interface InterventionShareRepository extends CrudRepository<InterventionShare, Long>, InterventionShareRepositoryCustom {

}
