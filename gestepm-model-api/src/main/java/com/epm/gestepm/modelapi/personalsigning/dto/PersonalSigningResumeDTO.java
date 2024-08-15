package com.epm.gestepm.modelapi.personalsigning.dto;

import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

public class PersonalSigningResumeDTO {

    private String projectName;

    private String type;

    private Date startDate;

    private Date endDate;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalSigningResumeDTO that = (PersonalSigningResumeDTO) o;
        return Objects.equals(projectName, that.projectName) && Objects.equals(type, that.type) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectName, type, startDate, endDate);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PersonalSigningResumeDTO.class.getSimpleName() + "[", "]")
                .add("projectName='" + projectName + "'")
                .add("type='" + type + "'")
                .add("startDate=" + startDate)
                .add("endDate=" + endDate)
                .toString();
    }
}
