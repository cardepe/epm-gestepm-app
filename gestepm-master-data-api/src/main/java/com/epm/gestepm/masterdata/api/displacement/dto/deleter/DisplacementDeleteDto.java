package com.epm.gestepm.masterdata.api.displacement.dto.deleter;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DisplacementDeleteDto {

    @NotNull
    private Integer id;

}
