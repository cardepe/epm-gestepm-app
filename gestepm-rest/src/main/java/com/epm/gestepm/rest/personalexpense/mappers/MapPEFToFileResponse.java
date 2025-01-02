package com.epm.gestepm.rest.personalexpense.mappers;

import com.epm.gestepm.modelapi.personalexpense.dto.PersonalExpenseFileDto;
import com.epm.gestepm.restapi.openapi.model.ShareFile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapPEFToFileResponse {

    ShareFile from(PersonalExpenseFileDto fileDto);

    List<ShareFile> from(List<PersonalExpenseFileDto> list);

}
