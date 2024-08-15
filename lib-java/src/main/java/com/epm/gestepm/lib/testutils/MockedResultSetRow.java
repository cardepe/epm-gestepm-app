package com.epm.gestepm.lib.testutils;

import java.util.ArrayList;
import java.util.List;

public class MockedResultSetRow {

    private final List<MockedResultSetCell<?>> cells;

    public MockedResultSetRow() {
        this.cells = new ArrayList<>();
    }

    public Integer getColumnCount() {
        return cells.size();
    }

    public MockedResultSetCell<?> getCell(Integer index) {
        return cells.get(index - 1);
    }

    public List<MockedResultSetCell<?>> getCells() {
        return cells;
    }

    public <T> void setCell(final String column, final T value, Class<T> valueType) {
        this.cells.add(new MockedResultSetCell<T>().setMockedType(valueType).setValue(value).setColumn(column));
    }

}
