package com.epm.gestepm.rest.shares.work.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.work.request.WorkShareBreakListRestRequest;
import com.epm.gestepm.restapi.openapi.api.WorkShareBreakV1Api;

public class ListWorkShareBreakV1Operation extends APIOperation<WorkShareBreakV1Api, WorkShareBreakListRestRequest> {

    public ListWorkShareBreakV1Operation() {
        super("listWorkShareBreakV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.listWorkShareBreaksV1(req.getWorkShareId(),
                        req.getMeta(), req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit(), req.getOrder(),
                        req.getOrderBy(), req.getIds(), req.getStatus()));
    }

}
