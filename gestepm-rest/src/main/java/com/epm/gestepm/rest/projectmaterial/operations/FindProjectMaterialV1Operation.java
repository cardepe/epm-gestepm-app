package com.epm.gestepm.rest.projectmaterial.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.projectmaterial.request.ProjectMaterialFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.ProjectMaterialV1Api;

public class FindProjectMaterialV1Operation extends APIOperation<ProjectMaterialV1Api, ProjectMaterialFindRestRequest> {

    public FindProjectMaterialV1Operation() {
        super("findProjectMaterialV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findProjectMaterialByIdV1(req.getProjectId(), req.getId(), req.getMeta(), req.getLinks(), req.getExpand()));
    }

}
