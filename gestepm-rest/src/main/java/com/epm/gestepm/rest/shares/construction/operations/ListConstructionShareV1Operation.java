package com.epm.gestepm.rest.shares.construction.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareListRestRequest;
import com.epm.gestepm.restapi.openapi.api.ConstructionShareV1Api;

public class ListConstructionShareV1Operation extends APIOperation<ConstructionShareV1Api, ConstructionShareListRestRequest> {

    public ListConstructionShareV1Operation() {
        super("listConstructionShareV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.listConstructionSharesV1(req.getMeta(), req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit(),
                        req.getIds(), req.getUserIds(), req.getProjectIds(), req.getStartDate(), req.getEndDate()));
    }

}
