package com.epm.gestepm.rest.customer.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.customer.dto.CustomerDto;
import com.epm.gestepm.restapi.openapi.model.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapCUToCustomerResponse {

  Customer from(CustomerDto dto);

  List<Customer> from(Page<CustomerDto> list);

}
