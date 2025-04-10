package com.epm.gestepm.modelapi.shares.displacement.dto.deleter;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DisplacementShareDeleteDto {

    @NotNull
    private Integer id;

}
