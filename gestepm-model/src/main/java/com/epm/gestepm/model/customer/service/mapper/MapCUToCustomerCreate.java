package com.epm.gestepm.model.customer.service.mapper;

import com.epm.gestepm.model.customer.dao.entity.creator.CustomerCreate;
import com.epm.gestepm.modelapi.customer.dto.CustomerDto;
import com.epm.gestepm.modelapi.customer.dto.creator.CustomerCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapCUToCustomerCreate {

  CustomerCreate from(CustomerCreateDto createDto);

  CustomerCreateDto from(CustomerDto dto);

}
