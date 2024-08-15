package com.epm.gestepm.model.constructionsharefile.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.constructionsharefile.dto.ConstructionShareFile;

public interface ConstructionShareFileRepository extends CrudRepository<ConstructionShareFile, Long>, ConstructionShareFileRepositoryCustom {

}
