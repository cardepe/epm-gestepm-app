package com.epm.gestepm.modelapi.project.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
public class ProjectLeaderCreateDto {

    @NotNull
    private Integer projectId;

    @NotNull
    private Integer userId;

}
