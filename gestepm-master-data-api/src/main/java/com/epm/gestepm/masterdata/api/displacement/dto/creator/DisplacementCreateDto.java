package com.epm.gestepm.masterdata.api.displacement.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DisplacementCreateDto {

    @NotNull
    private Integer activityCenterId;

    @NotNull
    private String name;

    @NotNull
    private Integer type;

    @NotNull
    private Integer totalTime;

}
