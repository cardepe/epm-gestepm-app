package com.epm.gestepm.model.deprecated.customer.service;

import com.epm.gestepm.model.deprecated.customer.dao.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.deprecated.customer.dto.Customer;
import com.epm.gestepm.modelapi.deprecated.customer.service.CustomerService;

@Component("customerServiceOld")
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

}
