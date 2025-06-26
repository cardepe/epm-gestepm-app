package com.epm.gestepm.modelapi.project.dto.creator;

import com.epm.gestepm.modelapi.project.dto.ProjectTypeDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
public class ProjectCreateDto {

    @NotNull
    private String name;

    @NotNull
    private ProjectTypeDto type;

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
    private Set<Integer> responsibleIds;

}
