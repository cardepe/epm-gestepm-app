package com.epm.gestepm.rest.inspection.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.restapi.openapi.model.Inspection;
import com.epm.gestepm.restapi.openapi.model.ShareFile;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface MapIToInspectionResponse {

  @Mapping(source = "userSigningId", target = "userSigning.id")
  @Mapping(source = "shareId", target = "share.id")
  @Mapping(source = "firstTechnicalId", target = "firstTechnical.id")
  @Mapping(source = "secondTechnicalId", target = "secondTechnical.id")
  @Mapping(source = "fileIds", target = "files", qualifiedByName = "mapFiles")
  Inspection from(InspectionDto dto);

  List<Inspection> from(Page<InspectionDto> list);

  @Named("mapFiles")
  static Set<ShareFile> mapFiles(final Set<Integer> fileIds) {

    return fileIds.stream().map(id -> {

      final ShareFile shareFile = new ShareFile();
      shareFile.setId(id);

      return shareFile;

    }).collect(Collectors.toSet());
  }

  @AfterMapping
  default void parse(@MappingTarget Inspection response) {
    response.setUserSigning(response.getUserSigning() != null && response.getUserSigning().getId() != null ? response.getUserSigning() : null);
  }
}