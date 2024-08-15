package com.epm.gestepm.model.materialrequired.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.materialrequired.dto.MaterialRequired;

public interface MaterialRequiredRepository extends CrudRepository<MaterialRequired, Long>, MaterialRequiredRepositoryCustom {

}
