package com.epm.gestepm.lib.entity;

import java.util.Objects;
import java.util.StringJoiner;

public abstract class Orderable {

    private String order;

    private String orderBy;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orderable orderable = (Orderable) o;
        return Objects.equals(getOrder(), orderable.getOrder()) && Objects.equals(getOrderBy(), orderable.getOrderBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrder(), getOrderBy());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Orderable.class.getSimpleName() + "[", "]")
                .add("order='" + order + "'")
                .add("orderBy='" + orderBy + "'")
                .toString();
    }
}
