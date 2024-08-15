package com.epm.gestepm.modelapi.common.scope;

import java.util.Objects;
import java.util.StringJoiner;

public class ShareListFilterParams {

    private Long id;

    private String type;

    private Long projectId;

    private Integer progress;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void clear() {
        id = null;
        type = null;
        projectId = null;
        progress = null;
        userId = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShareListFilterParams that = (ShareListFilterParams) o;
        return Objects.equals(id, that.id) && Objects.equals(type, that.type) && Objects.equals(projectId, that.projectId) && Objects.equals(progress, that.progress) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, projectId, progress, userId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ShareListFilterParams.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("type='" + type + "'")
                .add("projectId=" + projectId)
                .add("progress=" + progress)
                .add("userId=" + userId)
                .toString();
    }
}
