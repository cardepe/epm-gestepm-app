package com.epm.gestepm.model.company.service;

import java.util.List;

import com.epm.gestepm.model.company.dao.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.company.dto.Company;
import com.epm.gestepm.modelapi.company.service.CompanyService;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public List<Company> findAll() {
		return (List<Company>) companyRepository.findAll();
	}
	
	@Override
	public Company getById(Long id) {
		return companyRepository.findById(id).get();
	}
}
