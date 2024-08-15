package com.epm.gestepm.modelapi.customer.service;

import com.epm.gestepm.modelapi.customer.dto.Customer;

import java.util.List;

public interface CustomerService {

	List<Customer> findAll();
	Customer save(Customer customer);
	void delete(Long customerId);
	Customer getByProjectId(Long projectId);
}
