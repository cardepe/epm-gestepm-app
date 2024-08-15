package com.epm.gestepm.model.customer.service;

import java.util.List;

import com.epm.gestepm.model.customer.dao.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.customer.dto.Customer;
import com.epm.gestepm.modelapi.customer.service.CustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> findAll() {
		return (List<Customer>) customerRepository.findAll();
	}
	
	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}
	
	@Override
	public void delete(Long customerId) {
		customerRepository.deleteById(customerId);
	}
	
	@Override
	public Customer getByProjectId(Long projectId) {
		return customerRepository.findByProjectId(projectId);
	}
}
