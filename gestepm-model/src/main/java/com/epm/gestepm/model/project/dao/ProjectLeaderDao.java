package com.epm.gestepm.model.project.dao;

import com.epm.gestepm.model.project.dao.entity.creator.ProjectLeaderCreate;
import com.epm.gestepm.model.project.dao.entity.deleter.ProjectLeaderDelete;

import javax.validation.Valid;

public interface ProjectLeaderDao {

    void create(@Valid ProjectLeaderCreate create);

    void delete(@Valid ProjectLeaderDelete delete);
}
