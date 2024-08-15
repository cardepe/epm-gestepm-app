package com.epm.gestepm.modelapi.paymenttype.service;

import com.epm.gestepm.modelapi.paymenttype.dto.PaymentType;

import java.util.List;

public interface PaymentTypeService {

	List<PaymentType> findAll();
}
