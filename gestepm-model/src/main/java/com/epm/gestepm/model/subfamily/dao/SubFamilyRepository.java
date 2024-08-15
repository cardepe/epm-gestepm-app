package com.epm.gestepm.model.subfamily.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.subfamily.dto.SubFamily;

public interface SubFamilyRepository extends CrudRepository<SubFamily, Long>, SubFamilyRepositoryCustom {
	void removeByFamilyId(Long id);
}
