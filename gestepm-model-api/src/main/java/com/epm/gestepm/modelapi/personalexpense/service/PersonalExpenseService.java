package com.epm.gestepm.modelapi.personalexpense.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.personalexpense.dto.PersonalExpenseDto;
import com.epm.gestepm.modelapi.personalexpense.dto.creator.PersonalExpenseCreateDto;
import com.epm.gestepm.modelapi.personalexpense.dto.deleter.PersonalExpenseDeleteDto;
import com.epm.gestepm.modelapi.personalexpense.dto.filter.PersonalExpenseFilterDto;
import com.epm.gestepm.modelapi.personalexpense.dto.finder.PersonalExpenseByIdFinderDto;
import com.epm.gestepm.modelapi.personalexpense.dto.updater.PersonalExpenseUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface PersonalExpenseService {

  List<@Valid PersonalExpenseDto> list(@Valid PersonalExpenseFilterDto filterDto);

  Page<@Valid PersonalExpenseDto> list(@Valid PersonalExpenseFilterDto filterDto, Long offset, Long limit);

  Optional<@Valid PersonalExpenseDto> find(@Valid PersonalExpenseByIdFinderDto finderDto);

  @Valid
  PersonalExpenseDto findOrNotFound(@Valid PersonalExpenseByIdFinderDto finderDto);

  @Valid
  PersonalExpenseDto create(@Valid PersonalExpenseCreateDto createDto);

  @Valid
  PersonalExpenseDto update(@Valid PersonalExpenseUpdateDto updateDto);

  void delete(@Valid PersonalExpenseDeleteDto deleteDto);

}
