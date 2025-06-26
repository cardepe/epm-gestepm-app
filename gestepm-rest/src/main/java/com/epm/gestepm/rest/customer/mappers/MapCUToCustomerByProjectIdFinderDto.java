package com.epm.gestepm.rest.customer.mappers;

import com.epm.gestepm.modelapi.customer.dto.finder.CustomerByProjectIdFinderDto;
import com.epm.gestepm.rest.customer.request.CustomerFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapCUToCustomerByProjectIdFinderDto {

  CustomerByProjectIdFinderDto from(CustomerFindRestRequest req);

}
