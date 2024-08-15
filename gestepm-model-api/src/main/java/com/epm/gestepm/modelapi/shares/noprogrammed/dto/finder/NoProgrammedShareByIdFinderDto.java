package com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NoProgrammedShareByIdFinderDto {

    @NotNull
    private Integer id;

}
