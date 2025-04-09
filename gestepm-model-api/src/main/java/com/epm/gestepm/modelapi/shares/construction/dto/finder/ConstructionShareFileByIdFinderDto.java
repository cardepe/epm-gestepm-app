package com.epm.gestepm.modelapi.shares.construction.dto.finder;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ConstructionShareFileByIdFinderDto {

    @NotNull
    private Integer id;

}
