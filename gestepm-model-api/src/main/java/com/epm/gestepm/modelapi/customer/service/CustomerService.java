package com.epm.gestepm.modelapi.customer.service;

import com.epm.gestepm.modelapi.customer.dto.CustomerDto;
import com.epm.gestepm.modelapi.customer.dto.creator.CustomerCreateDto;
import com.epm.gestepm.modelapi.customer.dto.finder.CustomerByProjectIdFinderDto;

import javax.validation.Valid;
import java.util.Optional;

public interface CustomerService {
    
    Optional<@Valid CustomerDto> find(@Valid CustomerByProjectIdFinderDto finderDto);

    @Valid
    CustomerDto findOrNotFound(@Valid CustomerByProjectIdFinderDto finderDto);

    @Valid
    CustomerDto create(@Valid CustomerCreateDto createDto);

}
