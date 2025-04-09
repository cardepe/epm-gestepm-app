package com.epm.gestepm.model.shares.construction.dao.mappers;

import com.epm.gestepm.lib.jdbc.impl.rowmapper.CommonRowMapper;
import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShare;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.*;

public class ConstructionShareRowMapper extends CommonRowMapper implements RowMapper<ConstructionShare> {

    public static final String COL_CS_ID = "construction_share_id";

    public static final String COL_CS_U_ID = "user_id";

    public static final String COL_CS_P_ID = "project_id";

    public static final String COL_CS_OBSERVATIONS = "observations";

    public static final String COL_CS_SIGNATURE_OP = "signature_op";

    public static final String COL_CSF_ID = "construction_share_file_id";

    @Override
    public ConstructionShare mapRow(ResultSet rs, int rowNum) throws SQLException {

        final ConstructionShare constructionShare = new ConstructionShare();
        constructionShare.setId(rs.getInt(COL_CS_ID));
        constructionShare.setUserId(rs.getInt(COL_CS_U_ID));
        constructionShare.setProjectId(rs.getInt(COL_CS_P_ID));
        constructionShare.setObservations(nullableString(rs, COL_CS_OBSERVATIONS));
        constructionShare.setOperatorSignature(nullableString(rs, COL_CS_SIGNATURE_OP));

        final Set<Integer> fileIds = new HashSet<>();

        if (hasValue(rs, COL_CSF_ID)) {
            fileIds.add(rs.getInt(COL_CSF_ID));
        }

        constructionShare.setFileIds(fileIds);

        this.setCommonAudit(constructionShare, rs);

        return constructionShare;
    }
}
