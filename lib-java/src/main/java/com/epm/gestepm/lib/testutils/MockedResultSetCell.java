package com.epm.gestepm.lib.testutils;

public class MockedResultSetCell<T> {

    private Class<T> mockedType;

    private String column;

    private T value;

    public Class<T> getMockedType() {
        return mockedType;
    }

    public MockedResultSetCell<T> setMockedType(Class<T> mockedType) {
        this.mockedType = mockedType;
        return this;
    }

    public String getColumn() {
        return column;
    }

    public MockedResultSetCell<T> setColumn(String column) {
        this.column = column;
        return this;
    }

    public T getValue() {
        return value;
    }

    public MockedResultSetCell<T> setValue(T value) {
        this.value = value;
        return this;
    }

}
