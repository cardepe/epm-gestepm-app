package com.epm.gestepm.rest.shares.construction.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.ConstructionShareV1Api;

public class FindConstructionShareV1Operation extends APIOperation<ConstructionShareV1Api, ConstructionShareFindRestRequest> {

    public FindConstructionShareV1Operation() {
        super("findConstructionShareV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findConstructionShareByIdV1(req.getId(), req.getMeta(), req.getLinks(), req.getExpand(), req.getLocale()));
    }

}
