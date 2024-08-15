package com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NoProgrammedShareUpdateDto {

    @NotNull
    private Integer id;

}
