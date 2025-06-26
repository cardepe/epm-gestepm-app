package com.epm.gestepm.model.customer.dao.mappers;

import com.epm.gestepm.lib.jdbc.impl.rowmapper.CommonRowMapper;
import com.epm.gestepm.model.customer.dao.entity.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper extends CommonRowMapper implements RowMapper<Customer> {

    public static final String COL_CU_ID = "customer_id";

    public static final String COL_CU_NAME = "name";

    public static final String COL_CU_MAIN_EMAIL = "main_email";

    public static final String COL_CU_SECONDARY_EMAIL = "secondary_email";

    public static final String COL_CU_P_ID = "project_id";

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

        final Customer customer = new Customer();
        customer.setId(rs.getInt(COL_CU_ID));
        customer.setName(rs.getString(COL_CU_NAME));
        customer.setMainEmail(rs.getString(COL_CU_MAIN_EMAIL));
        customer.setSecondaryEmail(rs.getString(COL_CU_SECONDARY_EMAIL));
        customer.setProjectId(rs.getInt(COL_CU_P_ID));

        return customer;
    }
}
