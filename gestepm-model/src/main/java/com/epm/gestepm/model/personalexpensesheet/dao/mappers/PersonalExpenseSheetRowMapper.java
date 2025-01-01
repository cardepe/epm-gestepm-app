package com.epm.gestepm.model.personalexpensesheet.dao.mappers;

import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheet;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheetStatusEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.*;

public class PersonalExpenseSheetRowMapper implements RowMapper<PersonalExpenseSheet> {

    public static final String COL_PES_ID = "personal_expense_sheet_id";

    public static final String COL_PES_USER_ID = "user_id";

    public static final String COL_PES_PROJECT_ID = "project_id";

    public static final String COL_PES_DESCRIPTION = "description";

    public static final String COL_PES_START_DATE = "start_date";

    public static final String COL_PES_STATUS = "status";

    public static final String COL_PES_OBSERVATIONS = "observations";

    public static final String COL_PES_AMOUNT = "amount";

    public static final String COL_PES_PERSONAL_EXPENSE_ID = "personal_expense_id";

    @Override
    public PersonalExpenseSheet mapRow(ResultSet resultSet, int i) throws SQLException {

        final PersonalExpenseSheet personalExpenseSheet = new PersonalExpenseSheet();

        personalExpenseSheet.setId(resultSet.getInt(COL_PES_ID));
        personalExpenseSheet.setUserId(resultSet.getInt(COL_PES_USER_ID));
        personalExpenseSheet.setProjectId(resultSet.getInt(COL_PES_PROJECT_ID));
        personalExpenseSheet.setDescription(resultSet.getString(COL_PES_DESCRIPTION));
        personalExpenseSheet.setStartDate(resultSet.getTimestamp(COL_PES_START_DATE).toLocalDateTime());
        personalExpenseSheet.setStatus(PersonalExpenseSheetStatusEnum.valueOf(resultSet.getString(COL_PES_STATUS)));
        personalExpenseSheet.setObservations(nullableString(resultSet, COL_PES_OBSERVATIONS));
        personalExpenseSheet.setAmount(hasValue(resultSet, COL_PES_PERSONAL_EXPENSE_ID)
                ? resultSet.getDouble(COL_PES_AMOUNT)
                : 0.0);

        final List<Integer> personalExpenseIds = new ArrayList<>();

        if (hasValue(resultSet, COL_PES_PERSONAL_EXPENSE_ID)) {
            personalExpenseIds.add(resultSet.getInt(COL_PES_PERSONAL_EXPENSE_ID));
        }

        personalExpenseSheet.setPersonalExpenseIds(personalExpenseIds);

        return personalExpenseSheet;
    }

}
