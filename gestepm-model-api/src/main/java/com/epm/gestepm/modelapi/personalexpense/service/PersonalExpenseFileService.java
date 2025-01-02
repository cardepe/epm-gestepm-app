package com.epm.gestepm.modelapi.personalexpense.service;

import com.epm.gestepm.modelapi.personalexpense.dto.PersonalExpenseFileDto;
import com.epm.gestepm.modelapi.personalexpense.dto.creator.PersonalExpenseFileCreateDto;
import com.epm.gestepm.modelapi.personalexpense.dto.filter.PersonalExpenseFileFilterDto;
import com.epm.gestepm.modelapi.personalexpense.dto.finder.PersonalExpenseFileByIdFinderDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface PersonalExpenseFileService {

    List<@Valid PersonalExpenseFileDto> list(@Valid PersonalExpenseFileFilterDto filterDto);
    
    Optional<@Valid PersonalExpenseFileDto> find(@Valid PersonalExpenseFileByIdFinderDto finderDto);

    @Valid
    PersonalExpenseFileDto findOrNotFound(@Valid PersonalExpenseFileByIdFinderDto finderDto);

    @Valid
    PersonalExpenseFileDto create(@Valid PersonalExpenseFileCreateDto createDto);
    
}
