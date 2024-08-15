package com.epm.gestepm.lib.utils.datasync;

import java.util.Objects;
import java.util.Optional;

public class DataSyncResult {

    private Integer createdResourcesCount;

    private Integer updatedResourcesCount;

    private Integer deletedResourcesCount;

    private Integer equalResourcesCount;

    public DataSyncResult() {
        this.createdResourcesCount = 0;
        this.updatedResourcesCount = 0;
        this.deletedResourcesCount = 0;
        this.equalResourcesCount = 0;
    }

    public Integer getCreatedResourcesCount() {
        return this.createdResourcesCount;
    }

    public DataSyncResult setCreatedResourcesCount(final Integer createdResourcesCount) {
        this.createdResourcesCount = Optional.ofNullable(createdResourcesCount).orElse(0);
        return this;
    }

    public Integer getUpdatedResourcesCount() {
        return this.updatedResourcesCount;
    }

    public DataSyncResult setUpdatedResourcesCount(final Integer updatedResourcesCount) {
        this.updatedResourcesCount = Optional.ofNullable(updatedResourcesCount).orElse(0);
        return this;
    }

    public Integer getDeletedResourcesCount() {
        return this.deletedResourcesCount;
    }

    public DataSyncResult setDeletedResourcesCount(final Integer deletedResourcesCount) {
        this.deletedResourcesCount = Optional.ofNullable(deletedResourcesCount).orElse(0);
        return this;
    }

    public Integer getEqualResourcesCount() {
        return this.equalResourcesCount;
    }

    public DataSyncResult setEqualResourcesCount(final Integer equalResourcesCount) {
        this.equalResourcesCount = Optional.ofNullable(equalResourcesCount).orElse(0);
        return this;
    }

    public void addFrom(DataSyncResult result) {

        this.createdResourcesCount = this.createdResourcesCount + result.getCreatedResourcesCount();
        this.updatedResourcesCount = this.updatedResourcesCount + result.getUpdatedResourcesCount();
        this.deletedResourcesCount = this.deletedResourcesCount + result.getDeletedResourcesCount();
        this.equalResourcesCount = this.equalResourcesCount + result.getEqualResourcesCount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataSyncResult that = (DataSyncResult) o;
        return Objects.equals(getCreatedResourcesCount(), that.getCreatedResourcesCount())
                && Objects
                    .equals(getUpdatedResourcesCount(), that.getUpdatedResourcesCount())
                && Objects
                    .equals(getDeletedResourcesCount(), that.getDeletedResourcesCount())
                && Objects
                    .equals(getEqualResourcesCount(), that.getEqualResourcesCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreatedResourcesCount(), getUpdatedResourcesCount(), getDeletedResourcesCount(),
                getEqualResourcesCount());
    }

    @Override
    public String toString() {
        return "DataSyncResult{"
                + "createdResourcesCount=" + createdResourcesCount
                + ", updatedResourcesCount=" + updatedResourcesCount
                + ", deletedResourcesCount=" + deletedResourcesCount
                + ", equalResourcesCount=" + equalResourcesCount
                + '}';
    }

}
