package com.epm.gestepm.lib.jdbc.api.orderby;

import java.util.Objects;

public class SQLColumnOrderBy {

    private final String columnName;

    private final SQLOrderByType sqlOrderByType;

    public SQLColumnOrderBy(String columnName) {
        this.columnName = columnName;
        this.sqlOrderByType = SQLOrderByType.ASC;
    }

    public SQLColumnOrderBy(String columnName, SQLOrderByType sqlOrderByType) {
        this.columnName = columnName;
        this.sqlOrderByType = sqlOrderByType;
    }

    public String getColumnName() {
        return columnName;
    }

    public SQLOrderByType getSqlOrderByType() {
        return sqlOrderByType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SQLColumnOrderBy that = (SQLColumnOrderBy) o;
        return Objects.equals(getColumnName(), that.getColumnName()) && getSqlOrderByType() == that.getSqlOrderByType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getColumnName(), getSqlOrderByType());
    }

}
