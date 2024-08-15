package com.epm.gestepm.model.company.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.epm.gestepm.model.company.dao.CompanyRepositoryCustom;

@Repository
public class CompanyRepositoryImpl implements CompanyRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;
	
}
