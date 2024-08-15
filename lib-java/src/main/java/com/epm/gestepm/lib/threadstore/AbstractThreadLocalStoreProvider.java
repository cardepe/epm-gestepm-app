package com.epm.gestepm.lib.threadstore;

public abstract class AbstractThreadLocalStoreProvider<T> implements ThreadStoreProvider<T> {

    private final ThreadLocal<T> threadLocal;

    protected AbstractThreadLocalStoreProvider() {
        threadLocal = new ThreadLocal<>();
    }

    @Override
    public void acquire() {
        threadLocal.set(newStore());
    }

    @Override
    public void release() {
        threadLocal.remove();
    }

    @Override
    public T getStore() {

        final T store = threadLocal.get();

        if (store == null) {
            acquire();
        }

        return threadLocal.get();
    }

}
