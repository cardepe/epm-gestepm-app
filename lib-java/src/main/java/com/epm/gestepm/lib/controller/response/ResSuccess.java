package com.epm.gestepm.lib.controller.response;

import java.io.Serializable;
import java.util.Objects;

public class ResSuccess implements Serializable {

    private Integer httpStatus;

    private String title;

    private String detail;

    private String traceId;

    public Integer getHttpStatus() {
        return this.httpStatus;
    }

    public void setHttpStatus(final Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(final String detail) {
        this.detail = detail;
    }

    public String getTraceId() {
        return this.traceId;
    }

    public void setTraceId(final String traceId) {
        this.traceId = traceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.detail, this.httpStatus, this.title, this.traceId);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResSuccess)) {
            return false;
        }
        final ResSuccess other = (ResSuccess) obj;
        return Objects.equals(this.detail, other.detail)
                && Objects.equals(this.httpStatus, other.httpStatus)
                && Objects.equals(this.title, other.title) && Objects.equals(this.traceId, other.traceId);
    }

    @Override
    public String toString() {
        return "ResSuccess [httpStatus="
                + this.httpStatus + ", title=" + this.title + ", detail=" + this.detail
                + ", traceId="
                + this.traceId + "]";
    }

}
