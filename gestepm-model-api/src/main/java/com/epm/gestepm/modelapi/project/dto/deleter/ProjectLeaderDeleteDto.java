package com.epm.gestepm.modelapi.project.dto.deleter;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ProjectLeaderDeleteDto {

    @NotNull
    private Integer projectId;

    @NotNull
    private Integer userId;

}
