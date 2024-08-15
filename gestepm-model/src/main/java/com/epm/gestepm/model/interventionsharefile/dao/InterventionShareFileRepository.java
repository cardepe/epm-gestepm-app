package com.epm.gestepm.model.interventionsharefile.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.interventionsharefile.dto.InterventionShareFile;

public interface InterventionShareFileRepository extends CrudRepository<InterventionShareFile, Long>, InterventionShareFileRepositoryCustom {

}
