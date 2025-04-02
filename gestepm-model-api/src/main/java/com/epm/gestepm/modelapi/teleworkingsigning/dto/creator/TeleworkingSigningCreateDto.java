package com.epm.gestepm.modelapi.teleworkingsigning.dto.creator;

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
