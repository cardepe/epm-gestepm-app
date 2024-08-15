package com.epm.gestepm.masterdata.api.displacement.dto.updater;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DisplacementUpdateDto {

    @NotNull
    private Integer activityCenterId;

    @NotNull
    private String name;

    @NotNull
    private Integer type;

    @NotNull
    private Integer totalTime;

}
