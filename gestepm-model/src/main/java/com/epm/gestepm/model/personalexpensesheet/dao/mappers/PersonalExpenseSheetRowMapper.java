package com.epm.gestepm.model.personalexpensesheet.dao.mappers;

import com.epm.gestepm.lib.jdbc.impl.rowmapper.CommonRowMapper;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheet;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheetStatusEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.*;

public class PersonalExpenseSheetRowMapper extends CommonRowMapper implements RowMapper<PersonalExpenseSheet> {

    public static final String COL_PES_ID = "personal_expense_sheet_id";

    public static final String COL_PES_PROJECT_ID = "project_id";

    public static final String COL_PES_DESCRIPTION = "description";

    public static final String COL_PES_CREATED_AT = "created_at";

    public static final String COL_PES_STATUS = "status";

    public static final String COL_PES_OBSERVATIONS = "observations";

    public static final String COL_PES_AMOUNTS = "amounts";

    public static final String COL_PES_PERSONAL_EXPENSE_IDS = "personal_expense_ids";

    @Override
    public PersonalExpenseSheet mapRow(ResultSet resultSet, int i) throws SQLException {

        final PersonalExpenseSheet personalExpenseSheet = new PersonalExpenseSheet();

        personalExpenseSheet.setId(resultSet.getInt(COL_PES_ID));
        personalExpenseSheet.setProjectId(resultSet.getInt(COL_PES_PROJECT_ID));
        personalExpenseSheet.setDescription(resultSet.getString(COL_PES_DESCRIPTION));
        personalExpenseSheet.setStatus(nullableStatusEnum(resultSet.getString(COL_PES_STATUS)));
        personalExpenseSheet.setObservations(nullableString(resultSet, COL_PES_OBSERVATIONS));
        personalExpenseSheet.setAmount(hasValue(resultSet, COL_PES_AMOUNTS)
                ? Arrays.stream(resultSet.getString(COL_PES_AMOUNTS).split("\\|"))
                    .mapToDouble(Double::parseDouble)
                    .sum()
                : 0.0);

        final List<Integer> personalExpenseIds = new ArrayList<>();

        if (hasValue(resultSet, COL_PES_PERSONAL_EXPENSE_IDS)) {
            Arrays.stream(resultSet.getString(COL_PES_PERSONAL_EXPENSE_IDS).split(","))
                    .map(Integer::parseInt)
                    .forEach(personalExpenseIds::add);
        }

        personalExpenseSheet.setPersonalExpenseIds(personalExpenseIds);

        this.setCommonAudit(personalExpenseSheet, resultSet);

        return personalExpenseSheet;
    }

    public static PersonalExpenseSheetStatusEnum nullableStatusEnum(String value) {
        return Arrays.stream(PersonalExpenseSheetStatusEnum.values())
                .filter(e -> e.name().equals(value))
                .findFirst()
                .orElse(null);
    }
}
