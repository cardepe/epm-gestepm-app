package com.epm.gestepm.model.personalexpensesheet.service.mapper;

import com.epm.gestepm.model.personalexpensesheet.dao.entity.updater.PersonalExpenseSheetUpdate;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.updater.PersonalExpenseSheetUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPESToPersonalExpenseSheetUpdate {

  PersonalExpenseSheetUpdate from(PersonalExpenseSheetUpdateDto updateDto);

}
