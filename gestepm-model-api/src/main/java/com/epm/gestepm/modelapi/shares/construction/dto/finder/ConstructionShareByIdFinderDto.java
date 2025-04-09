package com.epm.gestepm.modelapi.shares.construction.dto.finder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConstructionShareByIdFinderDto {

    @NotNull
    private Integer id;

}
