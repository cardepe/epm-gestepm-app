package com.epm.gestepm.lib.user.data;

public abstract class UserData<T> {

    private final T value;

    public UserData(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

}
