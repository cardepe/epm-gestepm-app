package com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoProgrammedShareByIdFinderDto {

    @NotNull
    private Integer id;

}
