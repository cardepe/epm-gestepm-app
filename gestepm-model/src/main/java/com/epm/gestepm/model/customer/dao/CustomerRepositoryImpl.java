package com.epm.gestepm.model.customer.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.epm.gestepm.model.customer.dao.CustomerRepositoryCustom;

@Repository
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;
	
}
