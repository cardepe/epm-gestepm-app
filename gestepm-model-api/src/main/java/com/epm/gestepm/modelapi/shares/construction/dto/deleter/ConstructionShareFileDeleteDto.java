package com.epm.gestepm.modelapi.shares.construction.dto.deleter;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ConstructionShareFileDeleteDto {

    @NotNull
    private Integer id;

}
