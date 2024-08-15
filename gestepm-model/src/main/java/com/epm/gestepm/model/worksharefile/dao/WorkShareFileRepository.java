package com.epm.gestepm.model.worksharefile.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.worksharefile.dto.WorkShareFile;

public interface WorkShareFileRepository extends CrudRepository<WorkShareFile, Long>, WorkShareFileRepositoryCustom {

}
