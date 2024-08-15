package com.epm.gestepm.model.personalsigning.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;

public interface PersonalSigningRepository extends CrudRepository<PersonalSigning, Long>, PersonalSigningRepositoryCustom {

}
