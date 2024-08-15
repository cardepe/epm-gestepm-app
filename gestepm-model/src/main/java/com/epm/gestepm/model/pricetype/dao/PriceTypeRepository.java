package com.epm.gestepm.model.pricetype.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.pricetype.dto.PriceType;

public interface PriceTypeRepository extends CrudRepository<PriceType, Long>, PriceTypeRepositoryCustom {
	
}
