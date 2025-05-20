package com.epm.gestepm.rest.shares.construction.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareBreakListRestRequest;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareListRestRequest;
import com.epm.gestepm.restapi.openapi.api.ConstructionShareBreakV1Api;
import com.epm.gestepm.restapi.openapi.api.ConstructionShareV1Api;

public class ListConstructionShareBreakV1Operation extends APIOperation<ConstructionShareBreakV1Api, ConstructionShareBreakListRestRequest> {

    public ListConstructionShareBreakV1Operation() {
        super("listConstructionShareBreakV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.listConstructionShareBreaksV1(req.getConstructionShareId(),
                        req.getMeta(), req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit(), req.getOrder(),
                        req.getOrderBy(), req.getIds(), req.getStatus()));
    }

}
