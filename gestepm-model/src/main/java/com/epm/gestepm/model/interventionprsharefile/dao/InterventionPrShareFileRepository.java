package com.epm.gestepm.model.interventionprsharefile.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShareFile;

public interface InterventionPrShareFileRepository extends CrudRepository<InterventionPrShareFile, Long>, InterventionPrShareFileRepositoryCustom {

}
