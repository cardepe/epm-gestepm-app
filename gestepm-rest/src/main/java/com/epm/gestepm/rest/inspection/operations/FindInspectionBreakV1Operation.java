package com.epm.gestepm.rest.inspection.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.inspection.request.InspectionBreakFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.InspectionBreakV1Api;

public class FindInspectionBreakV1Operation extends APIOperation<InspectionBreakV1Api, InspectionBreakFindRestRequest> {

    public FindInspectionBreakV1Operation() {
        super("findInspectionBreakV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findInspectionBreakByIdV1(
                        req.getShareId(), req.getInspectionId(), req.getId(), req.getMeta(), req.getLinks(), req.getExpand(), req.getLocale()));
    }

}
