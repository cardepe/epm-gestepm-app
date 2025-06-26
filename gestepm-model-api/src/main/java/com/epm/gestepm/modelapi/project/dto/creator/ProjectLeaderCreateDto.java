package com.epm.gestepm.modelapi.project.dto.creator;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class ProjectLeaderCreateDto {

    @NotNull
    private Integer projectId;

    @NotNull
    private Integer userId;

}
