package com.epm.gestepm.model.personalexpensesheet.service.mapper;

import com.epm.gestepm.model.personalexpensesheet.dao.entity.finder.PersonalExpenseSheetByIdFinder;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.finder.PersonalExpenseSheetByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPESToPersonalExpenseSheetByIdFinder {

  PersonalExpenseSheetByIdFinder from(PersonalExpenseSheetByIdFinderDto finderDto);

}
