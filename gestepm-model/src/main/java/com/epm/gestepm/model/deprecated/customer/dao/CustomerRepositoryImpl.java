package com.epm.gestepm.model.deprecated.customer.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;
	
}
