package com.epm.gestepm.model.company.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepositoryImpl implements CompanyRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;
	
}
