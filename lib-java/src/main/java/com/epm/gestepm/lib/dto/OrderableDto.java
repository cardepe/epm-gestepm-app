package com.epm.gestepm.lib.dto;

import java.util.Objects;
import java.util.StringJoiner;

public abstract class OrderableDto {

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
        OrderableDto that = (OrderableDto) o;
        return Objects.equals(getOrder(), that.getOrder()) && Objects.equals(getOrderBy(), that.getOrderBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrder(), getOrderBy());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrderableDto.class.getSimpleName() + "[", "]")
                .add("order='" + order + "'")
                .add("orderBy='" + orderBy + "'")
                .toString();
    }
}
