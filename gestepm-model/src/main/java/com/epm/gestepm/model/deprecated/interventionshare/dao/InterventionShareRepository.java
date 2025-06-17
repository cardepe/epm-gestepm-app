package com.epm.gestepm.model.deprecated.interventionshare.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.InterventionShare;

public interface InterventionShareRepository extends CrudRepository<InterventionShare, Long>, InterventionShareRepositoryCustom {

}
