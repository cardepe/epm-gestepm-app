package com.epm.gestepm.rest.projectmaterial.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.projectmaterial.request.ProjectMaterialListRestRequest;
import com.epm.gestepm.restapi.openapi.api.ProjectMaterialV1Api;

public class ListProjectMaterialV1Operation extends APIOperation<ProjectMaterialV1Api, ProjectMaterialListRestRequest> {

    public ListProjectMaterialV1Operation() {
        super("listProjectMaterialV1");

        this.generateLinksWith((apiClass, req) -> apiClass.listProjectMaterialsV1(req.getProjectId(), req.getMeta(),
                req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit(), req.getOrder(), req.getOrderBy(), req.getIds(), req.getProjectIds(), req.getNameContains(), req.getRequired()));
    }

}
