package com.epm.gestepm.model.inspection.dao.mappers;

import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.inspection.dao.entity.ActionEnum;
import com.epm.gestepm.model.inspection.dao.entity.Inspection;
import com.epm.gestepm.model.inspection.dao.entity.Material;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.*;

public class InspectionRowMapper implements RowMapper<Inspection> {

    public static final String COL_I_ID = "inspection_id";

    public static final String COL_I_USER_SIGNING_ID = "user_signing_id";

    public static final String COL_I_SHARE_ID = "no_programmed_share_id";

    public static final String COL_I_ACTION = "action";

    public static final String COL_I_START_DATE = "start_date";

    public static final String COL_I_END_DATE = "end_date";

    public static final String COL_I_DESCRIPTION = "description";

    public static final String COL_I_FIRST_TECHNICAL = "first_technical";

    public static final String COL_I_SECOND_TECHNICAL = "second_technical";

    public static final String COL_I_SIGNATURE = "signature";

    public static final String COL_I_OPERATOR_SIGNATURE = "operator_signature";

    public static final String COL_I_CLIENT_NAME = "client_name";

    public static final String COL_I_MATERIAL_ID = "material_id";

    public static final String COL_I_MATERIAL_DESCRIPTION = "material_description";

    public static final String COL_I_MATERIAL_UNITS = "material_units";

    public static final String COL_I_MATERIAL_REFERENCE = "material_reference";

    public static final String COL_I_MATERIALS_FILE = "materials_file";

    public static final String COL_I_MATERIALS_FILE_EXT = "materials_file_extension";

    public static final String COL_I_EQUIPMENT_HOURS = "equipment_hours";

    public static final String COL_I_TOPIC_ID = "topic_id";

    public static final String COL_I_FILE_ID = "inspection_file_id";

    @Override
    public Inspection mapRow(ResultSet rs, int i) throws SQLException {

        final Inspection inspection = new Inspection();

        inspection.setId(rs.getInt(COL_I_ID));
        inspection.setUserSigningId(nullableInt(rs, COL_I_USER_SIGNING_ID));
        inspection.setShareId(rs.getInt(COL_I_SHARE_ID));
        inspection.setAction(ActionEnum.valueOf(rs.getString(COL_I_ACTION)));
        inspection.setStartDate(rs.getTimestamp(COL_I_START_DATE).toInstant().atOffset(ZoneOffset.UTC));
        inspection.setEndDate(nullableOffsetDateTime(rs, COL_I_END_DATE));
        inspection.setDescription(nullableString(rs, COL_I_DESCRIPTION));
        inspection.setFirstTechnicalId(rs.getInt(COL_I_FIRST_TECHNICAL));
        inspection.setSecondTechnicalId(nullableInt(rs, COL_I_SECOND_TECHNICAL));
        inspection.setSignature(nullableString(rs, COL_I_SIGNATURE));
        inspection.setOperatorSignature(nullableString(rs, COL_I_OPERATOR_SIGNATURE));
        inspection.setClientName(nullableString(rs, COL_I_CLIENT_NAME));
        if (hasValue(rs, COL_I_MATERIALS_FILE)) {
            inspection.setMaterialsFile(Base64.getEncoder().encodeToString(FileUtils.decompressBytes(rs.getBytes(COL_I_MATERIALS_FILE))));
            inspection.setMaterialsFileExtension(nullableString(rs, COL_I_MATERIALS_FILE_EXT));
        }
        inspection.setEquipmentHours(nullableInt(rs, COL_I_EQUIPMENT_HOURS));
        inspection.setTopicId(nullableInt(rs, COL_I_TOPIC_ID));

        if (hasValue(rs, COL_I_MATERIAL_ID)) {
            final Material material = new Material();
            material.setId(rs.getInt(COL_I_MATERIAL_ID));
            material.setDescription(rs.getString(COL_I_MATERIAL_DESCRIPTION));
            material.setUnits(rs.getInt(COL_I_MATERIAL_UNITS));
            material.setReference(rs.getString(COL_I_MATERIAL_REFERENCE));

            inspection.getMaterials().add(material);
        }

        final Set<Integer> fileIds = new HashSet<>();

        if (hasValue(rs, COL_I_FILE_ID)) {
            fileIds.add(rs.getInt(COL_I_FILE_ID));
        }

        inspection.setFileIds(fileIds);

        return inspection;
    }
}
