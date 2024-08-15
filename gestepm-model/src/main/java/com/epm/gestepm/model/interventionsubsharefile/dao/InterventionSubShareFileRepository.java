package com.epm.gestepm.model.interventionsubsharefile.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.interventionsubsharefile.dto.InterventionSubShareFile;

public interface InterventionSubShareFileRepository extends CrudRepository<InterventionSubShareFile, Long>, InterventionSubShareFileRepositoryCustom {

}
