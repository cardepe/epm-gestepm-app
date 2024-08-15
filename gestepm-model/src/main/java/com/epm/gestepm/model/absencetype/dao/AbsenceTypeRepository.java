package com.epm.gestepm.model.absencetype.dao;

import com.epm.gestepm.modelapi.absencetype.dto.AbsenceType;
import org.springframework.data.repository.CrudRepository;

public interface AbsenceTypeRepository extends CrudRepository<AbsenceType, Long>, AbsenceTypeRepositoryCustom {
	
}
