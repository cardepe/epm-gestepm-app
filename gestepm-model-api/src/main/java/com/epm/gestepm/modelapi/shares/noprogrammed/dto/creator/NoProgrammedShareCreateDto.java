package com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NoProgrammedShareCreateDto {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

}
