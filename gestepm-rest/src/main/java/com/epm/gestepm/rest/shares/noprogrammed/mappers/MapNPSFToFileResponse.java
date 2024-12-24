package com.epm.gestepm.rest.shares.noprogrammed.mappers;

import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareFileDto;
import com.epm.gestepm.restapi.openapi.model.ShareFile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapNPSFToFileResponse {

    ShareFile from(NoProgrammedShareFileDto fileDto);

    List<ShareFile> from(List<NoProgrammedShareFileDto> list);

}
