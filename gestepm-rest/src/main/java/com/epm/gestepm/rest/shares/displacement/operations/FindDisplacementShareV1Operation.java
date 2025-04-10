package com.epm.gestepm.rest.shares.displacement.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.displacement.request.DisplacementShareFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.DisplacementShareV1Api;

public class FindDisplacementShareV1Operation extends APIOperation<DisplacementShareV1Api, DisplacementShareFindRestRequest> {

    public FindDisplacementShareV1Operation() {
        super("findDisplacementShareV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findDisplacementShareByIdV1(req.getId(), req.getMeta(), req.getLinks(), req.getExpand(), req.getLocale()));
    }

}
