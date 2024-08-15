package com.epm.gestepm.model.customer.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.customer.dto.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>, CustomerRepositoryCustom {
	public Customer findByProjectId(Long projectId);
}
