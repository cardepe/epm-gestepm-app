package com.epm.gestepm.model.interventionsubshare.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;

public interface InterventionSubShareRepository extends CrudRepository<InterventionSubShare, Long>, InterventionSubShareRepositoryCustom {

}
