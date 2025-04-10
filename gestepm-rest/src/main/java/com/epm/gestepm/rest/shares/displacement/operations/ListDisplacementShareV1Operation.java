package com.epm.gestepm.rest.shares.displacement.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.displacement.request.DisplacementShareListRestRequest;
import com.epm.gestepm.restapi.openapi.api.DisplacementShareV1Api;

public class ListDisplacementShareV1Operation extends APIOperation<DisplacementShareV1Api, DisplacementShareListRestRequest> {

    public ListDisplacementShareV1Operation() {
        super("listDisplacementShareV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.listDisplacementSharesV1(req.getMeta(), req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit(),
                        req.getIds(), req.getUserIds(), req.getProjectIds(), req.getStartDate(), req.getEndDate()));
    }

}
