package com.epm.gestepm.model.deprecated.country.service;

import com.epm.gestepm.model.deprecated.country.dao.CountryRepository;
import com.epm.gestepm.modelapi.deprecated.country.dto.Country;
import com.epm.gestepm.modelapi.deprecated.country.service.CountryServiceOld;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CountryServiceOldImpl implements CountryServiceOld {

	private final CountryRepository countryRepository;

    public CountryServiceOldImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
	public Country getById(Long id) {
		return countryRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<Country> findAll() {
		return (List<Country>) countryRepository.findAll();
	}

}
