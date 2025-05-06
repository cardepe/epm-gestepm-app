package com.epm.gestepm.model.personalexpense.dao.mappers;

import com.epm.gestepm.model.personalexpense.dao.entity.PaymentTypeEnum;
import com.epm.gestepm.model.personalexpense.dao.entity.PersonalExpense;
import com.epm.gestepm.model.personalexpense.dao.entity.PriceTypeEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.hasValue;

public class PersonalExpenseRowMapper implements RowMapper<PersonalExpense> {

    public static final String COL_PE_ID = "personal_expense_id";

    public static final String COL_PE_PES_ID = "personal_expense_sheet_id";

    public static final String COL_PE_NOTICE_DATE = "notice_date";

    public static final String COL_PE_START_DATE = "start_date";

    public static final String COL_PE_DESCRIPTION = "description";

    public static final String COL_PE_PRICE_TYPE = "price_type";

    public static final String COL_PE_QUANTITY = "quantity";

    public static final String COL_PE_AMOUNT = "amount";

    public static final String COL_PE_PAYMENT_TYPE = "payment_type";

    public static final String COL_PE_FILE_IDS = "personal_expense_file_ids";

    @Override
    public PersonalExpense mapRow(ResultSet resultSet, int i) throws SQLException {

        final PersonalExpense personalExpense = new PersonalExpense();

        personalExpense.setId(resultSet.getInt(COL_PE_ID));
        personalExpense.setPersonalExpenseSheetId(resultSet.getInt(COL_PE_PES_ID));
        personalExpense.setNoticeDate(resultSet.getTimestamp(COL_PE_NOTICE_DATE).toLocalDateTime());
        personalExpense.setStartDate(resultSet.getTimestamp(COL_PE_START_DATE).toLocalDateTime());
        personalExpense.setDescription(resultSet.getString(COL_PE_DESCRIPTION));
        personalExpense.setPriceType(PriceTypeEnum.valueOf(resultSet.getString(COL_PE_PRICE_TYPE)));
        personalExpense.setQuantity(resultSet.getDouble(COL_PE_QUANTITY));
        personalExpense.setAmount(resultSet.getDouble(COL_PE_AMOUNT));
        personalExpense.setPaymentType(PaymentTypeEnum.valueOf(resultSet.getString(COL_PE_PAYMENT_TYPE)));

        final List<Integer> fileIds = new ArrayList<>();

        if (hasValue(resultSet, COL_PE_FILE_IDS)) {
            Arrays.stream(resultSet.getString(COL_PE_FILE_IDS).split(","))
                    .map(Integer::parseInt)
                    .forEach(fileIds::add);
        }

        personalExpense.setFileIds(fileIds);

        return personalExpense;
    }

}
