package com.epm.gestepm.modelapi.projectmaterial.dto.deleter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMaterialDeleteDto {

    @NotNull
    private Integer id;

}
