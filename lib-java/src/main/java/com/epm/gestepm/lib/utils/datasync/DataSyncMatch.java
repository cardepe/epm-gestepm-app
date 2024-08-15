package com.epm.gestepm.lib.utils.datasync;

public class DataSyncMatch<K, T> {

    private final K key;

    private final T leftValue;

    private final T rightValue;

    public DataSyncMatch(K key, T leftValue, T rightValue) {
        this.key = key;
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    public K getKey() {
        return key;
    }

    public T getLeftValue() {
        return leftValue;
    }

    public T getRightValue() {
        return rightValue;
    }

    public boolean isMatch() {
        return this.leftValue != null && this.rightValue != null;
    }

    public boolean hasLeft() {
        return this.leftValue != null;
    }

    public boolean hasRight() {
        return this.rightValue != null;
    }

}
