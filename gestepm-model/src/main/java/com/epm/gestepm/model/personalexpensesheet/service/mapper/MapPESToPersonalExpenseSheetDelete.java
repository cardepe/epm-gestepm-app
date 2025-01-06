package com.epm.gestepm.model.personalexpensesheet.service.mapper;

import com.epm.gestepm.model.personalexpensesheet.dao.entity.deleter.PersonalExpenseSheetDelete;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.deleter.PersonalExpenseSheetDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPESToPersonalExpenseSheetDelete {

  PersonalExpenseSheetDelete from(PersonalExpenseSheetDeleteDto deleteDto);

}
