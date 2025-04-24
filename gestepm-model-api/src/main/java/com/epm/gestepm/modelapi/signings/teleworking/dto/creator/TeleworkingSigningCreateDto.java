package com.epm.gestepm.modelapi.signings.teleworking.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TeleworkingSigningCreateDto {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

    private String startedLocation;

}
