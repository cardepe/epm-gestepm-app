package com.epm.gestepm.model.manualsigningtype.dao;

import com.epm.gestepm.model.manualsigningtype.dao.ManualSigningTypeRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ManualSigningTypeRepositoryImpl implements ManualSigningTypeRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;

}
