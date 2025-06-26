package com.epm.gestepm.modelapi.projectmaterial.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.projectmaterial.dto.ProjectMaterialDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.creator.ProjectMaterialCreateDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.deleter.ProjectMaterialDeleteDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.filter.ProjectMaterialFilterDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.finder.ProjectMaterialByIdFinderDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.updater.ProjectMaterialUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ProjectMaterialService {

  List<@Valid ProjectMaterialDto> list(@Valid ProjectMaterialFilterDto filterDto);

  Page<@Valid ProjectMaterialDto> list(@Valid ProjectMaterialFilterDto filterDto, Long offset, Long limit);

  Optional<@Valid ProjectMaterialDto> find(@Valid ProjectMaterialByIdFinderDto finderDto);

  @Valid
  ProjectMaterialDto findOrNotFound(@Valid ProjectMaterialByIdFinderDto finderDto);

  @Valid
  ProjectMaterialDto create(@Valid ProjectMaterialCreateDto createDto);

  @Valid
  ProjectMaterialDto update(@Valid ProjectMaterialUpdateDto updateDto);

  void delete(@Valid ProjectMaterialDeleteDto deleteDto);

}
