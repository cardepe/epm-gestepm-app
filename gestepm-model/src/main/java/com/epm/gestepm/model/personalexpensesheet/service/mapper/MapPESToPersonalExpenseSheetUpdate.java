package com.epm.gestepm.model.personalexpensesheet.service.mapper;

import com.epm.gestepm.model.personalexpensesheet.dao.entity.updater.PersonalExpenseSheetUpdate;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.updater.PersonalExpenseSheetUpdateDto;
import org.mapstruct.*;

@Mapper
public interface MapPESToPersonalExpenseSheetUpdate {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  PersonalExpenseSheetUpdate from(PersonalExpenseSheetUpdateDto source, @MappingTarget PersonalExpenseSheetUpdate target);

  PersonalExpenseSheetUpdate from(PersonalExpenseSheetUpdateDto updateDto);

  PersonalExpenseSheetUpdate from(PersonalExpenseSheetDto dto);

}
