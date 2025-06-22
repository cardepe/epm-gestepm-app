package com.epm.gestepm.rest.projectmaterial.request;

import com.epm.gestepm.lib.controller.RestRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProjectMaterialListRestRequest extends RestRequest {

    @NotNull
    private Integer projectId;

    private List<Integer> ids;

    private List<Integer> projectIds;

    private String nameContains;

    private Boolean required;

}
