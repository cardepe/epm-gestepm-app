package com.epm.gestepm.rest.shares.work.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.work.request.WorkShareBreakFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.WorkShareBreakV1Api;

public class FindWorkShareBreakV1Operation extends APIOperation<WorkShareBreakV1Api, WorkShareBreakFindRestRequest> {

    public FindWorkShareBreakV1Operation() {
        super("findWorkShareBreakV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findWorkShareBreakByIdV1(req.getWorkShareId(), req.getId(), req.getMeta(), req.getLinks(), req.getExpand(), req.getLocale()));
    }

}
