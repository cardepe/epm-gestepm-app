package com.epm.gestepm.rest.displacement.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.displacement.request.DisplacementFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.DisplacementV1Api;

public class FindDisplacementV1Operation extends APIOperation<DisplacementV1Api, DisplacementFindRestRequest> {

    public FindDisplacementV1Operation() {
        super("findDisplacementV1");

        this.generateLinksWith((apiClass, req) -> apiClass.findDisplacementByIdV1(req.getId(), req.getMeta(),
                req.getLinks(), req.getExpand()));
    }
}
