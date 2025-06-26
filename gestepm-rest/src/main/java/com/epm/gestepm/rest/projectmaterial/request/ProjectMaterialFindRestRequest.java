package com.epm.gestepm.rest.projectmaterial.request;

import com.epm.gestepm.lib.controller.RestRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProjectMaterialFindRestRequest extends RestRequest {

    @NotNull
    private Integer projectId;

    @NotNull
    private Integer id;

}
