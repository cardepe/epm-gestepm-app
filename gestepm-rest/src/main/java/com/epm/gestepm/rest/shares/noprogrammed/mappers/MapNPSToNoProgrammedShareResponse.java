package com.epm.gestepm.rest.shares.noprogrammed.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.ShareFileUpdateDto;
import com.epm.gestepm.restapi.openapi.model.Intervention;
import com.epm.gestepm.restapi.openapi.model.NoProgrammedShare;
import com.epm.gestepm.restapi.openapi.model.ShareFile;
import org.mapstruct.*;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface MapNPSToNoProgrammedShareResponse {

  @Mapping(source = "userId", target = "user.id")
  @Mapping(source = "projectId", target = "project.id")
  @Mapping(source = "userSigningId", target = "userSigning.id")
  @Mapping(source = "familyId", target = "family.id")
  @Mapping(source = "subFamilyId", target = "subFamily.id")
  @Mapping(source = "interventionIds", target = "interventions", qualifiedByName = "mapInterventions")
  @Mapping(source = "fileIds", target = "files", qualifiedByName = "mapFiles")
  NoProgrammedShare from(NoProgrammedShareDto dto);

  List<NoProgrammedShare> from(Page<NoProgrammedShareDto> list);

  @Named("mapInterventions")
  static List<Intervention> mapInterventions(final Set<Integer> interventionIds) {

    return interventionIds.stream().map(id -> {

      final Intervention intervention = new Intervention();
      intervention.setId(id);

      return intervention;

    }).collect(Collectors.toList());
  }

  @Named("mapFiles")
  static List<ShareFile> mapFiles(final Set<Integer> fileIds) {

    return fileIds.stream().map(id -> {

      final ShareFile shareFile = new ShareFile();
      shareFile.setId(id);

      return shareFile;

    }).collect(Collectors.toList());
  }

  @AfterMapping
  default void parse(@MappingTarget NoProgrammedShare response) {
    response.setUserSigning(response.getUserSigning() != null && response.getUserSigning().getId() != null ? response.getUserSigning() : null);
    response.setFamily(response.getFamily() != null && response.getFamily().getId() != null ? response.getFamily() : null);
    response.setSubFamily(response.getSubFamily() != null && response.getSubFamily().getId() != null ? response.getSubFamily() : null);
  }
}
