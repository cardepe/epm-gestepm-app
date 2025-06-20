package com.epm.gestepm.model.project.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.project.dao.entity.Project;
import com.epm.gestepm.model.project.dao.entity.creator.ProjectCreate;
import com.epm.gestepm.model.project.dao.entity.deleter.ProjectDelete;
import com.epm.gestepm.model.project.dao.entity.filter.ProjectFilter;
import com.epm.gestepm.model.project.dao.entity.finder.ProjectByIdFinder;
import com.epm.gestepm.model.project.dao.entity.updater.ProjectUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ProjectDao {

    List<Project> list(@Valid ProjectFilter filter);

    Page<@Valid Project> list(@Valid ProjectFilter filter, Long offset, Long limit);

    Optional<@Valid Project> find(@Valid ProjectByIdFinder finder);

    @Valid
    Project create(@Valid ProjectCreate create);

    @Valid
    Project update(@Valid ProjectUpdate update);

    void delete(@Valid ProjectDelete delete);
}
