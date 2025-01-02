package com.epm.gestepm.model.country.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.deprecated.country.dto.Country;

public interface CountryRepository extends CrudRepository<Country, Long> {
	
}
