package com.epm.gestepm.rest.shares.construction.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareBreakFindRestRequest;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.ConstructionShareBreakV1Api;
import com.epm.gestepm.restapi.openapi.api.ConstructionShareV1Api;

public class FindConstructionShareBreakV1Operation extends APIOperation<ConstructionShareBreakV1Api, ConstructionShareBreakFindRestRequest> {

    public FindConstructionShareBreakV1Operation() {
        super("findConstructionShareBreakV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findConstructionShareBreakByIdV1(req.getConstructionShareId(), req.getId(), req.getMeta(), req.getLinks(), req.getExpand(), req.getLocale()));
    }

}
