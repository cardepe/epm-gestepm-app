package com.epm.gestepm.rest.shares.work.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.work.request.WorkShareListRestRequest;
import com.epm.gestepm.restapi.openapi.api.WorkShareV1Api;

public class ListWorkShareV1Operation extends APIOperation<WorkShareV1Api, WorkShareListRestRequest> {

    public ListWorkShareV1Operation() {
        super("listWorkShareV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.listWorkSharesV1(req.getMeta(), req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit(),
                        req.getOrder(), req.getOrderBy(), req.getIds(), req.getUserIds(), req.getProjectIds(), req.getStartDate(), req.getEndDate(), req.getStatus()));
    }

}
