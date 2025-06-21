package com.epm.gestepm.model.deprecated.customer.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.deprecated.customer.dto.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>, CustomerRepositoryCustom {
	public Customer findByProjectId(Long projectId);
}
