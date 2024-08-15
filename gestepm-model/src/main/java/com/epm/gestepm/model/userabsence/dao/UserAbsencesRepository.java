package com.epm.gestepm.model.userabsence.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.userabsence.dto.UserAbsence;

public interface UserAbsencesRepository extends CrudRepository<UserAbsence, Long>, UserAbsencesRepositoryCustom {

}
