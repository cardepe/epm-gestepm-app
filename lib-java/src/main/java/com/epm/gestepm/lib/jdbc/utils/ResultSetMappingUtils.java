package com.epm.gestepm.lib.jdbc.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.stream.IntStream;

public class ResultSetMappingUtils {

    private ResultSetMappingUtils() {
    }

    public static Integer nullableInt(final ResultSet resultSet, final String col) throws SQLException {

        if (!hasColumn(resultSet, col)) {

            return null;

        } else {

            final int anInt = resultSet.getInt(col);

            return resultSet.wasNull() ? null : anInt;
        }
    }

    public static LocalDateTime nullableLocalDateTime(final ResultSet resultSet, final String col)
            throws SQLException {

        if (hasColumn(resultSet, col)) {

            final Timestamp timestamp = resultSet.getTimestamp(col);

            if (timestamp != null) {
                return timestamp.toLocalDateTime();
            }
        }

        return null;

    }

    public static String nullableString(final ResultSet resultSet, final String col) throws SQLException {

        if (!hasColumn(resultSet, col)) {

            return null;

        } else {

            final String value = resultSet.getString(col);

            return resultSet.wasNull() ? null : value;
        }
    }

    /**
     * Comprueba si el ResultSet contiene la columna indicada.
     * @param rs the rs
     * @param column the column name
     * @return true, if successful
     * @throws SQLException the SQL exception
     */
    public static boolean hasColumn(final ResultSet rs, final String column) throws SQLException {

        final ResultSetMetaData rsMetaData = rs.getMetaData();
        final int numberOfColumns = rsMetaData.getColumnCount();

        return IntStream.range(1, numberOfColumns + 1).anyMatch(i -> {
            try {
                return rsMetaData.getColumnLabel(i).equals(column);
            } catch (final SQLException e) {

                // TODO REVISAR
                // AppLogger.forClass(ResultSetMappingUtils.class).error(e);
                return false;
            }
        });
    }

    public static boolean hasValue(final ResultSet rs, final String column) throws SQLException {

        boolean result = false;

        if (hasColumn(rs, column)) {

            rs.getObject(column);
            result = !rs.wasNull();
        }

        return result;
    }

}
