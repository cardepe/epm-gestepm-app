package com.epm.gestepm.model.project.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
public class Project implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private ProjectType type;

    @NotNull
    private Double objectiveCost;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Integer activityCenterId;

    private Integer forumId;

    @NotNull
    private Integer state;

    @NotNull
    private Set<Integer> responsibleIds;

}
