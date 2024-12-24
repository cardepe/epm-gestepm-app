package com.epm.gestepm.rest.inspection.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.inspection.request.InspectionFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.InspectionV1Api;

public class FindInspectionV1Operation extends APIOperation<InspectionV1Api, InspectionFindRestRequest> {

    public FindInspectionV1Operation() {
        super("findInspectionV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findInspectionByIdV1(req.getShareId(), req.getId(), req.getMeta(), req.getLinks(), req.getExpand()));
    }

}
