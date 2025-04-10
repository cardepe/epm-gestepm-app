package com.epm.gestepm.modelapi.shares.displacement.dto.updater;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class DisplacementShareUpdateDto {

    @NotNull
    private Integer id;

    @NotNull
    private Integer projectId;

    @NotNull
    private String description;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    private String observations;

}
