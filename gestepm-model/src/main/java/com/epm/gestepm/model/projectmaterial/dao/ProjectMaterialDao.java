package com.epm.gestepm.model.projectmaterial.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.projectmaterial.dao.entity.ProjectMaterial;
import com.epm.gestepm.model.projectmaterial.dao.entity.creator.ProjectMaterialCreate;
import com.epm.gestepm.model.projectmaterial.dao.entity.deleter.ProjectMaterialDelete;
import com.epm.gestepm.model.projectmaterial.dao.entity.filter.ProjectMaterialFilter;
import com.epm.gestepm.model.projectmaterial.dao.entity.finder.ProjectMaterialByIdFinder;
import com.epm.gestepm.model.projectmaterial.dao.entity.updater.ProjectMaterialUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ProjectMaterialDao {

  List<ProjectMaterial> list(@Valid ProjectMaterialFilter filter);

  Page<ProjectMaterial> list(@Valid ProjectMaterialFilter filter, Long offset, Long limit);

  Optional<@Valid ProjectMaterial> find(@Valid ProjectMaterialByIdFinder finder);

  @Valid
  ProjectMaterial create(@Valid ProjectMaterialCreate create);

  @Valid
  ProjectMaterial update(@Valid ProjectMaterialUpdate update);

  void delete(@Valid ProjectMaterialDelete delete);

}
