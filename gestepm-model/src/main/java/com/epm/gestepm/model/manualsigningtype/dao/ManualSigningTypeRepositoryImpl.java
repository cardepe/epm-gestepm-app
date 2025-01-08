package com.epm.gestepm.model.manualsigningtype.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ManualSigningTypeRepositoryImpl implements ManualSigningTypeRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;

}
