package com.epm.gestepm.model.project.dao;

import com.epm.gestepm.model.project.dao.entity.creator.ProjectResponsibleCreate;
import com.epm.gestepm.model.project.dao.entity.deleter.ProjectResponsibleDelete;

import javax.validation.Valid;

public interface ProjectResponsibleDao {

    void create(@Valid ProjectResponsibleCreate create);

    void delete(@Valid ProjectResponsibleDelete delete);
}
