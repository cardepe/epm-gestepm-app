package com.epm.gestepm.model.projectmaterial.dao.mappers;

import com.epm.gestepm.lib.jdbc.impl.rowmapper.CommonRowMapper;
import com.epm.gestepm.model.projectmaterial.dao.entity.ProjectMaterial;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectMaterialRowMapper extends CommonRowMapper implements RowMapper<ProjectMaterial> {

    public static final String COL_PRMAT_ID = "project_material_id";

    public static final String COL_PRMAT_PROJECT_ID = "project_id";

    public static final String COL_PRMAT_NAME_ES = "name_es";

    public static final String COL_PRMAT_NAME_FR = "name_fr";

    public static final String COL_PRMAT_REQUIRED = "required";

    @Override
    public ProjectMaterial mapRow(ResultSet resultSet, int i) throws SQLException {

        final ProjectMaterial projectMaterial = new ProjectMaterial();

        projectMaterial.setId(resultSet.getInt(COL_PRMAT_ID));
        projectMaterial.setProjectId(resultSet.getInt(COL_PRMAT_PROJECT_ID));
        projectMaterial.setNameEs(resultSet.getString(COL_PRMAT_NAME_ES));
        projectMaterial.setNameFr(resultSet.getString(COL_PRMAT_NAME_FR));
        projectMaterial.setRequired(resultSet.getBoolean(COL_PRMAT_REQUIRED));

        return projectMaterial;
    }
}
