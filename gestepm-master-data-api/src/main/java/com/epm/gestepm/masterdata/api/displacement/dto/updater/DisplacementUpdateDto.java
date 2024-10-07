package com.epm.gestepm.masterdata.api.displacement.dto.updater;

import com.epm.gestepm.masterdata.api.displacement.dto.DisplacementTypeDto;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DisplacementUpdateDto {

    @NotNull
    private Integer id;

    @NotNull
    private Integer activityCenterId;

    @NotNull
    private String name;

    @NotNull
    private DisplacementTypeDto type;

    @NotNull
    private Integer totalTime;

}
