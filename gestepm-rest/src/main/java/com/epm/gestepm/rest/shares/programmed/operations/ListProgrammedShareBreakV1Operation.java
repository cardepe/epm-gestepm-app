package com.epm.gestepm.rest.shares.programmed.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.shares.programmed.request.ProgrammedShareBreakListRestRequest;
import com.epm.gestepm.restapi.openapi.api.ProgrammedShareBreakV1Api;

public class ListProgrammedShareBreakV1Operation extends APIOperation<ProgrammedShareBreakV1Api, ProgrammedShareBreakListRestRequest> {

    public ListProgrammedShareBreakV1Operation() {
        super("listProgrammedShareBreakV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.listProgrammedShareBreaksV1(req.getProgrammedShareId(),
                        req.getMeta(), req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit(), req.getOrder(),
                        req.getOrderBy(), req.getIds(), req.getStatus()));
    }

}
