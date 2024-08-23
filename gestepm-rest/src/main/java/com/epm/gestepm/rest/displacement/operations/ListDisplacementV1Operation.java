package com.epm.gestepm.rest.displacement.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.displacement.request.DisplacementListRestRequest;
import com.epm.gestepm.restapi.openapi.api.DisplacementV1Api;

public class ListDisplacementV1Operation extends APIOperation<DisplacementV1Api, DisplacementListRestRequest> {

    public ListDisplacementV1Operation() {
        super("listDisplacementV1");

        this.generateLinksWith((apiClass, req) -> apiClass.listDisplacementsV1(req.getMeta(), req.getLinks(),
                req.getExpand(), req.getOffset(), req.getLimit(), req.getOrder(), req.getOrderBy(), req.getIds(),
                req.getActivityCenterIds(), req.getName(), req.getType()));
    }

}
