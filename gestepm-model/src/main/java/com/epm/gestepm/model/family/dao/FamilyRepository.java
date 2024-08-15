package com.epm.gestepm.model.family.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.family.dto.Family;

public interface FamilyRepository extends CrudRepository<Family, Long>, FamilyRepositoryCustom {

}
