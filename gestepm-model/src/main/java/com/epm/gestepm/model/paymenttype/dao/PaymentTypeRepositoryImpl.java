package com.epm.gestepm.model.paymenttype.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.epm.gestepm.model.paymenttype.dao.PaymentTypeRepositoryCustom;

@Repository
public class PaymentTypeRepositoryImpl implements PaymentTypeRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;
	
}
