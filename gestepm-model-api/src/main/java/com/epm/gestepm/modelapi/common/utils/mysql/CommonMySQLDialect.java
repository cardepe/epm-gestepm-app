package com.epm.gestepm.modelapi.common.utils.mysql;

import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.LocalDateTimeType;

public class CommonMySQLDialect extends MySQL57Dialect {

    public CommonMySQLDialect() {
        super();
        registerFunction("date_add_minute", new SQLFunctionTemplate(LocalDateTimeType.INSTANCE, "date_add(?1, INTERVAL ?2 MINUTE)"));
    }
}
