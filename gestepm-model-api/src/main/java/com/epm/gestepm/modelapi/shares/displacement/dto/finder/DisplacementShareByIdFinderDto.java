package com.epm.gestepm.modelapi.shares.displacement.dto.finder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisplacementShareByIdFinderDto {

    @NotNull
    private Integer id;

}
