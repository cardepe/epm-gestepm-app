package com.epm.gestepm.model.personalexpensesheet.service.mapper;

import com.epm.gestepm.model.personalexpensesheet.dao.entity.creator.PersonalExpenseSheetCreate;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.creator.PersonalExpenseSheetCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPESToPersonalExpenseSheetCreate {

  PersonalExpenseSheetCreate from(PersonalExpenseSheetCreateDto createDto);

}
