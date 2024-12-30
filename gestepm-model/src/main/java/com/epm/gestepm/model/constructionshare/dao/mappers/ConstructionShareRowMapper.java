package com.epm.gestepm.model.constructionshare.dao.mappers;

import com.epm.gestepm.lib.jdbc.impl.rowmapper.CommonRowMapper;
import com.epm.gestepm.model.constructionshare.dao.entity.ConstructionShare;
import com.epm.gestepm.model.constructionshare.dao.entity.ConstructionShareFile;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.*;

public class ConstructionShareRowMapper extends CommonRowMapper implements RowMapper<ConstructionShare> {

    public static final String COL_CS_ID = "construction_share_id";

    public static final String COL_CS_U_ID = "user_id";

    public static final String COL_CS_P_ID = "project_id";

    public static final String COL_CS_U_S_ID = "user_signing_id";

    public static final String COL_CS_START_DATE = "start_date";

    public static final String COL_CS_END_DATE = "end_date";

    public static final String COL_CS_OBSERVATIONS = "observations";

    public static final String COL_CS_SIGNATURE_OP = "signature_op";

    public static final String COL_CS_MATERIALS = "materials";

    public static final String COL_CS_MR_SIGNATURE = "mr_signature";

    public static final String COL_CS_DS_ID = "displacement_share_id";

    public static final String COL_CSF_ID = "construction_share_file_id";

    public static final String COL_CSF_NAME = "name";

    public static final String COL_CSF_EXT = "ext";

    public static final String COL_CSF_CONTENT = "content";

    @Override
    public ConstructionShare mapRow(ResultSet rs, int rowNum) throws SQLException {

        return ConstructionShare.builder()
                .id(rs.getInt(COL_CS_ID))
                .userId(rs.getInt(COL_CS_U_ID))
                .projectId(rs.getInt(COL_CS_P_ID))
                .userSigningId(nullableInt(rs, COL_CS_U_S_ID))
                .startDate(nullableLocalDateTime(rs, COL_CS_START_DATE))
                .endDate(nullableLocalDateTime(rs, COL_CS_END_DATE))
                .observations(nullableString(rs, COL_CS_OBSERVATIONS))
                .signatureOp(nullableString(rs, COL_CS_SIGNATURE_OP))
                .materials(nullableString(rs, COL_CS_MATERIALS))
                .mrSignature(nullableString(rs, COL_CS_MR_SIGNATURE))
                .displacementShareId(nullableInt(rs, COL_CS_DS_ID))
                .files(mapRowFiles(rs))
                .build();
    }

    @SneakyThrows
    private List<ConstructionShareFile> mapRowFiles(ResultSet rs) {

        final Integer constructionShareFileId = nullableInt(rs, COL_CSF_ID);

        if (constructionShareFileId == null) {
            return new ArrayList<>();
        }

        final ConstructionShareFile constructionShareFile = ConstructionShareFile.builder()
                .id(constructionShareFileId)
                .name(rs.getString(COL_CSF_NAME))
                .ext(rs.getString(COL_CSF_EXT))
                .content(rs.getBytes(COL_CSF_CONTENT))
                .build();

        return List.of(constructionShareFile);
    }
}
