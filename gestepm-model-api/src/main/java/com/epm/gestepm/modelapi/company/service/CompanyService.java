package com.epm.gestepm.modelapi.company.service;

import com.epm.gestepm.modelapi.company.dto.Company;

import java.util.List;

public interface CompanyService {

	List<Company> findAll();
	Company getById(Long id);
}
