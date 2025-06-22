package com.epm.gestepm.model.project.dao;

import com.epm.gestepm.model.project.dao.entity.creator.ProjectMemberCreate;
import com.epm.gestepm.model.project.dao.entity.deleter.ProjectMemberDelete;

import javax.validation.Valid;

public interface ProjectMemberDao {

    void create(@Valid ProjectMemberCreate create);

    void delete(@Valid ProjectMemberDelete delete);
}
