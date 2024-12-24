package com.epm.gestepm.model.shares.noprogrammed.service.mapper;

import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShareFile;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareFileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface MapNPSFToNoProgrammedShareFileDto {

  @Mapping(source = ".", target = "name", qualifiedByName = "parseFileName")
  NoProgrammedShareFileDto from(NoProgrammedShareFile file);

  List<NoProgrammedShareFileDto> from(List<NoProgrammedShareFile> files);

  @Named("parseFileName")
  static String parseFileName(NoProgrammedShareFile file) {
    return file.getName() + "." + file.getExt();
  }
}
