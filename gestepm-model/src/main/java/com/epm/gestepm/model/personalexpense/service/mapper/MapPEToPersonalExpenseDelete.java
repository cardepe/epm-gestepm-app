package com.epm.gestepm.model.personalexpense.service.mapper;

import com.epm.gestepm.model.personalexpense.dao.entity.deleter.PersonalExpenseDelete;
import com.epm.gestepm.modelapi.personalexpense.dto.deleter.PersonalExpenseDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPEToPersonalExpenseDelete {

  PersonalExpenseDelete from(PersonalExpenseDeleteDto deleteDto);

}
