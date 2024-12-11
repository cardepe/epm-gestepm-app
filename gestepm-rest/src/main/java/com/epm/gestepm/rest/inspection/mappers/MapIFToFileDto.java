package com.epm.gestepm.rest.inspection.mappers;

import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.ShareFileUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateInspectionV1RequestFilesInner;
import com.epm.gestepm.restapi.openapi.model.UpdateNoProgrammedShareV1RequestFilesInner;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface MapIFToFileDto {

    ShareFileUpdateDto from(UpdateInspectionV1RequestFilesInner req);

    List<ShareFileUpdateDto> from(List<UpdateNoProgrammedShareV1RequestFilesInner> list);

    @AfterMapping
    default void parse(@MappingTarget ShareFileUpdateDto updateDto) {
        final String fileName = updateDto.getName().substring(0, updateDto.getName().lastIndexOf('.'));
        final String fileExtension = updateDto.getName().substring(updateDto.getName().lastIndexOf('.') + 1);

        updateDto.setName(fileName);
        updateDto.setExt(fileExtension);
    }
}
