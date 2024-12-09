package com.epm.gestepm.rest.shares.noprogrammed.mappers;

import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.ShareFileUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateNoProgrammedShareV1RequestFilesInner;
import org.mapstruct.*;

import java.util.Base64;
import java.util.List;

@Mapper
public interface MapNPSFToFileDto {

    ShareFileUpdateDto from(UpdateNoProgrammedShareV1RequestFilesInner req);

    List<ShareFileUpdateDto> from(List<UpdateNoProgrammedShareV1RequestFilesInner> list);

    @AfterMapping
    default void parse(@MappingTarget ShareFileUpdateDto updateDto) {
        final String fileName = updateDto.getName().substring(0, updateDto.getName().lastIndexOf('.'));
        final String fileExtension = updateDto.getName().substring(updateDto.getName().lastIndexOf('.') + 1);

        updateDto.setName(fileName);
        updateDto.setExt(fileExtension);
    }
}
