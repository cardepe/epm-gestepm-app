package com.epm.gestepm.lib.testutils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

public class MockedResultSet {

    private final List<MockedResultSetRow> rows;

    private int cursor;

    public MockedResultSet() {
        this.rows = new ArrayList<>();
        this.cursor = -1;
    }

    public ResultSet getMock() {

        final ResultSet resultSet = Mockito.mock(ResultSet.class);
        final ResultSetMetaData metadata = Mockito.mock(ResultSetMetaData.class);

        try {

            this.mockMetadata(metadata);

            Mockito.when(resultSet.getMetaData()).thenReturn(metadata);

            final Answer<Boolean> answer = invocationOnMock -> {

                this.cursor++;

                final boolean hasNext = this.cursor < this.rows.size();

                if (hasNext) {

                    final MockedResultSetRow data = this.rows.get(this.cursor);

                    this.mockOne(resultSet, data);
                }

                return hasNext;
            };

            Mockito.when(resultSet.next()).thenAnswer(answer);

        } catch (final SQLException e) {
            // N/A
        }

        return resultSet;
    }

    public void addRow(final MockedResultSetRow row) {
        this.rows.add(row);
    }

    private void mockOne(final ResultSet resultSet, final MockedResultSetRow data) {

        try {

            for (final MockedResultSetCell<?> cell : data.getCells()) {

                final String column = cell.getColumn();
                final Object value = cell.getValue();
                final Class<?> mockedType = cell.getMockedType();
                final boolean isNull = value == null;

                if (mockedType.equals(String.class)) {

                    Mockito.doAnswer(invocationOnMock -> {
                        Mockito.when(resultSet.wasNull()).thenReturn(isNull);
                        return value;
                    }).when(resultSet).getString(column);

                } else if (mockedType.equals(Integer.class)) {

                    Mockito.doAnswer(invocationOnMock -> {
                        Mockito.when(resultSet.wasNull()).thenReturn(isNull);
                        return isNull ? 0 : value;
                    }).when(resultSet).getInt(column);

                } else if (mockedType.equals(Long.class)) {

                    Mockito.doAnswer(invocationOnMock -> {
                        Mockito.when(resultSet.wasNull()).thenReturn(isNull);
                        return isNull ? 0 : value;
                    }).when(resultSet).getLong(column);

                } else if (mockedType.equals(Timestamp.class)) {

                    Mockito.doAnswer(invocationOnMock -> {
                        Mockito.when(resultSet.wasNull()).thenReturn(isNull);
                        return value;
                    }).when(resultSet).getTimestamp(column);

                } else if (mockedType.equals(Boolean.class)) {

                    Mockito.doAnswer(invocationOnMock -> {
                        Mockito.when(resultSet.wasNull()).thenReturn(isNull);
                        return value;
                    }).when(resultSet).getBoolean(column);
                }
            }

        } catch (final Exception ex) {
            // N/A
        }

    }

    private void mockMetadata(final ResultSetMetaData resultSetMetaData) {

        try {

            final MockedResultSetRow firstRow = this.rows.get(0);
            final List<MockedResultSetCell<?>> cells = firstRow.getCells();
            final int columnCount = cells.size();

            Mockito.when(resultSetMetaData.getColumnCount()).thenReturn(columnCount);

            for (int i = 1; i <= columnCount; i++) {

                final String columnName = firstRow.getCell(i).getColumn();
                Mockito.when(resultSetMetaData.getColumnLabel(i)).thenReturn(columnName);
            }

        } catch (final Exception ex) {
            // N/A
        }

    }

}
