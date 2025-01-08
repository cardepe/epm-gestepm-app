package com.epm.gestepm.model.role.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl implements RoleRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;
	
}
