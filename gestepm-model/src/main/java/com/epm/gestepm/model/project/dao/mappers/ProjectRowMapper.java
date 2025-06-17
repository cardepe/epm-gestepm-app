package com.epm.gestepm.model.project.dao.mappers;

import com.epm.gestepm.lib.jdbc.impl.rowmapper.CommonRowMapper;
import com.epm.gestepm.model.project.dao.entity.Project;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.*;

public class ProjectRowMapper extends CommonRowMapper implements RowMapper<Project> {

    public static final String COL_PR_ID = "attr_pr_id";

    public static final String COL_PR_NAME = "attr_pr_name";

    public static final String COL_PR_IS_STATION = "attr_pr_is_station";

    public static final String COL_PR_OBJECTIVE_COST = "attr_pr_objective_cost";

    public static final String COL_PR_START_DATE = "attr_pr_start_date";

    public static final String COL_PR_END_DATE = "attr_pr_end_date";

    public static final String COL_PR_ACTIVITY_CENTER_ID = "attr_pr_activity_center_id";

    public static final String COL_PR_ACTIVITY_CENTER_NAME = "attr_pr_activity_center_name";

    public static final String COL_PR_FORUM_ID = "attr_pr_forum_id";

    public static final String COL_PR_IS_TELEWORKING = "attr_pr_is_teleworking";

    public static final String COL_PR_STATE = "attr_pr_state";

    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {

        final Project project = new Project();
        project.setId(rs.getInt(COL_PR_ID));
        project.setName(rs.getString(COL_PR_NAME));
        project.setIsStation(rs.getBoolean(COL_PR_IS_STATION));
        project.setObjectiveCost(rs.getDouble(COL_PR_OBJECTIVE_COST));
        project.setStartDate(rs.getTimestamp(COL_PR_START_DATE).toLocalDateTime().toLocalDate());
        project.setEndDate(rs.getTimestamp(COL_PR_END_DATE).toLocalDateTime().toLocalDate());
        project.setActivityCenterId(rs.getInt(COL_PR_ACTIVITY_CENTER_ID));
        project.setForumId(nullableInt(rs, COL_PR_FORUM_ID));
        project.setIsTeleworking(rs.getBoolean(COL_PR_IS_TELEWORKING));
        project.setState(rs.getInt(COL_PR_STATE));

        return project;
    }
}
