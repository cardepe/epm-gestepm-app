package com.epm.gestepm.rest.project.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.project.request.ProjectFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.ProjectV1Api;

public class FindProjectV1Operation extends APIOperation<ProjectV1Api, ProjectFindRestRequest> {

    public FindProjectV1Operation() {
        super("findProjectV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findProjectByIdV1(req.getId(), req.getMeta(), req.getLinks(), req.getExpand()));
    }

}
