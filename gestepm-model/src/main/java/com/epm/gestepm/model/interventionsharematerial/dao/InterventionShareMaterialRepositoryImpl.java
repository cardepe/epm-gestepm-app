package com.epm.gestepm.model.interventionsharematerial.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.epm.gestepm.model.interventionsharematerial.dao.InterventionShareMaterialRepositoryCustom;

@Repository
public class InterventionShareMaterialRepositoryImpl implements InterventionShareMaterialRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;
	
}
