package com.epm.gestepm.model.pricetype.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.epm.gestepm.model.pricetype.dao.PriceTypeRepositoryCustom;

@Repository
public class PriceTypeRepositoryImpl implements PriceTypeRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;
	
}
