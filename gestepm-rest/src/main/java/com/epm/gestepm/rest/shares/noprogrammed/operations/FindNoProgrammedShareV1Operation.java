package com.epm.gestepm.rest.shares.noprogrammed.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.noprogrammed.request.NoProgrammedShareFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.NoProgrammedShareV1Api;

public class FindNoProgrammedShareV1Operation extends APIOperation<NoProgrammedShareV1Api, NoProgrammedShareFindRestRequest> {

    public FindNoProgrammedShareV1Operation() {
        super("findNoProgrammedShareV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findNoProgrammedShareByIdV1(req.getId(), req.getMeta(), req.getLinks(), req.getExpand(), req.getLocale()));
    }

}
