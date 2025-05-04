package com.epm.gestepm.modelapi.shares.work.dto.deleter;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkShareDeleteDto {

    @NotNull
    private Integer id;

}
