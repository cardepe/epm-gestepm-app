package com.epm.gestepm.model.personalexpensesheet.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheet;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapPESToPersonalExpenseSheetDto {

  PersonalExpenseSheetDto from(PersonalExpenseSheet personalExpenseSheet);

  List<PersonalExpenseSheetDto> from(List<PersonalExpenseSheet> personalExpenseSheet);

  default Page<PersonalExpenseSheetDto> from(Page<PersonalExpenseSheet> page) {
    return new Page<>(page.cursor(), from(page.getContent()));
  }

}
