package com.epm.gestepm.rest.personalexpense.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.personalexpense.dto.PersonalExpenseDto;
import com.epm.gestepm.restapi.openapi.model.PersonalExpense;
import com.epm.gestepm.restapi.openapi.model.ShareFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface MapPEToPersonalExpenseResponse {

  @Mapping(source = "personalExpenseSheetId", target = "personalExpenseSheet.id")
  @Mapping(source = "fileIds", target = "files", qualifiedByName = "mapFiles")
  PersonalExpense from(PersonalExpenseDto dto);

  List<PersonalExpense> from(Page<PersonalExpenseDto> list);

  @Named("mapFiles")
  static List<ShareFile> mapFiles(final List<Integer> fileIds) {
    return fileIds.stream()
            .map(id -> new ShareFile().id(id))
            .collect(Collectors.toList());
  }
}
