package com.epm.gestepm.masterdata.api.displacement.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DisplacementDto implements Serializable {

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
