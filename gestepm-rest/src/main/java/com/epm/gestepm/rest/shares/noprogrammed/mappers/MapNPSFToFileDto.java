package com.epm.gestepm.rest.shares.noprogrammed.mappers;

import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareFileCreateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateNoProgrammedShareV1RequestFilesInner;
import org.mapstruct.*;

import java.util.List;

@Mapper
public interface MapNPSFToFileDto {

    NoProgrammedShareFileCreateDto from(UpdateNoProgrammedShareV1RequestFilesInner req);

    List<NoProgrammedShareFileCreateDto> from(List<UpdateNoProgrammedShareV1RequestFilesInner> list);

}
