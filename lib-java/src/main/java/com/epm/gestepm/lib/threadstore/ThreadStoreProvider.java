package com.epm.gestepm.lib.threadstore;

public interface ThreadStoreProvider<T> {

    void acquire();

    void release();

    T getStore();

    T newStore();

}
