package com.epm.gestepm.model.personalexpense.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.personalexpense.dao.entity.PersonalExpense;
import com.epm.gestepm.modelapi.personalexpense.dto.PersonalExpenseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapPEToPersonalExpenseDto {

  PersonalExpenseDto from(PersonalExpense personalExpense);

  List<PersonalExpenseDto> from(List<PersonalExpense> personalExpense);

  default Page<PersonalExpenseDto> from(Page<PersonalExpense> page) {
    return new Page<>(page.cursor(), from(page.getContent()));
  }

}
