package com.epm.gestepm.model.customer.service.mapper;

import com.epm.gestepm.model.customer.dao.entity.finder.CustomerByProjectIdFinder;
import com.epm.gestepm.modelapi.customer.dto.finder.CustomerByProjectIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapCUToCustomerByProjectIdFinder {

  CustomerByProjectIdFinder from(CustomerByProjectIdFinderDto finderDto);

}
