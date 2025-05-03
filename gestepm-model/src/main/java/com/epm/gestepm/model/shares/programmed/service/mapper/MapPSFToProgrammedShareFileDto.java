package com.epm.gestepm.model.shares.programmed.service.mapper;

import com.epm.gestepm.model.shares.programmed.dao.entity.ProgrammedShareFile;
import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareFileDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapPSFToProgrammedShareFileDto {

  ProgrammedShareFileDto from(ProgrammedShareFile file);

  List<ProgrammedShareFileDto> from(List<ProgrammedShareFile> files);

}
