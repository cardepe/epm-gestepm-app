package com.epm.gestepm.modelapi.personalexpensesheet.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.creator.PersonalExpenseSheetCreateDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.deleter.PersonalExpenseSheetDeleteDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.filter.PersonalExpenseSheetFilterDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.finder.PersonalExpenseSheetByIdFinderDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.updater.PersonalExpenseSheetUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface PersonalExpenseSheetService {

  List<@Valid PersonalExpenseSheetDto> list(@Valid PersonalExpenseSheetFilterDto filterDto);

  Page<@Valid PersonalExpenseSheetDto> list(@Valid PersonalExpenseSheetFilterDto filterDto, Long offset, Long limit);

  Optional<@Valid PersonalExpenseSheetDto> find(@Valid PersonalExpenseSheetByIdFinderDto finderDto);

  @Valid
  PersonalExpenseSheetDto findOrNotFound(@Valid PersonalExpenseSheetByIdFinderDto finderDto);

  @Valid
  PersonalExpenseSheetDto create(@Valid PersonalExpenseSheetCreateDto createDto);

  @Valid
  PersonalExpenseSheetDto update(@Valid PersonalExpenseSheetUpdateDto updateDto);

  void delete(@Valid PersonalExpenseSheetDeleteDto deleteDto);

}
