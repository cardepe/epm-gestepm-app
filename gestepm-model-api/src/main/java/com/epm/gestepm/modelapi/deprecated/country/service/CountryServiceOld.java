package com.epm.gestepm.modelapi.deprecated.country.service;

import com.epm.gestepm.modelapi.deprecated.country.dto.Country;

import java.util.List;

public interface CountryServiceOld {

	Country getById(Long id);

	List<Country> findAll();

}
