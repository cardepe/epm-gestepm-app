package com.epm.gestepm.model.paymenttype.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.paymenttype.dto.PaymentType;

public interface PaymentTypeRepository extends CrudRepository<PaymentType, Long>, PaymentTypeRepositoryCustom {
	
}
