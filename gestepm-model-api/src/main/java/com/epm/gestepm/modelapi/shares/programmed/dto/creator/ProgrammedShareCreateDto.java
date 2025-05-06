package com.epm.gestepm.modelapi.shares.programmed.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ProgrammedShareCreateDto {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

    @NotNull
    private LocalDateTime startDate;

}
