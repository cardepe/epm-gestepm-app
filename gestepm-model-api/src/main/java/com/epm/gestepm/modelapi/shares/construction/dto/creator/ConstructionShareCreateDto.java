package com.epm.gestepm.modelapi.shares.construction.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ConstructionShareCreateDto {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

}
