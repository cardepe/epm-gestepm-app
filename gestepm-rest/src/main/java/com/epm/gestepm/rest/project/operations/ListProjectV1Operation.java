package com.epm.gestepm.rest.project.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.project.request.ProjectListRestRequest;
import com.epm.gestepm.restapi.openapi.api.ProjectV1Api;

public class ListProjectV1Operation extends APIOperation<ProjectV1Api, ProjectListRestRequest> {

    public ListProjectV1Operation() {
        super("listProjectV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.listProjectsV1(req.getMeta(), req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit(),
                        req.getOrder(), req.getOrderBy(), req.getIds(), req.getNameContains(), req.getIsStation(), req.getActivityCenterIds(), req.getIsTeleworking(), req.getState(), req.getResponsibleIds(), req.getMemberIds()));
    }

}
