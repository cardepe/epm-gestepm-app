package com.epm.gestepm.modelapi.shares.work.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class WorkShareCreateDto {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

    @NotNull
    private LocalDateTime startDate;

}
