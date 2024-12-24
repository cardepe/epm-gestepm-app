package com.epm.gestepm.rest.shares.noprogrammed.mappers;

import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareFileCreateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateNoProgrammedShareV1RequestFilesInner;
import org.mapstruct.*;

import java.util.List;

@Mapper
public interface MapNPSFToFileDto {

    NoProgrammedShareFileCreateDto from(UpdateNoProgrammedShareV1RequestFilesInner req);

    List<NoProgrammedShareFileCreateDto> from(List<UpdateNoProgrammedShareV1RequestFilesInner> list);

    @AfterMapping
    default void parse(@MappingTarget NoProgrammedShareFileCreateDto updateDto) {
        final String fileName = updateDto.getName().substring(0, updateDto.getName().lastIndexOf('.'));
        final String fileExtension = updateDto.getName().substring(updateDto.getName().lastIndexOf('.') + 1);

        updateDto.setName(fileName);
        updateDto.setExt(fileExtension);
    }
}
