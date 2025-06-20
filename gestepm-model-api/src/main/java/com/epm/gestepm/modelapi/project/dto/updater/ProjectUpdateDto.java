package com.epm.gestepm.modelapi.project.dto.updater;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
public class ProjectUpdateDto {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private Boolean isStation;

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
    private Boolean isTeleworking;

    @NotNull
    private Integer state;

    @NotNull
    private Set<Integer> responsibleIds;

}
