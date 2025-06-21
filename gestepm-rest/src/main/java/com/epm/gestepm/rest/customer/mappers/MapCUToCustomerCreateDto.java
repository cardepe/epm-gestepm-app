package com.epm.gestepm.rest.customer.mappers;

import com.epm.gestepm.modelapi.customer.dto.creator.CustomerCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreateProjectCustomerV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapCUToCustomerCreateDto {

  CustomerCreateDto from(CreateProjectCustomerV1Request req);

}
