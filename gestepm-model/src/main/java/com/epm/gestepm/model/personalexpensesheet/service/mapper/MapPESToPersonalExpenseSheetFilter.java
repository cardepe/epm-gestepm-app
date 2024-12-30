package com.epm.gestepm.model.personalexpensesheet.service.mapper;

import com.epm.gestepm.model.personalexpensesheet.dao.entity.filter.PersonalExpenseSheetFilter;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.filter.PersonalExpenseSheetFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPESToPersonalExpenseSheetFilter {

  PersonalExpenseSheetFilter from(PersonalExpenseSheetFilterDto filterDto);

}
