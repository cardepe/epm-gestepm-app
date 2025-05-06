package com.epm.gestepm.rest.shares.work.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.work.request.WorkShareFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.WorkShareV1Api;

public class FindWorkShareV1Operation extends APIOperation<WorkShareV1Api, WorkShareFindRestRequest> {

    public FindWorkShareV1Operation() {
        super("findWorkShareV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findWorkShareByIdV1(req.getId(), req.getMeta(), req.getLinks(), req.getExpand(), req.getLocale()));
    }

}
