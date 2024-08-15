package com.epm.gestepm.model.company.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.company.dto.Company;

public interface CompanyRepository extends CrudRepository<Company, Long>, CompanyRepositoryCustom {
	
}
