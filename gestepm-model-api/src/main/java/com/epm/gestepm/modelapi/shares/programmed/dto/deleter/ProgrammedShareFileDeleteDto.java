package com.epm.gestepm.modelapi.shares.programmed.dto.deleter;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProgrammedShareFileDeleteDto {

    @NotNull
    private Integer id;

}
