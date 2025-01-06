package com.epm.gestepm.model.personalexpense.service.mapper;

import com.epm.gestepm.model.personalexpense.dao.entity.PersonalExpenseFile;
import com.epm.gestepm.modelapi.personalexpense.dto.PersonalExpenseFileDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapPEFToPersonalExpenseFileDto {

  PersonalExpenseFileDto from(PersonalExpenseFile file);

  List<PersonalExpenseFileDto> from(List<PersonalExpenseFile> files);

}
