package com.epm.gestepm.rest.inspection.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.inspection.request.InspectionBreakListRestRequest;
import com.epm.gestepm.restapi.openapi.api.InspectionBreakV1Api;

public class ListInspectionBreakV1Operation extends APIOperation<InspectionBreakV1Api, InspectionBreakListRestRequest> {

    public ListInspectionBreakV1Operation() {
        super("listInspectionBreakV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.listInspectionBreaksV1(req.getShareId(), req.getInspectionId(),
                        req.getMeta(), req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit(), req.getOrder(),
                        req.getOrderBy(), req.getIds(), req.getStatus()));
    }

}
