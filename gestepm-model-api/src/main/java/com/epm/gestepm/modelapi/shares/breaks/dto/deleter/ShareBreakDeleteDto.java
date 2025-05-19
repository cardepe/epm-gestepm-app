package com.epm.gestepm.modelapi.shares.breaks.dto.deleter;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ShareBreakDeleteDto {

    @NotNull
    private Integer id;

}
