package com.epm.gestepm.model.paymenttype.service;

import java.util.List;

import com.epm.gestepm.model.paymenttype.dao.PaymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.paymenttype.dto.PaymentType;
import com.epm.gestepm.modelapi.paymenttype.service.PaymentTypeService;

@Service
@Transactional
public class PaymentTypeServiceImpl implements PaymentTypeService {

	@Autowired
	private PaymentTypeRepository paymentTypeRepository;

	@Override
	public List<PaymentType> findAll() {
		return (List<PaymentType>) paymentTypeRepository.findAll();
	}
}
