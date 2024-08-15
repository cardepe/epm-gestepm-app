package com.epm.gestepm.modelapi.shares.noprogrammed.dto.deleter;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NoProgrammedShareDeleteDto {

    @NotNull
    private Integer id;

}
