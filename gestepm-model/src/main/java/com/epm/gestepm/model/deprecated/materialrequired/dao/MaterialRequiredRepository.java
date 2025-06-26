package com.epm.gestepm.model.deprecated.materialrequired.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.deprecated.materialrequired.dto.MaterialRequired;

public interface MaterialRequiredRepository extends CrudRepository<MaterialRequired, Long>, MaterialRequiredRepositoryCustom {

}
